import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterModule } from '@angular/router';

@Component({
  selector: 'app-cuentas-component',
  imports: [RouterLink, RouterLinkActive],
  standalone: true,
  templateUrl: './cuentas-component.html',
  styleUrl: './cuentas-component.css',
})
export class CuentasComponent {}
