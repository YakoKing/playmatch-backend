package com.playmatch.backend.controller;

import com.playmatch.backend.dto.ReservaRequest;
import com.playmatch.backend.entity.Reserva;
import com.playmatch.backend.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    // GET /api/reservas — Listar todas
    @GetMapping
    public ResponseEntity<List<Reserva>> getAll() {
        return ResponseEntity.ok(reservaService.findAll());
    }

    // GET /api/reservas/{id} — Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> getById(@PathVariable Long id) {
        return reservaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/reservas/usuario/{usuarioId} — Reservas de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Reserva>> getByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(reservaService.findByUsuarioId(usuarioId));
    }

    // GET /api/reservas/pista/{pistaId} — Reservas de una pista
    @GetMapping("/pista/{pistaId}")
    public ResponseEntity<List<Reserva>> getByPista(@PathVariable Long pistaId) {
        return ResponseEntity.ok(reservaService.findByPistaId(pistaId));
    }

    // POST /api/reservas — Crear reserva
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody ReservaRequest request) {
        try {
            Reserva creada = reservaService.crear(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // PUT /api/reservas/{id}/estado — Cambiar estado (pendiente/pagado/cancelado)
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            String nuevoEstado = body.get("estado");
            Reserva actualizada = reservaService.actualizarEstado(id, nuevoEstado);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // DELETE /api/reservas/{id} — Eliminar reserva
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            reservaService.delete(id);
            return ResponseEntity.ok(Map.of("mensaje", "Reserva eliminada correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
