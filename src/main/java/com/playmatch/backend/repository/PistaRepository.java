package com.playmatch.backend.repository;

import com.playmatch.backend.entity.Pista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PistaRepository extends JpaRepository<Pista, Long> {

    List<Pista> findByUbicacionContainingIgnoreCase(String ubicacion);

    List<Pista> findByPrecioHoraLessThanEqual(double precioMax);
}
