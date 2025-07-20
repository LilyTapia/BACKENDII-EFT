package com.letrasypapeles.backend.service;

import com.letrasypapeles.backend.entity.Notificacion;
import com.letrasypapeles.backend.repository.NotificacionRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;

    public NotificacionService(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    public List<Notificacion> obtenerTodas() {
        return notificacionRepository.findAll();
    }

    public Optional<Notificacion> obtenerPorId(Long id) {
        return notificacionRepository.findById(id);
    }

    public Notificacion guardar(Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }

    public void eliminar(Long id) {
        notificacionRepository.deleteById(id);
    }

    public List<Notificacion> obtenerPorClienteId(Long clienteId) {
        return notificacionRepository.findByClienteId(clienteId);
    }

    public List<Notificacion> obtenerPorFechaEntre(LocalDateTime inicio, LocalDateTime fin) {
        return notificacionRepository.findByFechaBetween(inicio, fin);
    }
}
