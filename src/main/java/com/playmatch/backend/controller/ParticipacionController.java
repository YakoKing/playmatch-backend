package com.playmatch.backend.controller;

import com.playmatch.backend.dto.ParticipacionRequest;
import com.playmatch.backend.entity.Participacion;
import com.playmatch.backend.service.ParticipacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/participaciones")
public class ParticipacionController {

    @Autowired
    private ParticipacionService participacionService;

    @GetMapping
    public ResponseEntity<List<Participacion>> getAll() {
        return ResponseEntity.ok(participacionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Participacion> getById(@PathVariable Long id) {
        return participacionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/partido/{partidoId}")
    public ResponseEntity<List<Participacion>> getByPartido(@PathVariable Long partidoId) {
        return ResponseEntity.ok(participacionService.findByPartidoId(partidoId));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Participacion>> getByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(participacionService.findByUsuarioId(usuarioId));
    }

    @PostMapping
    public ResponseEntity<?> unirse(@RequestBody ParticipacionRequest request) {
        try {
            Participacion participacion = participacionService.unirse(request);
            return ResponseEntity.ok(participacion);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/salir")
    public ResponseEntity<?> salir(@RequestParam Long usuarioId, @RequestParam Long partidoId) {
        try {
            participacionService.salir(usuarioId, partidoId);
            return ResponseEntity.ok(Map.of("mensaje", "Has salido del partido"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            participacionService.delete(id);
            return ResponseEntity.ok(Map.of("mensaje", "Participación eliminada correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
