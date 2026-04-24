package com.playmatch.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "pistas")
public class Pista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la pista es obligatorio")
    @Column(nullable = false)
    private String nombre;

    private String foto;

    @Positive(message = "El precio debe ser positivo")
    @Column(name = "precio_hora", nullable = false)
    private double precioHora;

    @NotBlank(message = "La ubicación es obligatoria")
    @Column(nullable = false)
    private String ubicacion;

    @Positive(message = "La capacidad debe ser positiva")
    @Column(name = "capacidad_max", nullable = false)
    private int capacidadMax;

    public Pista() {
    }

    public Pista(String nombre, double precioHora, String ubicacion, int capacidadMax) {
        this.nombre = nombre;
        this.precioHora = precioHora;
        this.ubicacion = ubicacion;
        this.capacidadMax = capacidadMax;
    }

    // Getters y Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }

    public double getPrecioHora() { return precioHora; }
    public void setPrecioHora(double precioHora) { this.precioHora = precioHora; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public int getCapacidadMax() { return capacidadMax; }
    public void setCapacidadMax(int capacidadMax) { this.capacidadMax = capacidadMax; }
}
