package com.playmatch.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "partidos")
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reserva_id")
    @JsonIgnoreProperties({"usuario"})
    private Reserva reserva;

    @NotBlank(message = "El título es obligatorio")
    @Column(nullable = false)
    private String titulo;

    @Positive(message = "Debe haber al menos 1 jugador máximo")
    @Column(name = "jugadores_max", nullable = false)
    private int jugadoresMax;

    @Column(nullable = false)
    private String estado = "abierto"; // abierto, completo, cancelado, finalizado

    @Column(name = "es_publica", nullable = false)
    private boolean esPublica = true;

    // Campos calculados (no persistidos, se rellenan en el servicio)
    @Transient
    private String organizador;

    @Transient
    private String pista;

    public Partido() {
    }

    // Getters y Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Reserva getReserva() { return reserva; }
    public void setReserva(Reserva reserva) { this.reserva = reserva; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public int getJugadoresMax() { return jugadoresMax; }
    public void setJugadoresMax(int jugadoresMax) { this.jugadoresMax = jugadoresMax; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public boolean isEsPublica() { return esPublica; }
    public void setEsPublica(boolean esPublica) { this.esPublica = esPublica; }

    public String getOrganizador() {
        if (reserva != null && reserva.getUsuario() != null) {
            return reserva.getUsuario().getNombre();
        }
        return organizador;
    }
    public void setOrganizador(String organizador) { this.organizador = organizador; }

    public String getPista() {
        if (reserva != null && reserva.getPista() != null) {
            return reserva.getPista().getNombre();
        }
        return pista;
    }
    public void setPista(String pista) { this.pista = pista; }
}
