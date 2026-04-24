package com.playmatch.backend.controller;

import com.playmatch.backend.dto.ParticipacionRequest;
import com.playmatch.backend.entity.Participacion;
import com.playmatch.backend.service.ParticipacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/participaciones")
public class ParticipacionController {

    @Autowired
    private ParticipacionService participacionService;

    // GET /api/participaciones — Listar todas
    @GetMapping
    public ResponseEntity<List<Participacion>> getAll() {
        return ResponseEntity.ok(participacionService.findAll());
    }

    // GET /api/participaciones/{id} — Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Participacion> getById(@PathVariable Long id) {
        return participacionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/participaciones/partido/{partidoId} — Jugadores de un partido
    @GetMapping("/partido/{partidoId}")
    public ResponseEntity<List<Participacion>> getByPartido(@PathVariable Long partidoId) {
        return ResponseEntity.ok(participacionService.findByPartidoId(partidoId));
    }

    // GET /api/participaciones/usuario/{usuarioId} — Partidos de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Participacion>> getByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(participacionService.findByUsuarioId(usuarioId));
    }

    // POST /api/participaciones — Unirse a un partido
    @PostMapping
    public ResponseEntity<?> unirse(@RequestBody ParticipacionRequest request) {
        try {
            Participacion creada = participacionService.unirseAPartido(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // DELETE /api/participaciones/salir?usuarioId=1&partidoId=2 — Salir de un partido
    @DeleteMapping("/salir")
    public ResponseEntity<?> salir(@RequestParam Long usuarioId, @RequestParam Long partidoId) {
        try {
            participacionService.salirDePartido(usuarioId, partidoId);
            return ResponseEntity.ok(Map.of("mensaje", "Has salido del partido correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // DELETE /api/participaciones/{id} — Eliminar participación por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            participacionService.delete(id);
            return ResponseEntity.ok(Map.of("mensaje", "Participación eliminada correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
