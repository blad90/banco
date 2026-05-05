import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Reporte } from '../../common/reporte';
import { Cliente } from '../../common/cliente';
import { ClienteService } from '../../services/cliente-service';
import { ReporteService } from '../../services/reporte-service';

@Component({
  selector: 'app-reportes',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './reportes-component.html',
})
export class ReportesComponent implements OnInit {
  clientes = signal<Cliente[]>([]);
  reporte = signal<Reporte[]>([]);
  loading = signal(false);
  toast = signal<{type: string; msg: string} | null>(null);
  downloading = signal(false);

  filtros = { clienteId: 0, fechaInicio: '', fechaFin: '' };

  get totalCreditos() {
    return this.reporte().filter(r => r['Movimiento'] > 0).reduce((s, r) => s + r['Movimiento'], 0);
  }
  get totalDebitos() {
    return this.reporte().filter(r => r['Movimiento'] < 0).reduce((s, r) => s + Math.abs(r['Movimiento']), 0);
  }

  constructor(private clienteService: ClienteService, private reporteService: ReporteService) {}

  ngOnInit() {
    this.clienteService.getClientes().subscribe(data => this.clientes.set(data));
    const today = new Date().toISOString().split('T')[0];
    const past = new Date(Date.now() - 30 * 86400000).toISOString().split('T')[0];
    this.filtros.fechaInicio = past;
    this.filtros.fechaFin = today;
  }

  buscar() {
    if (!this.filtros.clienteId || !this.filtros.fechaInicio || !this.filtros.fechaFin) {
      this.showToast('error', 'Complete todos los filtros');
      return;
    }
    this.loading.set(true);
    this.reporteService.getReporte(this.filtros.clienteId, this.filtros.fechaInicio, this.filtros.fechaFin).subscribe({
      next: data => { this.reporte.set(data); this.loading.set(false); },
      error: (e: any) => { this.showToast('error', e.error?.message || 'Error al generar reporte'); this.loading.set(false); }
    });
  }

  descargarPdf() {
    if (!this.filtros.clienteId) { this.showToast('error', 'Seleccione un cliente'); return; }
    this.downloading.set(true);
    this.reporteService.getReportePdf(this.filtros.clienteId, this.filtros.fechaInicio, this.filtros.fechaFin).subscribe({
      next: (blob: Blob) => {
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url; a.download = 'estado_cuenta.pdf'; a.click();
        URL.revokeObjectURL(url);
        this.downloading.set(false);
      },
      error: () => { this.showToast('error', 'Error al descargar PDF'); this.downloading.set(false); }
    });
  }

  showToast(type: string, msg: string) {
    this.toast.set({ type, msg });
    setTimeout(() => this.toast.set(null), 3500);
  }
}