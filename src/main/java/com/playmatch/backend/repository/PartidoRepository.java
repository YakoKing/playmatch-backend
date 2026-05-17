package com.playmatch.backend.repository;

import com.playmatch.backend.entity.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidoRepository extends JpaRepository<Partido, Long> {

    List<Partido> findByReservaId(Long reservaId);

    List<Partido> findByEstado(String estado);

    List<Partido> findByEsPublicaTrue();
}