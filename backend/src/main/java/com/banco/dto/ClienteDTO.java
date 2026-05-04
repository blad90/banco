package com.banco.dto;

import jakarta.validation.constraints.*;

public class ClienteDTO {
    private Long id;

    @NotBlank(message = "El nombre es requerido")
    private String nombre;

    @NotBlank(message = "El género es requerido")
    private String genero;

    @NotNull(message = "La edad es requerida")
    @Min(18) @Max(70)
    private Integer edad;

    @NotBlank(message = "La identificación es requerida")
    private String identificacion;

    @NotBlank(message = "El clienteId es requerido")
    private String clienteId;

    @NotBlank(message = "La dirección es requerido")
    private String direccion;

    @NotBlank(message = "El teléfono es requerido")
    private String telefono;

    @NotBlank(message = "La contraseña es requerido")
    @Size(min = 8)
    private String contrasena;

    private Boolean estado;

    public ClienteDTO() {
    }

    public ClienteDTO(String nombre, String genero, int edad, String identificacion, String direccion, String telefono, String clienteId, String contrasena, boolean estado) {
        this.nombre = nombre;
        this.genero = genero;
        this.edad = edad;
        this.identificacion = identificacion;
        this.direccion = direccion;
        this.telefono = telefono;
        this.clienteId = clienteId;
        this.contrasena = contrasena;
        this.estado = estado;
    }

    public static ClienteDTO builder() {
        return new ClienteDTO();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "ClienteDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", genero='" + genero + '\'' +
                ", edad=" + edad +
                ", identificacion='" + identificacion + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", clienteId=" + clienteId +
                ", contrasena='" + contrasena + '\'' +
                ", estado=" + estado +
                '}';
    }
}
