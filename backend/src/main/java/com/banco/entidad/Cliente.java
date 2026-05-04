package com.banco.entidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cliente", uniqueConstraints = @UniqueConstraint(name = "uk_cliente_id", columnNames = "cliente_id"))
public class Cliente extends Persona{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El clienteId es requerido")
    @Size(max = 50)
    @Column(name = "cliente_id", nullable = false, unique = true, length = 50)
    private String clienteId;

    @NotBlank(message = "La contraseña es requerido")
    @Size(min = 8, max = 255, message = "La contraseña debe tener al menos 8 caracteres")
    @Column(nullable = false)
    private String contrasena;

    @Column(nullable = false)
    private Boolean estado = true;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cuenta> cuentas = new ArrayList<>();

    public Cliente(){}

    public Cliente(String nombre, String genero, int edad, String identificacion, String direccion, String telefono, Long id, String clienteId, String contrasena, Boolean estado, List<Cuenta> cuentas) {
        super(nombre, genero, edad, identificacion, direccion, telefono);
        this.id = id;
        this.clienteId = clienteId;
        this.contrasena = contrasena;
        this.estado = estado;
        this.cuentas = cuentas;
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
