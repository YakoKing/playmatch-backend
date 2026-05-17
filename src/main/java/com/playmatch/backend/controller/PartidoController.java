package com.playmatch.backend.controller;

import com.playmatch.backend.dto.PartidoRequest;
import com.playmatch.backend.entity.Partido;
import com.playmatch.backend.service.PartidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/partidos")
public class PartidoController {

    @Autowired
    private PartidoService partidoService;

    @GetMapping
    public ResponseEntity<List<Partido>> getAll() {
        return ResponseEntity.ok(partidoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Partido> getById(@PathVariable Long id) {
        return partidoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/publicos")
    public ResponseEntity<List<Partido>> getPublicos() {
        return ResponseEntity.ok(partidoService.findPublicos());
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody PartidoRequest request) {
        try {
            Partido partido = partidoService.crear(request);
            return ResponseEntity.ok(partido);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            Partido partido = partidoService.actualizarEstado(id, body.get("estado"));
            return ResponseEntity.ok(partido);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            partidoService.delete(id);
            return ResponseEntity.ok(Map.of("mensaje", "Partido eliminado correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
