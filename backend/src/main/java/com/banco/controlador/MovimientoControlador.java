package com.banco.controlador;

import com.banco.dto.MovimientoDTO;
import com.banco.service.MovimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    private final MovimientoServicio movimientoServicio;

    public MovimientoController(MovimientoServicio movimientoServicio) {
        this.movimientoServicio = movimientoServicio;
    }

    @GetMapping
    public ResponseEntity<List<MovimientoDTO>> getAll() {
        return ResponseEntity.ok(movimientoServicio.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(movimientoServicio.findById(id));
    }

    @GetMapping("/cuenta/{cuentaId}")
    public ResponseEntity<List<MovimientoDTO>> getByCuenta(@PathVariable Long cuentaId) {
        return ResponseEntity.ok(movimientoServicio.findByCuenta(cuentaId));
    }

    @PostMapping
    public ResponseEntity<MovimientoDTO> create(@Valid @RequestBody MovimientoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoServicio.create(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        movimientoServicio.delete(id);
        return ResponseEntity.noContent().build();
    }
}