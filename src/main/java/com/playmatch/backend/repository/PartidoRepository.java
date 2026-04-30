package com.playmatch.backend.repository;

import com.playmatch.backend.entity.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidoRepository extends JpaRepository<Partido, Long> {

    List<Partido> findByEstado(String estado);

    List<Partido> findByEsPublicaTrue();

    List<Partido> findByReservaId(Long reservaId);

    List<Partido> findByReservaUsuarioId(Long usuarioId);

    List<Partido> findByReservaPistaId(Long pistaId);

    void deleteByReservaId(Long reservaId);
}
