package com.playmatch.backend.controller;

import com.playmatch.backend.dto.PartidoRequest;
import com.playmatch.backend.entity.Partido;
import com.playmatch.backend.service.PartidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/partidos")
public class PartidoController {

    @Autowired
    private PartidoService partidoService;

    // GET /api/partidos — Listar todos
    @GetMapping
    public ResponseEntity<List<Partido>> getAll() {
        return ResponseEntity.ok(partidoService.findAll());
    }

    // GET /api/partidos/{id} — Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Partido> getById(@PathVariable Long id) {
        return partidoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/partidos/publicos — Solo partidos públicos
    @GetMapping("/publicos")
    public ResponseEntity<List<Partido>> getPublicos() {
        return ResponseEntity.ok(partidoService.findPublicos());
    }

    // GET /api/partidos/estado/{estado} — Filtrar por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Partido>> getByEstado(@PathVariable String estado) {
        return ResponseEntity.ok(partidoService.findByEstado(estado));
    }

    // POST /api/partidos — Crear partido
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody PartidoRequest request) {
        try {
            Partido creado = partidoService.crear(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // PUT /api/partidos/{id} — Actualizar partido
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody PartidoRequest request) {
        try {
            Partido actualizado = partidoService.update(id, request);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // PUT /api/partidos/{id}/estado — Cambiar estado
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            String nuevoEstado = body.get("estado");
            Partido actualizado = partidoService.actualizarEstado(id, nuevoEstado);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // DELETE /api/partidos/{id} — Eliminar partido
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            partidoService.delete(id);
            return ResponseEntity.ok(Map.of("mensaje", "Partido eliminado correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
