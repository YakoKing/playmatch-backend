package com.playmatch.backend.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaRequest {
    private Long usuarioId;
    private Long pistaId;
    private LocalDate fechaPartido;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    public ReservaRequest() {}

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Long getPistaId() { return pistaId; }
    public void setPistaId(Long pistaId) { this.pistaId = pistaId; }

    public LocalDate getFechaPartido() { return fechaPartido; }
    public void setFechaPartido(LocalDate fechaPartido) { this.fechaPartido = fechaPartido; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
}
