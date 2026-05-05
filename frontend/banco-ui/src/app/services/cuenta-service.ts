import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cuenta } from '../common/cuenta';

@Injectable({
  providedIn: 'root',
})
export class CuentaService {

  private baseUrl = `http://localhost:8081/api/cuentas`;

  constructor(private httpClient: HttpClient) { }

  getCuentas(): Observable<Cuenta[]>{ 
    return this.httpClient.get<Cuenta[]>(`${this.baseUrl}`); 
  }
  getCuenta(id: number): Observable<Cuenta>{ 
    return this.httpClient.get<Cuenta>(`${this.baseUrl}/${id}`); 
  }
  createCuenta(c: Cuenta): Observable<Cuenta>{ 
    return this.httpClient.post<Cuenta>(`${this.baseUrl}`, c); 
  }
  updateCuenta(id: number, c: Cuenta): Observable<Cuenta> { 
    return this.httpClient.put<Cuenta>(`${this.baseUrl}/${id}`, c); 
  }
  deleteCuenta(id: number): Observable<void>{ 
    return this.httpClient.delete<void>(`${this.baseUrl}/${id}`); }

}
