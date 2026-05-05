import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { Cliente } from '../../common/cliente';
import { ClienteService } from '../../services/cliente-service';

@Component({
  selector: 'app-clientes',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './clientes-component.html',
})
export class ClientesComponent implements OnInit {
  clientes = signal<Cliente[]>([]);
  search = signal('');
  loading = signal(false);
  toast = signal<{type: string; msg: string} | null>(null);
  showModal = signal(false);
  editMode = signal(false);
  saving = signal(false);

  filtered = computed(() => {
    const q = this.search().toLowerCase();
    return this.clientes().filter(c =>
      c.nombre.toLowerCase().includes(q) ||
      c.clienteId.toLowerCase().includes(q) ||
      c.identificacion.toLowerCase().includes(q) ||
      c.telefono.includes(q)
    );
  });

  form: Cliente = this.emptyForm();

  constructor(private clienteService: ClienteService) {}

  ngOnInit() { this.load(); }

  load() {
    this.loading.set(true);
    this.clienteService.getClientes().subscribe({
      next: data => { this.clientes.set(data); this.loading.set(false); },
      error: () => { this.showToast('error', 'Error al cargar clientes'); this.loading.set(false); }
    });
  }

  openCreate() { this.form = this.emptyForm(); this.editMode.set(false); this.showModal.set(true); }

  openEdit(c: Cliente) { this.form = { ...c }; this.editMode.set(true); this.showModal.set(true); }

  save(f: NgForm) {
    if (f.invalid) { f.form.markAllAsTouched(); return; }
    this.saving.set(true);
    const obs = this.editMode()
      ? this.clienteService.updateCliente(this.form.id!, this.form)
      : this.clienteService.createCliente(this.form);
    obs.subscribe({
      next: () => { this.showToast('success', this.editMode() ? 'Cliente actualizado' : 'Cliente creado'); this.showModal.set(false); this.load(); this.saving.set(false); },
      error: (e) => { this.showToast('error', e.error?.message || 'Error al guardar'); this.saving.set(false); }
    });
  }

  delete(c: Cliente) {
    if (!confirm(`¿Eliminar al cliente "${c.nombre}"?`)) return;
    this.clienteService.deleteCliente(c.id!).subscribe({
      next: () => { this.showToast('success', 'Cliente eliminado'); this.load(); },
      error: (e) => this.showToast('error', e.error?.message || 'Error al eliminar')
    });
  }

  showToast(type: string, msg: string) {
    this.toast.set({ type, msg });
    setTimeout(() => this.toast.set(null), 3500);
  }

  emptyForm(): Cliente {
    return { clienteId: '', nombre: '', genero: 'Masculino', edad: 18, identificacion: '', direccion: '', telefono: '', contrasena: '', estado: true };
  }
}