package com.banco.controller;

import com.banco.dto.ReporteDTO;
import com.banco.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/reportes")
public class ReporteControlador {

    private final ReporteServicio reporteServicio;

    @GetMapping
    public ResponseEntity<List<ReporteDTO>> getReporte(
            @RequestParam Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam(defaultValue = "json") String formato) {

        if ("pdf".equalsIgnoreCase(formato) || "base64".equalsIgnoreCase(formato)) {
            byte[] pdf = reporteServicio.generarReportePdf(clienteId, fechaInicio, fechaFin);
            String base64 = Base64.getEncoder().encodeToString(pdf);
            Map<String, String> response = new HashMap<>();
            response.put("base64", base64);
            response.put("filename", "reporte_" + clienteId + "_" + fechaInicio + "_" + fechaFin + ".pdf");
            return ResponseEntity.ok().body(null); // handled below
        }

        return ResponseEntity.ok(reporteServicio.generarReporte(clienteId, fechaInicio, fechaFin));
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> getReportePdf(
            @RequestParam Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        byte[] pdf = reporteServicio.generarReportePdf(clienteId, fechaInicio, fechaFin);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=estado_cuenta_" + clienteId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/base64")
    public ResponseEntity<Map<String, String>> getReporteBase64(
            @RequestParam Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        byte[] pdf = reporteServicio.generarReportePdf(clienteId, fechaInicio, fechaFin);
        Map<String, String> response = new HashMap<>();
        response.put("base64", Base64.getEncoder().encodeToString(pdf));
        response.put("filename", "estado_cuenta_" + clienteId + ".pdf");
        return ResponseEntity.ok(response);
    }
}