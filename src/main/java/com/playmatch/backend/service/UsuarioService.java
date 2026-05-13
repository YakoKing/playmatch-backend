package com.playmatch.backend.service;

import com.playmatch.backend.dto.LoginRequest;
import com.playmatch.backend.entity.Reserva;
import com.playmatch.backend.entity.Usuario;
import com.playmatch.backend.repository.ReservaRepository;
import com.playmatch.backend.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario save(Usuario usuario) {
        // Validar que no exista email duplicado
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con ese email");
        }
        // Validar que no exista nombre duplicado
        if (usuarioRepository.existsByNombre(usuario.getNombre())) {
            throw new RuntimeException("Ya existe un usuario con ese nombre");
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario update(Long id, Usuario datosActualizados) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    if (datosActualizados.getNombre() != null) {
                        usuario.setNombre(datosActualizados.getNombre());
                    }
                    if (datosActualizados.getEmail() != null) {
                        usuario.setEmail(datosActualizados.getEmail());
                    }
                    if (datosActualizados.getTelefono() != null) {
                        usuario.setTelefono(datosActualizados.getTelefono());
                    }
                    if (datosActualizados.getAvatarUrl() != null) {
                        usuario.setAvatarUrl(datosActualizados.getAvatarUrl());
                    }
                    if (datosActualizados.getPassword() != null && !datosActualizados.getPassword().isEmpty()) {
                        usuario.setPassword(datosActualizados.getPassword());
                    }
                    if (datosActualizados.getPosicion() !=null ){
                        usuario.setPosicion(datosActualizados.getPosicion());
                    }
                    if(datosActualizados.getEdad()>0){
                        usuario.setEdad(datosActualizados.getEdad());
                    }
                    return usuarioRepository.save(usuario);

                })
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    @Transactional
    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }

        //Borrar reservas del usuario
        List<Reserva> reservas = reservaRepository.findByUsuarioId(id);
        reservaRepository.deleteAll(reservas);

        //Borrar el usuario
        usuarioRepository.deleteById(id);
    }

    public Optional<Usuario> login(LoginRequest loginRequest) {
        return usuarioRepository.findByNombreAndPassword(
                loginRequest.getNombre(),
                loginRequest.getPassword()
        );
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}
