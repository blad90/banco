package com.banco.controlador;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaControlador {

    @PostMapping("crear")
    public ResponseEntity<String> crearCuenta(){
        return new ResponseEntity<>("Cuenta creada", HttpStatus.OK);
    }

    @GetMapping("obtener")
    public ResponseEntity<String> obtenerCuenta(){
        return new ResponseEntity<>("Cuenta obtenida", HttpStatus.OK);
    }
}
