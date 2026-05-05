import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { Movimiento } from '../../common/movimiento';
import { Cuenta } from '../../common/cuenta';
import { ClienteService } from '../../services/cliente-service';
import { CuentaService } from '../../services/cuenta-service';
import { MovimientoService } from '../../services/movimiento-service';
import { ReporteService } from '../../services/reporte-service';

@Component({
  selector: 'app-movimientos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './movimientos-component.html',
})
export class MovimientosComponent implements OnInit {
  movimientos = signal<Movimiento[]>([]);
  cuentas = signal<Cuenta[]>([]);
  search = signal('');
  loading = signal(false);
  toast = signal<{type: string; msg: string} | null>(null);
  showModal = signal(false);
  saving = signal(false);

  filtered = computed(() => {
    const q = this.search().toLowerCase();
    return this.movimientos().filter(m =>
      (m.numeroCuenta || '').toLowerCase().includes(q) ||
      m.tipoMovimiento.toLowerCase().includes(q) ||
      String(m.valor).includes(q)
    );
  });

  totalCreditos = computed(() =>
    this.filtered().filter(m => m.valor! > 0).reduce((s, m) => s + m.valor!, 0)
  );
  totalDebitos = computed(() =>
    this.filtered().filter(m => m.valor! < 0).reduce((s, m) => s + Math.abs(m.valor!), 0)
  );

  form: Movimiento = this.emptyForm();

  constructor(private clienteService: ClienteService, private cuentaService: CuentaService,
              private movimientoService: MovimientoService, private reporteService: ReporteService) {}

  ngOnInit() { this.loadAll(); }

  loadAll() {
    this.loading.set(true);
    this.cuentaService.getCuentas().subscribe(data => this.cuentas.set(data));
    this.movimientoService.getMovimientos().subscribe({
      next: data => { this.movimientos.set(data); this.loading.set(false); },
      error: () => { this.showToast('error', 'Error al cargar movimientos'); this.loading.set(false); }
    });
  }

  openCreate() { this.form = this.emptyForm(); this.showModal.set(true); }

  save(f: NgForm) {
    if (f.invalid) { f.form.markAllAsTouched(); return; }
    this.saving.set(true);
    this.movimientoService.createMovimiento(this.form).subscribe({
      next: () => { this.showToast('success', 'Movimiento registrado'); this.showModal.set(false); this.loadAll(); this.saving.set(false); },
      error: (e: any) => { this.showToast('error', e.error?.message || 'Error al guardar'); this.saving.set(false); }
    });
  }

  delete(m: Movimiento) {
    if (!confirm('Eliminar este movimiento?')) return;
    this.movimientoService.deleteMovimiento(m.id!).subscribe({
      next: () => { this.showToast('success', 'Eliminado'); this.loadAll(); },
      error: (e: any) => this.showToast('error', e.error?.message || 'Error')
    });
  }

  showToast(type: string, msg: string) {
    this.toast.set({ type, msg });
    setTimeout(() => this.toast.set(null), 4000);
  }

  emptyForm(): Movimiento { return { tipoMovimiento: 'CREDITO', valor: 0, cuentaId: 0 }; }
}