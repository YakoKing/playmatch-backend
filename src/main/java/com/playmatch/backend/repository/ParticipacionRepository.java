package com.playmatch.backend.repository;

import com.playmatch.backend.entity.Participacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipacionRepository extends JpaRepository<Participacion, Long> {

    List<Participacion> findByPartidoId(Long partidoId);

    List<Participacion> findByUsuarioId(Long usuarioId);

    Optional<Participacion> findByUsuarioIdAndPartidoId(Long usuarioId, Long partidoId);

    long countByPartidoIdAndEstado(Long partidoId, String estado);

    boolean existsByUsuarioIdAndPartidoId(Long usuarioId, Long partidoId);

    void deleteByUsuarioId(Long usuarioId);

    void deleteByPartidoId(Long partidoId);
}
