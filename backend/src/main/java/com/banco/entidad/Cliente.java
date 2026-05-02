package com.banco.entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cliente")
public class Cliente extends Persona{
    private Long clienteId;
    private String contrasena;
    private boolean estado;

    public Cliente(){}

    public Cliente(Long id, String nombre, String genero, int edad, String identificacion, String direccion, String telefono, Long clienteId, String contrasena, boolean estado, List<Cuenta> cuentas) {
        super(id, nombre, genero, edad, identificacion, direccion, telefono);
        this.clienteId = clienteId;
        this.contrasena = contrasena;
        this.estado = estado;
        this.cuentas = cuentas;
    }

    public Cliente(Long id, String nombre, String genero, int edad, String identificacion, String direccion, String telefono, Long clienteId, String contrasena, boolean estado) {
        super(id, nombre, genero, edad, identificacion, direccion, telefono);
        this.clienteId = clienteId;
        this.contrasena = contrasena;
        this.estado = estado;
    }

    @OneToMany
    private List<Cuenta> cuentas;

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
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

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return estado == cliente.estado && Objects.equals(clienteId, cliente.clienteId) && Objects.equals(contrasena, cliente.contrasena) && Objects.equals(cuentas, cliente.cuentas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clienteId, contrasena, estado, cuentas);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "clienteId=" + clienteId +
                ", contrasena='" + contrasena + '\'' +
                ", estado=" + estado +
                ", cuentas=" + cuentas +
                '}';
    }
}
