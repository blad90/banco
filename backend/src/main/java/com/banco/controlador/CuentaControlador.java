package com.banco.controlador;

import com.banco.dto.CuentaDTO;
import com.banco.servicio.CuentaServicio;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaControlador {

    private final CuentaServicio cuentaServicio;

    public CuentaControlador(CuentaServicio cuentaServicio) {
        this.cuentaServicio = cuentaServicio;
    }

    @GetMapping
    public ResponseEntity<List<CuentaDTO>> getAll() {
        return ResponseEntity.ok(cuentaServicio.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(cuentaServicio.obtenerCuenta(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<CuentaDTO>> getByCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(cuentaServicio.obtenerCuentaPorCliente(clienteId));
    }

    @PostMapping
    public ResponseEntity<CuentaDTO> create(@Valid @RequestBody CuentaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cuentaServicio.crearCuenta(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaDTO> update(@PathVariable Long id, @Valid @RequestBody CuentaDTO dto) {
        return ResponseEntity.ok(cuentaServicio.actualizarCuenta(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cuentaServicio.eliminarCuenta(id);
        return ResponseEntity.noContent().build();
    }
}
