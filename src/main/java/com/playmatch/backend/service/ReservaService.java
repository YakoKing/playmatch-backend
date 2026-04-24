package com.playmatch.backend.service;

import com.playmatch.backend.dto.ReservaRequest;
import com.playmatch.backend.entity.Pista;
import com.playmatch.backend.entity.Reserva;
import com.playmatch.backend.entity.Usuario;
import com.playmatch.backend.repository.PistaRepository;
import com.playmatch.backend.repository.ReservaRepository;
import com.playmatch.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PistaRepository pistaRepository;

    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> findById(Long id) {
        return reservaRepository.findById(id);
    }

    public Reserva crear(ReservaRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + request.getUsuarioId()));

        Pista pista = pistaRepository.findById(request.getPistaId())
                .orElseThrow(() -> new RuntimeException("Pista no encontrada con id: " + request.getPistaId()));

        // Validar que no haya solapamiento de horarios en la misma pista y fecha
        List<Reserva> reservasExistentes = reservaRepository
                .findByPistaIdAndFechaPartido(request.getPistaId(), request.getFechaPartido());

        for (Reserva existente : reservasExistentes) {
            if (!"cancelado".equals(existente.getEstado())) {
                boolean solapa = request.getHoraInicio().isBefore(existente.getHoraFin())
                        && request.getHoraFin().isAfter(existente.getHoraInicio());
                if (solapa) {
                    throw new RuntimeException("Ya existe una reserva en esa pista para ese horario");
                }
            }
        }

        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setPista(pista);
        reserva.setFechaPartido(request.getFechaPartido());
        reserva.setHoraInicio(request.getHoraInicio());
        reserva.setHoraFin(request.getHoraFin());
        reserva.setEstado("pendiente");

        return reservaRepository.save(reserva);
    }

    public Reserva actualizarEstado(Long id, String nuevoEstado) {
        return reservaRepository.findById(id)
                .map(reserva -> {
                    reserva.setEstado(nuevoEstado);
                    return reservaRepository.save(reserva);
                })
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con id: " + id));
    }

    public void delete(Long id) {
        if (!reservaRepository.existsById(id)) {
            throw new RuntimeException("Reserva no encontrada con id: " + id);
        }
        reservaRepository.deleteById(id);
    }

    public List<Reserva> findByUsuarioId(Long usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId);
    }

    public List<Reserva> findByPistaId(Long pistaId) {
        return reservaRepository.findByPistaId(pistaId);
    }
}
