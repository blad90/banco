package com.banco;

import com.banco.controlador.ClienteControlador;
import com.banco.dto.ClienteDTO;
import com.banco.excepciones.GlobalExceptionHandler;
import com.banco.excepciones.ResourceNotFoundException;
import com.banco.servicio.ClienteServicio;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@Import(GlobalExceptionHandler.class)
class ClienteControladorTest {

    private MockMvc mvc;
    private final ObjectMapper mapper = new ObjectMapper();

    @Mock ClienteServicio clienteServicio;
    @InjectMocks ClienteControlador clienteControlador;
    private ClienteDTO sample;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(clienteControlador)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        sample = new ClienteDTO(1L, "Juan Pérez","Masculino", 30, "00145678", "Av. Principal 123", "8091234567",
                "101", "pass1234", true);
    }

    @Test
    void getAll_returnsListAndOk() throws Exception {
        when(clienteServicio.obtenerClientes()).thenReturn(List.of(sample));
        mvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].clienteId").value("CL001"))
                .andExpect(jsonPath("$[0].nombre").value("Juan Pérez"));
    }

    @Test
    void getById_found_returnsOk() throws Exception {
        when(clienteServicio.obtenerCliente(1L)).thenReturn(sample);
        mvc.perform(get("/api/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clienteId").value("CL001"));
    }

    @Test
    void getById_notFound_returns404() throws Exception {
        when(clienteServicio.obtenerCliente(99L)).thenThrow(new ResourceNotFoundException("Cliente no encontrado con id: 99"));
        mvc.perform(get("/api/clientes/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Cliente no encontrado con id: 99"));
    }

    @Test
    void delete_existing_returns204() throws Exception {
        doNothing().when(clienteServicio).eliminarCliente(1L);
        mvc.perform(delete("/api/clientes/1"))
                .andExpect(status().isNoContent());
    }
}