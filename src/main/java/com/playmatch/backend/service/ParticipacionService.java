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
    private PartidoRepository partidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Participacion> findAll() {
        return participacionRepository.findAll();
    }

    public Optional<Participacion> findById(Long id) {
        return participacionRepository.findById(id);
    }

    public List<Participacion> findByPartidoId(Long partidoId) {
        return participacionRepository.findByPartidoId(partidoId);
    }

    public List<Participacion> findByUsuarioId(Long usuarioId) {
        return participacionRepository.findByUsuarioId(usuarioId);
    }

    public Participacion unirse(ParticipacionRequest request) {
        if (participacionRepository.existsByUsuarioIdAndPartidoId(request.getUsuarioId(), request.getPartidoId())) {
            throw new RuntimeException("El usuario ya está unido a este partido");
        }

        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + request.getUsuarioId()));

        Partido partido = partidoRepository.findById(request.getPartidoId())
                .orElseThrow(() -> new RuntimeException("Partido no encontrado con id: " + request.getPartidoId()));

        List<Participacion> confirmados = participacionRepository.findByPartidoIdAndEstado(partido.getId(), "confirmado");
        String estado = confirmados.size() < partido.getJugadoresMax() ? "confirmado" : "lista_espera";

        Participacion participacion = new Participacion();
        participacion.setUsuario(usuario);
        participacion.setPartido(partido);
        participacion.setEstado(estado);

        return participacionRepository.save(participacion);
    }

    public void salir(Long usuarioId, Long partidoId) {
        Participacion participacion = participacionRepository.findByUsuarioIdAndPartidoId(usuarioId, partidoId)
                .orElseThrow(() -> new RuntimeException("Participación no encontrada"));

        participacionRepository.delete(participacion);

        if ("confirmado".equals(participacion.getEstado())) {
            List<Participacion> enEspera = participacionRepository.findByPartidoIdAndEstado(partidoId, "lista_espera");
            if (!enEspera.isEmpty()) {
                Participacion primeroEnEspera = enEspera.get(0);
                primeroEnEspera.setEstado("confirmado");
                participacionRepository.save(primeroEnEspera);
            }
        }
    }

    public void delete(Long id) {
        if (!participacionRepository.existsById(id)) {
            throw new RuntimeException("Participación no encontrada con id: " + id);
        }
        participacionRepository.deleteById(id);
    }
}
