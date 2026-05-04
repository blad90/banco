import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClienteService } from '../../services/cliente-service';
import { FormsModule } from '@angular/forms';
import { RegistroClientesComponent } from "../registro-clientes-component/registro-clientes-component";

@Component({
  selector: 'app-clientes-component',
  standalone: true,
  imports: [CommonModule, FormsModule, RegistroClientesComponent],
  templateUrl: './clientes-component.html',
  styleUrl: './clientes-component.css',
})
export class ClientesComponent {

  clientes: ICliente[] = [];

  currentPage = 1;
  totalPages = 0;

  pages: number[] = [];

  mostrarFormulario = false;
  modoEdicion = false;
  clienteSeleccionado: ICliente = this.resetearDatos();

  page = 0;

  constructor(private clienteService: ClienteService) { }

  ngOnInit() {
    this.loadClientes();
  }

  loadClientes(page: number = 0) {
    this.clienteService.getClientes(page).subscribe(res => {
      this.clientes = res.content;
      this.page = res.number;

      this.currentPage = res.number + 1;
      this.totalPages = res.totalPages;

      this.updatePages();
    });
  }

  seleccionarCliente(cliente: ICliente) {
    this.clienteSeleccionado = cliente;
  }

  resetearDatos(): ICliente {
    return {
      id: null,
      nombre: '',
      genero: '',
      edad: 0,
      identificacion: '',
      direccion: '',
      telefono: '',
      clienteId: '',
      contrasena: '',
      estado: true
    };
  }

  mostrarCrear() {
    this.modoEdicion = false;
    this.clienteSeleccionado = this.resetearDatos();
    this.mostrarFormulario = true;
  }

  editar(cliente: ICliente) {
    this.modoEdicion = true;
    this.clienteSeleccionado = { ...cliente };
    this.mostrarFormulario = true;
  }

  guardar(cliente: ICliente) {
    if (this.modoEdicion) {
      this.clienteService.editarCliente(cliente).subscribe(() => {
        this.limpiarDatos();
      });
    } else {
      this.clienteService.crearCliente(cliente).subscribe(() => {
        this.limpiarDatos();
      });
    }
  }

  limpiarDatos() {
    this.loadClientes(this.page);
    this.cancelar();
  }

  cancelar() {
    this.mostrarFormulario = false;
    this.modoEdicion = false;
  }

  eliminar(cliente: ICliente) {
    if (!cliente.id) return;

    if (confirm(`Eliminar ${cliente.nombre}?`)) {
      this.clienteService.removerCliente(cliente.id).subscribe(() => {
        this.loadClientes(this.page);
      });
    }
  }

  updatePages() {
    this.pages = this.totalPages > 0
      ? Array.from({ length: this.totalPages }, (_, i) => i + 1)
      : [];
  }

  goToPage(page: number) {
    this.currentPage = page;
    this.loadClientes(page - 1);
  }

  nextPage() {
    if (this.currentPage < this.totalPages) {
      this.goToPage(this.currentPage + 1);
    }
  }

  prevPage() {
    if (this.currentPage > 1) {
      this.goToPage(this.currentPage - 1);
    }
  }
}