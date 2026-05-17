package com.playmatch.backend.service;

import com.playmatch.backend.dto.PartidoRequest;
import com.playmatch.backend.entity.Partido;
import com.playmatch.backend.entity.Reserva;
import com.playmatch.backend.repository.PartidoRepository;
import com.playmatch.backend.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartidoService {

    @Autowired
    private PartidoRepository partidoRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    public List<Partido> findAll() {
        return partidoRepository.findAll();
    }

    public Optional<Partido> findById(Long id) {
        return partidoRepository.findById(id);
    }

    public List<Partido> findByEstado(String estado) {
        return partidoRepository.findByEstado(estado);
    }

    public List<Partido> findPublicos() {
        return partidoRepository.findByEsPublicaTrue();
    }

    public Partido crear(PartidoRequest request) {
        Reserva reserva = reservaRepository.findById(request.getReservaId())
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con id: " + request.getReservaId()));

        Partido partido = new Partido();
        partido.setReserva(reserva);
        partido.setTitulo(request.getTitulo());
        partido.setJugadoresMax(request.getJugadoresMax());
        partido.setEsPublica(request.isEsPublica());

        return partidoRepository.save(partido);
    }

    public Partido actualizarEstado(Long id, String nuevoEstado) {
        return partidoRepository.findById(id)
                .map(partido -> {
                    partido.setEstado(nuevoEstado);
                    return partidoRepository.save(partido);
                })
                .orElseThrow(() -> new RuntimeException("Partido no encontrado con id: " + id));
    }

    public void delete(Long id) {
        if (!partidoRepository.existsById(id)) {
            throw new RuntimeException("Partido no encontrado con id: " + id);
        }
        partidoRepository.deleteById(id);
    }
}