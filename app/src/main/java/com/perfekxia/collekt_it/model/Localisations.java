package com.perfekxia.collekt_it.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Localisations {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Integer IdLocalisation;

    @ColumnInfo(name="Longitude")
    private Double Longitude;

    @ColumnInfo(name="Latitude")
    private Double Latitude;

    @ColumnInfo(name="IdCompte")
    private String IdCompte;

    public Localisations() {
    }

    @NonNull
    public Integer getIdLocalisation() {
        return IdLocalisation;
    }

    public void setIdLocalisation(@NonNull Integer idLocalisation) {
        IdLocalisation = idLocalisation;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public String getIdCompte() {
        return IdCompte;
    }

    public void setIdCompte(String idCompte) {
        IdCompte = idCompte;
    }

    @Override
    public String toString() {
        return "Localisations{" +
                "IdLocalisation=" + IdLocalisation +
                ", Longitude=" + Longitude +
                ", Latitude=" + Latitude +
                ", IdCompte='" + IdCompte + '\'' +
                '}';
    }
}
