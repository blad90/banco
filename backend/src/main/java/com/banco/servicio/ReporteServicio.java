package com.banco.servicio;

import com.banco.dto.ReporteDTO;
import com.banco.entidad.Cliente;
import com.banco.entidad.Cuenta;
import com.banco.entidad.Movimiento;
import com.banco.excepciones.ResourceNotFoundException;
import com.banco.repositorio.IClienteRepositorio;
import com.banco.repositorio.IMovimientoRepositorio;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReporteServicio {
    private final IClienteRepositorio clienteRepositorio;
    private final IMovimientoRepositorio movimientoRepositorio;

    public ReporteServicio(IClienteRepositorio clienteRepositorio, IMovimientoRepositorio movimientoRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
        this.movimientoRepositorio = movimientoRepositorio;
    }

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public List<ReporteDTO> generarReporte(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin) {
        Cliente cliente = clienteRepositorio.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado: " + clienteId));

        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(LocalTime.MAX);

        List<Movimiento> movimientos = movimientoRepositorio.findByClienteAndFechas(clienteId, inicio, fin);

        List<ReporteDTO> reporte = new ArrayList<>();
        for (Movimiento m : movimientos) {
            Cuenta cuenta = m.getCuenta();
            ReporteDTO dto = ReporteDTO.builder()
                    .fecha(m.getFecha().format(FMT))
                    .cliente(cliente.getNombre())
                    .numeroCuenta(cuenta.getNumeroCuenta())
                    .tipo(cuenta.getTipoCuenta())
                    .saldoInicial(cuenta.getSaldoInicial())
                    .estado(cuenta.getEstado())
                    .movimiento(m.getValor())
                    .saldoDisponible(m.getSaldo())
                    .build();
            reporte.add(dto);
        }
        return reporte;
    }

    public byte[] generarReportePdf(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin) {
        List<ReporteDTO> datos = generarReporte(clienteId, fechaInicio, fechaFin);
        Cliente cliente = clienteRepositorio.findById(clienteId).orElseThrow();

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document doc = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(doc, baos);
            doc.open();

            // Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.DARK_GRAY);
            Paragraph title = new Paragraph("Estado de Cuenta Bancario", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            doc.add(title);
            doc.add(Chunk.NEWLINE);

            // Subtitle
            Font subFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            doc.add(new Paragraph("Cliente: " + cliente.getNombre(), subFont));
            doc.add(new Paragraph("Período: " + fechaInicio.format(FMT) + " - " + fechaFin.format(FMT), subFont));
            doc.add(Chunk.NEWLINE);

            // Table
            PdfPTable table = new PdfPTable(8);
            table.setWidthPercentage(100);
            float[] widths = {1.2f, 2f, 1.5f, 1.2f, 1.5f, 0.8f, 1.5f, 1.5f};
            table.setWidths(widths);

            String[] headers = {"Fecha", "Cliente", "N° Cuenta", "Tipo", "Saldo Inicial", "Estado", "Movimiento", "Saldo Disponible"};
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.WHITE);
            BaseColor headerBg = new BaseColor(41, 128, 185);

            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, headerFont));
                cell.setBackgroundColor(headerBg);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(6);
                table.addCell(cell);
            }

            Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 9);
            boolean alt = false;
            BigDecimal totalDebitos = BigDecimal.ZERO;
            BigDecimal totalCreditos = BigDecimal.ZERO;

            for (ReporteDTO row : datos) {
                BaseColor rowBg = alt ? new BaseColor(235, 245, 255) : BaseColor.WHITE;
                addCell(table, row.getFecha(), dataFont, rowBg);
                addCell(table, row.getCliente(), dataFont, rowBg);
                addCell(table, row.getNumeroCuenta(), dataFont, rowBg);
                addCell(table, row.getTipo(), dataFont, rowBg);
                addCell(table, formatMoney(row.getSaldoInicial()), dataFont, rowBg);
                addCell(table, row.getEstado() ? "Activa" : "Inactiva", dataFont, rowBg);
                addCell(table, formatMoney(row.getMovimiento()), dataFont, rowBg);
                addCell(table, formatMoney(row.getSaldoDisponible()), dataFont, rowBg);
                alt = !alt;

                if (row.getMovimiento().compareTo(BigDecimal.ZERO) < 0) {
                    totalDebitos = totalDebitos.add(row.getMovimiento().abs());
                } else {
                    totalCreditos = totalCreditos.add(row.getMovimiento());
                }
            }
            doc.add(table);
            doc.add(Chunk.NEWLINE);

            // Summary
            Font summaryFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
            doc.add(new Paragraph("Total Créditos: $" + totalCreditos, summaryFont));
            doc.add(new Paragraph("Total Débitos: $" + totalDebitos, summaryFont));

            doc.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF: " + e.getMessage(), e);
        }
    }

    private void addCell(PdfPTable table, String text, Font font, BaseColor bg) {
        PdfPCell cell = new PdfPCell(new Phrase(text != null ? text : "", font));
        cell.setBackgroundColor(bg);
        cell.setPadding(5);
        table.addCell(cell);
    }

    private String formatMoney(BigDecimal value) {
        if (value == null) return "0.00";
        return String.format("%.2f", value);
    }
}
