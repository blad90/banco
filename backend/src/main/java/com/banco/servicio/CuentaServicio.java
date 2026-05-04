package com.banco.servicio;

import com.banco.dto.CuentaDTO;
import com.banco.entidad.Cliente;
import com.banco.entidad.Cuenta;
import com.banco.excepciones.BusinessException;
import com.banco.excepciones.ResourceNotFoundException;
import com.banco.repositorio.IClienteRepositorio;
import com.banco.repositorio.ICuentaRepositorio;
import com.banco.utils.CuentaMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CuentaServicio implements ICuentaServicio{
    private final ICuentaRepositorio cuentaRepositorio;
    private final IClienteRepositorio clienteRepositorio;

    public CuentaServicio(ICuentaRepositorio cuentaRepositorio, IClienteRepositorio clienteRepositorio) {
        this.cuentaRepositorio = cuentaRepositorio;
        this.clienteRepositorio = clienteRepositorio;
    }

    public CuentaDTO crearCuenta(CuentaDTO cuentaDTO) {
        if (cuentaRepositorio.existsByNumeroCuenta(cuentaDTO.getNumeroCuenta())) {
            throw new BusinessException("Ya existe una cuenta con el número: " + cuentaDTO.getNumeroCuenta());
        }
        Cliente cliente = clienteRepositorio.findById(cuentaDTO.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado: " + cuentaDTO.getClienteId()));
        Cuenta cuenta = CuentaMapper.toEntity(cuentaDTO, cliente);
        return CuentaMapper.toDTO(cuentaRepositorio.save(cuenta));
    }

    public CuentaDTO actualizarCuenta(Long id, CuentaDTO cuentaDTO) {
        Cuenta cuentaExistente = cuentaRepositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con id: " + id));
        cuentaRepositorio.findByNumeroCuenta(cuentaDTO.getNumeroCuenta()).ifPresent(c -> {
            if (!c.getId().equals(id)) throw new BusinessException("Número de cuenta ya existe!");
        });
        Cliente cliente = clienteRepositorio.findById(cuentaDTO.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado: " + cuentaDTO.getClienteId()));
        cuentaExistente.setNumeroCuenta(cuentaDTO.getNumeroCuenta());
        cuentaExistente.setTipoCuenta(cuentaDTO.getTipoCuenta());
        cuentaExistente.setSaldoInicial(cuentaDTO.getSaldoInicial());
        if (cuentaDTO.getEstado() != null) cuentaExistente.setEstado(cuentaDTO.getEstado());
        cuentaExistente.setCliente(cliente);
        return CuentaMapper.toDTO(cuentaRepositorio.save(cuentaExistente));
    }

    public void eliminarCuenta(Long id) {
        cuentaRepositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con el id: " + id));
        cuentaRepositorio.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<CuentaDTO> obtenerTodas() {
        return cuentaRepositorio.findAll().stream().map(CuentaMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public CuentaDTO obtenerCuenta(Long id) {
        return CuentaMapper.toDTO(cuentaRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con id: " + id)));
    }

    @Transactional(readOnly = true)
    public List<CuentaDTO> obtenerCuentaPorCliente(Long clienteId) {
        return cuentaRepositorio.findByClienteId(clienteId).stream().map(CuentaMapper::toDTO).toList();
    }
}
