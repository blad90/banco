package com.banco.controlador;

import com.banco.dto.MovimientoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoControlador {

    @PostMapping("/crear")
    public ResponseEntity<String> crearMovimiento(@RequestBody MovimientoDTO movimientoDTO){
        return new ResponseEntity<>("Movimiento creado", HttpStatus.OK);
    }

    @PostMapping("/obtener/{id}")
    public ResponseEntity<String> obtenerMovimiento(@PathVariable Long id){
        return new ResponseEntity<>("Movimiento obtenido", HttpStatus.OK);
    }

    @PostMapping("/todos")
    public ResponseEntity<String> obtenerMovimientos(){
        return new ResponseEntity<>("Movimiento obtenido", HttpStatus.OK);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<String> editarMovimiento(@PathVariable Long id, MovimientoDTO movimientoDTO){
        return new ResponseEntity<>("Movimiento editado", HttpStatus.OK);
    }

    @PatchMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizarMovimiento(@PathVariable Long id, MovimientoDTO movimientoDTO){
        return new ResponseEntity<>("Movimiento actualizado", HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarMovimiento(@PathVariable Long id){
        return new ResponseEntity<>("Movimiento eliminado", HttpStatus.OK);
    }




}
