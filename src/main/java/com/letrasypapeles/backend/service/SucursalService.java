package com.letrasypapeles.backend.service;

import com.letrasypapeles.backend.entity.Sucursal;
import com.letrasypapeles.backend.repository.SucursalRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SucursalService {

    private final SucursalRepository sucursalRepository;

    public SucursalService(SucursalRepository sucursalRepository) {
        this.sucursalRepository = sucursalRepository;
    }

    public List<Sucursal> obtenerTodas() {
        return sucursalRepository.findAll();
    }

    public Optional<Sucursal> obtenerPorId(Long id) {
        return sucursalRepository.findById(id);
    }

    public Sucursal guardar(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }

    public void eliminar(Long id) {
        sucursalRepository.deleteById(id);
    }
}
