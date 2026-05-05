export interface Cuenta {
  id?: number;
  numeroCuenta: string;
  tipoCuenta: string;
  saldoInicial: number;
  saldoDisponible?: number;
  estado?: boolean;
  clienteId: number;
  clienteNombre?: string;
}