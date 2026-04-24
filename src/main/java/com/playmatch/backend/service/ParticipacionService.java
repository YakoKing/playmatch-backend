package com.playmatch.backend.service;

import com.playmatch.backend.dto.ParticipacionRequest;
import com.playmatch.backend.entity.Participacion;
import com.playmatch.backend.entity.Partido;
import com.playmatch.backend.entity.Usuario;
import com.playmatch.backend.repository.ParticipacionRepository;
import com.playmatch.backend.repository.PartidoRepository;
import com.playmatch.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipacionService {

    @Autowired
    private ParticipacionRepository participacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PartidoRepository partidoRepository;

    public List<Participacion> findAll() {
        return participacionRepository.findAll();
    }

    public Optional<Participacion> findById(Long id) {
        return participacionRepository.findById(id);
    }

    public Participacion unirseAPartido(ParticipacionRequest request) {
        // Validar que el usuario existe
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + request.getUsuarioId()));

        // Validar que el partido existe
        Partido partido = partidoRepository.findById(request.getPartidoId())
                .orElseThrow(() -> new RuntimeException("Partido no encontrado con id: " + request.getPartidoId()));

        // Validar que no esté ya inscrito
        if (participacionRepository.existsByUsuarioIdAndPartidoId(request.getUsuarioId(), request.getPartidoId())) {
            throw new RuntimeException("El usuario ya está inscrito en este partido");
        }

        // Determinar si entra como confirmado o en lista de espera
        long confirmados = participacionRepository.countByPartidoIdAndEstado(request.getPartidoId(), "confirmado");

        Participacion participacion = new Participacion();
        participacion.setUsuario(usuario);
        participacion.setPartido(partido);

        if (confirmados < partido.getJugadoresMax()) {
            participacion.setEstado("confirmado");
        } else {
            participacion.setEstado("lista_espera");
        }

        return participacionRepository.save(participacion);
    }

    public void salirDePartido(Long usuarioId, Long partidoId) {
        Participacion participacion = participacionRepository
                .findByUsuarioIdAndPartidoId(usuarioId, partidoId)
                .orElseThrow(() -> new RuntimeException("El usuario no está inscrito en este partido"));

        participacionRepository.delete(participacion);

        // Si salió un confirmado, promover al primero de lista de espera
        if ("confirmado".equals(participacion.getEstado())) {
            List<Participacion> enEspera = participacionRepository.findByPartidoId(partidoId);
            enEspera.stream()
                    .filter(p -> "lista_espera".equals(p.getEstado()))
                    .findFirst()
                    .ifPresent(p -> {
                        p.setEstado("confirmado");
                        participacionRepository.save(p);
                    });
        }
    }

    public void delete(Long id) {
        if (!participacionRepository.existsById(id)) {
            throw new RuntimeException("Participación no encontrada con id: " + id);
        }
        participacionRepository.deleteById(id);
    }

    public List<Participacion> findByPartidoId(Long partidoId) {
        return participacionRepository.findByPartidoId(partidoId);
    }

    public List<Participacion> findByUsuarioId(Long usuarioId) {
        return participacionRepository.findByUsuarioId(usuarioId);
    }
}
