package com.banco.controlador;

import com.banco.dto.MovimientoDTO;
import com.banco.servicio.IMovimientoServicio;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoControlador {

    private final IMovimientoServicio movimientoServicio;

    public MovimientoControlador(IMovimientoServicio movimientoServicio) {
        this.movimientoServicio = movimientoServicio;
    }

    @GetMapping
    public ResponseEntity<List<MovimientoDTO>> getAll() {
        return ResponseEntity.ok(movimientoServicio.obtenerMovimientos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(movimientoServicio.obtenerMovimiento(id));
    }

    @GetMapping("/cuenta/{cuentaId}")
    public ResponseEntity<List<MovimientoDTO>> getByCuenta(@PathVariable Long cuentaId) {
        return ResponseEntity.ok(movimientoServicio.obtenerMovimientoPorCuenta(cuentaId));
    }

    @PostMapping
    public ResponseEntity<MovimientoDTO> create(@Valid @RequestBody MovimientoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoServicio.crearMovimiento(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        movimientoServicio.eliminarMovimiento(id);
        return ResponseEntity.noContent().build();
    }
}