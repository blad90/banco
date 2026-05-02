import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterModule } from '@angular/router';

@Component({
  selector: 'app-clientes-component',
  imports: [RouterLink, RouterLinkActive],
  standalone: true,
  templateUrl: './clientes-component.html',
  styleUrl: './clientes-component.css',
})
export class ClientesComponent {}
