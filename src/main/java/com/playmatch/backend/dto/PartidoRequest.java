package com.playmatch.backend.dto;

public class PartidoRequest {

    private Long reservaId;
    private String titulo;
    private int jugadoresMax;
    private boolean esPublica;

    public Long getReservaId() { return reservaId; }
    public void setReservaId(Long reservaId) { this.reservaId = reservaId; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public int getJugadoresMax() { return jugadoresMax; }
    public void setJugadoresMax(int jugadoresMax) { this.jugadoresMax = jugadoresMax; }

    public boolean isEsPublica() { return esPublica; }
    public void setEsPublica(boolean esPublica) { this.esPublica = esPublica; }
}