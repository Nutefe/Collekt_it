package com.perfekxia.collekt_it.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity
public class Zones {
    @PrimaryKey()
    @NonNull
    @SerializedName("idZone")
    private String IdZone;

    @ColumnInfo(name="NomZone")
    @SerializedName("nomZone")
    private String NomZone;

    @ColumnInfo(name="Quartier")
    @SerializedName("quartier")
    private  String Quartier;

    @ColumnInfo(name="DateJ")
    @SerializedName("dateJ")
    private Date DateJ;

    @ColumnInfo(name= "Recupere")
    @SerializedName("recupere")
    private  Boolean Recupere;

    @ColumnInfo(name= "Nouveau")
    @SerializedName("nouveau")
    private  Boolean Nouveau;

    public Zones() {
    }

    @NonNull
    public String getIdZone() {
        return IdZone;
    }

    public void setIdZone(@NonNull String idZone) {
        IdZone = idZone;
    }

    public String getNomZone() {
        return NomZone;
    }

    public void setNomZone(String nomZone) {
        NomZone = nomZone;
    }

    public String getQuartier() {
        return Quartier;
    }

    public void setQuartier(String quartier) {
        Quartier = quartier;
    }

    public Date getDateJ() {
        return DateJ;
    }

    public void setDateJ(Date dateJ) {
        DateJ = dateJ;
    }

    public Boolean getRecupere() {
        return Recupere;
    }

    public void setRecupere(Boolean recupere) {
        Recupere = recupere;
    }

    public Boolean getNouveau() {
        return Nouveau;
    }

    public void setNouveau(Boolean nouveau) {
        Nouveau = nouveau;
    }

    @Override
    public String toString() {
        return "Zone{" +
                "IdZone='" + IdZone + '\'' +
                ", NomZone='" + NomZone + '\'' +
                ", Quartier='" + Quartier + '\'' +
                ", DateJ=" + DateJ +
                ", Recupere=" + Recupere +
                ", Nouveau=" + Nouveau +
                '}';
    }
}
