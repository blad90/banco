package com.banco.servicio;

import com.banco.dto.ReporteDTO;

import java.time.LocalDate;
import java.util.List;

public interface IReporteServicio {
    List<ReporteDTO> generarReporte(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin);
    byte[] generarReportePdf(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin);
}
