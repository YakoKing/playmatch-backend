package com.playmatch.backend.repository;

import com.playmatch.backend.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByUsuarioId(Long usuarioId);

    List<Reserva> findByPistaId(Long pistaId);

    List<Reserva> findByFechaPartido(LocalDate fecha);

    List<Reserva> findByPistaIdAndFechaPartido(Long pistaId, LocalDate fecha);

    List<Reserva> findByEstado(String estado);
}
