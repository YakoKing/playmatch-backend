package com.playmatch.backend.controller;

import com.playmatch.backend.entity.Pista;
import com.playmatch.backend.service.PistaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pistas")
public class PistaController {

    @Autowired
    private PistaService pistaService;

    // GET /api/pistas — Listar todas
    @GetMapping
    public ResponseEntity<List<Pista>> getAll() {
        return ResponseEntity.ok(pistaService.findAll());
    }

    // GET /api/pistas/{id} — Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pista> getById(@PathVariable Long id) {
        return pistaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/pistas/buscar?ubicacion=Murcia — Buscar por ubicación
    @GetMapping("/buscar")
    public ResponseEntity<List<Pista>> buscarPorUbicacion(@RequestParam String ubicacion) {
        return ResponseEntity.ok(pistaService.findByUbicacion(ubicacion));
    }

    // POST /api/pistas — Crear pista
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Pista pista) {
        try {
            Pista creada = pistaService.save(pista);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // PUT /api/pistas/{id} — Actualizar pista
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Pista pista) {
        try {
            Pista actualizada = pistaService.update(id, pista);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // DELETE /api/pistas/{id} — Eliminar pista
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            pistaService.delete(id);
            return ResponseEntity.ok(Map.of("mensaje", "Pista eliminada correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
