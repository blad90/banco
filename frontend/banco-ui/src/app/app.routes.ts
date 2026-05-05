import { Routes } from '@angular/router';
import { ClientesComponent } from './components/clientes-component/clientes-component';
import { CuentasComponent } from './components/cuentas-component/cuentas-component';
import { MovimientosComponent } from './components/movimientos-component/movimientos-component';
import { ReportesComponent } from './components/reportes-component/reportes-component';

export const routes: Routes = [
    { path: '', redirectTo: '/clientes', pathMatch: 'full' },
    { path: 'clientes', component: ClientesComponent },
    { path: 'cuentas', component: CuentasComponent },
    { path: 'movimientos', component: MovimientosComponent },
    { path: 'reportes', component: ReportesComponent },
    { path: '**', redirectTo: '/clientes' }
];


