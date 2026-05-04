package com.banco.controlador;

import com.banco.dto.CuentaDTO;
import com.banco.servicio.ClienteServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    private final CuentaServicio cuentaServicio;

    public CuentaController(CuentaServicio cuentaServicio) {
        this.cuentaServicio = cuentaServicio;
    }

    @GetMapping
    public ResponseEntity<List<CuentaDTO>> getAll() {
        return ResponseEntity.ok(cuentaServicio.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(cuentaServicio.findById(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<CuentaDTO>> getByCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(cuentaServicio.findByCliente(clienteId));
    }

    @PostMapping
    public ResponseEntity<CuentaDTO> create(@Valid @RequestBody CuentaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cuentaServicio.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaDTO> update(@PathVariable Long id, @Valid @RequestBody CuentaDTO dto) {
        return ResponseEntity.ok(cuentaServicio.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cuentaServicio.delete(id);
        return ResponseEntity.noContent().build();
    }
}
