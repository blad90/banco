package com.banco.controlador;

import com.banco.dto.CuentaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaControlador {

    @PostMapping("/crear")
    public ResponseEntity<String> crearCuenta(@RequestBody CuentaDTO cuentaDTO){
        return new ResponseEntity<>("Cuenta creada", HttpStatus.OK);
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<String> obtenerCuenta(@PathVariable Long id){
        return new ResponseEntity<>("Cuenta obtenida", HttpStatus.OK);
    }

    @GetMapping("/todos")
    public ResponseEntity<String> obtenerCuentas(){
        return new ResponseEntity<>("Cuenta obtenida", HttpStatus.OK);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<String> editarCuenta(@PathVariable Long id, CuentaDTO cuentaDTO){
        return new ResponseEntity<>("Cuenta editada", HttpStatus.OK);
    }

    @PatchMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizarCuenta(@PathVariable Long id, CuentaDTO cuentaDTO){
        return new ResponseEntity<>("Cuenta actualizada", HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarCuenta(@PathVariable Long id){
        return new ResponseEntity<>("Cuenta eliminada", HttpStatus.OK);
    }


}
