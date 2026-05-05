import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Movimiento } from '../common/movimiento';

@Injectable({
  providedIn: 'root',
})
export class MovimientoService {
  private baseUrl = `http://localhost:8081/api/movimientos`;

  constructor(private httpClient: HttpClient) {}

  getMovimientos(): Observable<Movimiento[]> { 
    return this.httpClient.get<Movimiento[]>(`${this.baseUrl}`); 
  }
  getMovimiento(id: number): Observable<Movimiento>  { 
    return this.httpClient.get<Movimiento>(`${this.baseUrl}/${id}`); 
  }
  createMovimiento(m: Movimiento): Observable<Movimiento> { 
    return this.httpClient.post<Movimiento>(`${this.baseUrl}`, m); 
  }
  deleteMovimiento(id: number): Observable<void>     { 
    return this.httpClient.delete<void>(`${this.baseUrl}/${id}`); 
  }
}
