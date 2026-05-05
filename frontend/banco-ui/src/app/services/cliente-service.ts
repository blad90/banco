import { Injectable } from '@angular/core';
import { map, Observable, retry } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Cliente } from '../common/cliente';


@Injectable({
  providedIn: 'root',
})
export class ClienteService {
  private baseUrl = `http://localhost:8081/api/clientes`;

  constructor(private httpClient: HttpClient) { }

  getClientes(): Observable<Cliente[]>{ 
    return this.httpClient.get<Cliente[]>(`${this.baseUrl}`); 
  }

  getCliente(id: number): Observable<Cliente>{ 
    return this.httpClient.get<Cliente>(`${this.baseUrl}/${id}`); 
  }
  
  createCliente(c: Cliente): Observable<Cliente> { 
    return this.httpClient.post<Cliente>(`${this.baseUrl}`, c); 
  }
  updateCliente(id: number, c: Cliente): Observable<Cliente> { 
    return this.httpClient.put<Cliente>(`${this.baseUrl}/${id}`, c); 
  }
  deleteCliente(id: number): Observable<void>        { 
    return this.httpClient.delete<void>(`${this.baseUrl}/${id}`); 
  }
}