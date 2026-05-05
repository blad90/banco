import { ClienteService } from '../../services/cliente-service';
import { MovimientoService } from '../../services/movimiento-service';
import it from '@angular/common/locales/it';
import { Cliente } from '../../common/cliente';
import { Movimiento } from '../../common/movimiento';
import { ReporteService } from '../../services/reporte-service';
import { CuentaService } from '../../services/cuenta-service';
import { HttpClientTestingModule, HttpTestingController, provideHttpClientTesting } from '@angular/common/types/http-testing';
import { TestBed } from '@angular/core/types/testing';
import { provideHttpClient } from '@angular/common/types/http';

describe('ClienteService', () => {
  let clienteService: ClienteService;
  let movimientoService: MovimientoService;
  let reporteService: ReporteService;
  let cuentaService: CuentaService;
  let http: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ClienteService, MovimientoService, ReporteService, CuentaService, provideHttpClient(), provideHttpClientTesting()]
    });
    clienteService = TestBed.inject(ClienteService);
    movimientoService = TestBed.inject(MovimientoService);
    reporteService = TestBed.inject(ReporteService);
    cuentaService = TestBed.inject(CuentaService);
    http = TestBed.inject(HttpTestingController);
  });

  afterEach(() => http.verify());

  it('should be created', () => {
    expect(clienteService).toBeTruthy();
  });

  it('getClientes() should GET /api/clientes', () => {
    const mock: Cliente[] = [
      { id: 1, clienteId: 'CL001', nombre: 'Juan', genero: 'M', edad: 30,
        identificacion: '001', direccion: 'Calle 1', telefono: '099', contrasena: 'pass', estado: true }
    ];
    clienteService.getClientes().subscribe(data => {
      expect(data.length).toBe(1);
      expect(data[0].clienteId).toBe('CL001');
    });
    const req = http.expectOne('/api/clientes');
    expect(req.request.method).toBe('GET');
    req.flush(mock);
  });

  it('createCliente() should POST to /api/clientes', () => {
    const newCliente: Cliente = { clienteId: 'CL002', nombre: 'Ana', genero: 'F', edad: 25,
      identificacion: '002', direccion: 'Av 2', telefono: '098', contrasena: '1234' };
    const resp: Cliente = { ...newCliente, id: 2 };
    clienteService.createCliente(newCliente).subscribe(data => {
      expect(data.id).toBe(2);
    });
    const req = http.expectOne('/api/clientes');
    expect(req.request.method).toBe('POST');
    expect(req.request.body.clienteId).toBe('CL002');
    req.flush(resp);
  });

  it('deleteCliente() should DELETE /api/clientes/:id', () => {
    clienteService.deleteCliente(1).subscribe(data => {
      expect(data).toBeUndefined();
    });
    const req = http.expectOne('/api/clientes/1');
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });

  it('createMovimiento() should POST with correct body', () => {
    const mov: Movimiento = { tipoMovimiento: 'CREDITO', valor: 500, cuentaId: 1 };
    const resp: Movimiento = { ...mov, id: 10, saldo: 1500 };
    movimientoService.createMovimiento(mov).subscribe(data => {
      expect(data.id).toBe(10);
      expect(data.saldo).toBe(1500);
    });
    const req = http.expectOne('/api/movimientos');
    expect(req.request.method).toBe('POST');
    expect(req.request.body.tipoMovimiento).toBe('CREDITO');
    req.flush(resp);
  });

  it('getReporte() should include query params', () => {
    reporteService.getReporte(1, '2023-01-01', '2023-12-31').subscribe();
    const req = http.expectOne(r => r.url === '/api/reportes');
    expect(req.request.params.get('clienteId')).toBe('1');
    expect(req.request.params.get('fechaInicio')).toBe('2023-01-01');
    req.flush([]);
  });

  it('updateCliente() should PUT to /api/clientes/:id', () => {
    const c: Cliente = { id: 1, clienteId: 'CL001', nombre: 'Juan', genero: 'M',
      edad: 31, identificacion: '001', direccion: 'Calle 1', telefono: '099', contrasena: 'pass' };
    clienteService.updateCliente(1, c).subscribe(data => expect(data.edad).toBe(31));
    const req = http.expectOne('/api/clientes/1');
    expect(req.request.method).toBe('PUT');
    req.flush(c);
  });

  it('getCuentas() should GET /api/cuentas', () => {
    cuentaService.getCuentas().subscribe(data => expect(Array.isArray(data)).toBe(true));
    const req = http.expectOne('/api/cuentas');
    expect(req.request.method).toBe('GET');
    req.flush([]);
  });
});