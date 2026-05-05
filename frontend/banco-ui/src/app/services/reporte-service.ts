import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Reporte } from '../common/reporte';

@Injectable({
  providedIn: 'root',
})
export class ReporteService {
  private baseUrl = `http://localhost:8081/api/reportes`;

  constructor(private httpClient: HttpClient) {}

  getReporte(clienteId: number, fechaInicio: string, fechaFin: string): Observable<Reporte[]> {
    const params = new HttpParams()
      .set('clienteId', clienteId)
      .set('fechaInicio', fechaInicio)
      .set('fechaFin', fechaFin);
    return this.httpClient.get<Reporte[]>(`${this.baseUrl}`, { params });
  }

  getReportePdf(clienteId: number, fechaInicio: string, fechaFin: string): Observable<Blob> {
    const params = new HttpParams()
      .set('clienteId', clienteId)
      .set('fechaInicio', fechaInicio)
      .set('fechaFin', fechaFin);
    return this.httpClient.get(`${this.baseUrl}/pdf`, { params, responseType: 'blob' });
  }
}
