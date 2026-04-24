package com.playmatch.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "participaciones",
       uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "partido_id"}))
public class Participacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnoreProperties({"password", "email"})
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "partido_id", nullable = false)
    @JsonIgnoreProperties({"reserva"})
    private Partido partido;

    @Column(nullable = false)
    private String estado = "confirmado"; // confirmado, lista_espera

    // Campo derivado
    @Transient
    private String nombreUsuario;

    public Participacion() {
    }

    // Getters y Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Partido getPartido() { return partido; }
    public void setPartido(Partido partido) { this.partido = partido; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getNombreUsuario() {
        if (usuario != null) {
            return usuario.getNombre();
        }
        return nombreUsuario;
    }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
}
