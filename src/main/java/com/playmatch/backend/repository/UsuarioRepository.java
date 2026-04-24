package com.playmatch.backend.repository;

import com.playmatch.backend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByNombre(String nombre);

    Optional<Usuario> findByNombreAndPassword(String nombre, String password);

    boolean existsByEmail(String email);

    boolean existsByNombre(String nombre);
}
