package com.banco.controlador;

import com.banco.dto.ClienteDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteControlador {

    @PostMapping("/crear")
    public ResponseEntity<String> crearCliente(@RequestBody ClienteDTO clienteDTO){
        return new ResponseEntity<>("Cliente creado", HttpStatus.OK);
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<String> obtenerCliente(@PathVariable Long id){
        return new ResponseEntity<>("Cliente obtenido", HttpStatus.OK);
    }

    @GetMapping("/todos")
    public ResponseEntity<String> obtenerClientes(){
        return new ResponseEntity<>("Cliente obtenido", HttpStatus.OK);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<String> editarCliente(@PathVariable Long id, ClienteDTO clienteDTO){
        return new ResponseEntity<>("Cliente editado", HttpStatus.OK);
    }

    @PatchMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizarCliente(@PathVariable Long id, ClienteDTO clienteDTO){
        return new ResponseEntity<>("Cliente actualizado", HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable Long id){
        return new ResponseEntity<>("Cliente eliminado", HttpStatus.OK);
    }
}
