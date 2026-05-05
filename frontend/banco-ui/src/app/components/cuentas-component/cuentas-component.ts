import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { Cuenta } from '../../common/cuenta';
import { Cliente } from '../../common/cliente';
import { ClienteService } from '../../services/cliente-service';
import { CuentaService } from '../../services/cuenta-service';

@Component({
  selector: 'app-cuentas',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './cuentas-component.html',
})
export class CuentasComponent implements OnInit {
  cuentas = signal<Cuenta[]>([]);
  clientes = signal<Cliente[]>([]);
  search = signal('');
  loading = signal(false);
  toast = signal<{type: string; msg: string} | null>(null);
  showModal = signal(false);
  editMode = signal(false);
  saving = signal(false);

  filtered = computed(() => {
    const q = this.search().toLowerCase();
    return this.cuentas().filter(c =>
      c.numeroCuenta.toLowerCase().includes(q) ||
      c.tipoCuenta.toLowerCase().includes(q) ||
      (c.clienteNombre || '').toLowerCase().includes(q)
    );
  });

  form: Cuenta = this.emptyForm();

  constructor(private clienteService: ClienteService, private cuentaService: CuentaService) {}

  ngOnInit() { this.loadAll(); }

  loadAll() {
    this.loading.set(true);
    this.clienteService.getClientes().subscribe(data => this.clientes.set(data));
    this.cuentaService.getCuentas().subscribe({
      next: data => { this.cuentas.set(data); this.loading.set(false); },
      error: () => { this.showToast('error', 'Error al cargar cuentas'); this.loading.set(false); }
    });
  }

  openCreate() { this.form = this.emptyForm(); this.editMode.set(false); this.showModal.set(true); }
  openEdit(c: Cuenta) { this.form = { ...c }; this.editMode.set(true); this.showModal.set(true); }

  save(f: NgForm) {
    if (f.invalid) { f.form.markAllAsTouched(); return; }
    this.saving.set(true);
    const obs = this.editMode()
      ? this.cuentaService.updateCuenta(this.form.id!, this.form)
      : this.cuentaService.createCuenta(this.form);
    obs.subscribe({
      next: () => { this.showToast('success', this.editMode() ? 'Cuenta actualizada' : 'Cuenta creada'); this.showModal.set(false); this.loadAll(); this.saving.set(false); },
      error: (e) => { this.showToast('error', e.error?.message || 'Error al guardar'); this.saving.set(false); }
    });
  }

  delete(c: Cuenta) {
    if (!confirm(`¿Eliminar cuenta "${c.numeroCuenta}"?`)) return;
    this.cuentaService.deleteCuenta(c.id!).subscribe({
      next: () => { this.showToast('success', 'Cuenta eliminada'); this.loadAll(); },
      error: (e) => this.showToast('error', e.error?.message || 'Error al eliminar')
    });
  }

  showToast(type: string, msg: string) {
    this.toast.set({ type, msg });
    setTimeout(() => this.toast.set(null), 3500);
  }

  emptyForm(): Cuenta {
    return { numeroCuenta: '', tipoCuenta: 'Ahorros', saldoInicial: 0, estado: true, clienteId: 0 };
  }
}