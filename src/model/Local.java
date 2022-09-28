/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author guilherme.oliveira
 */
public class Local {
    private int id;
    private String nome_maquina;
    private LocalDateTime data;
    private double latitude;
    private double longitude;

    public Local() {
    }

    public Local(int id, String nome_maquina, LocalDateTime data, double latitude, double longitude) {
        this.id = id;
        this.nome_maquina = nome_maquina;
        this.data = data;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome_maquina() {
        return nome_maquina;
    }

    public void setNome_maquina(String nome_maquina) {
        this.nome_maquina = nome_maquina;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
