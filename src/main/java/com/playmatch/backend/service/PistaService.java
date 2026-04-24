package com.playmatch.backend.service;

import com.playmatch.backend.entity.Pista;
import com.playmatch.backend.repository.PistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PistaService {

    @Autowired
    private PistaRepository pistaRepository;

    public List<Pista> findAll() {
        return pistaRepository.findAll();
    }

    public Optional<Pista> findById(Long id) {
        return pistaRepository.findById(id);
    }

    public Pista save(Pista pista) {
        return pistaRepository.save(pista);
    }

    public Pista update(Long id, Pista datosActualizados) {
        return pistaRepository.findById(id)
                .map(pista -> {
                    if (datosActualizados.getNombre() != null) pista.setNombre(datosActualizados.getNombre());
                    if (datosActualizados.getFoto() != null) pista.setFoto(datosActualizados.getFoto());
                    if (datosActualizados.getPrecioHora() > 0) pista.setPrecioHora(datosActualizados.getPrecioHora());
                    if (datosActualizados.getUbicacion() != null) pista.setUbicacion(datosActualizados.getUbicacion());
                    if (datosActualizados.getCapacidadMax() > 0) pista.setCapacidadMax(datosActualizados.getCapacidadMax());
                    return pistaRepository.save(pista);
                })
                .orElseThrow(() -> new RuntimeException("Pista no encontrada con id: " + id));
    }

    public void delete(Long id) {
        if (!pistaRepository.existsById(id)) {
            throw new RuntimeException("Pista no encontrada con id: " + id);
        }
        pistaRepository.deleteById(id);
    }

    public List<Pista> findByUbicacion(String ubicacion) {
        return pistaRepository.findByUbicacionContainingIgnoreCase(ubicacion);
    }
}
