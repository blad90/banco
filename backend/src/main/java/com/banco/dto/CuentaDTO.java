package com.banco.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CuentaDTO {
    private Long id;

    @NotBlank(message = "El número de cuenta es requerido")
    private String numeroCuenta;

    @NotBlank(message = "El tipo de cuenta es requerido")
    private String tipoCuenta;

    @NotNull(message = "El saldo inicial es requerido")
    @DecimalMin("0.0")
    private BigDecimal saldoInicial;

    private BigDecimal saldoDisponible;
    private Boolean estado;

    @NotNull(message = "El cliente es requerido")
    private Long clienteId;

    private String clienteNombre;

    public CuentaDTO() {
    }

    public static CuentaDTO builder() {
        return new CuentaDTO();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public BigDecimal getSaldoDisponible() {
        return saldoDisponible;
    }

    public void setSaldoDisponible(BigDecimal saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    @Override
    public String toString() {
        return "CuentaDTO{" +
                "id=" + id +
                ", numeroCuenta='" + numeroCuenta + '\'' +
                ", tipoCuenta='" + tipoCuenta + '\'' +
                ", saldoInicial=" + saldoInicial +
                ", saldoDisponible=" + saldoDisponible +
                ", estado=" + estado +
                ", clienteId=" + clienteId +
                ", clienteNombre='" + clienteNombre + '\'' +
                '}';
    }
}
