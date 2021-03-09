package com.perfekxia.collekt_it.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity
public class Cycles {
    @PrimaryKey()
    @NonNull
    @SerializedName("numeroCycle")
    private String NumeroCycle;

    @ColumnInfo(name="DateDebut")
    @SerializedName("dateDebut")
    private Date DateDebut;

    @ColumnInfo(name="DateFin")
    @SerializedName("dateFin")
    private Date DateFin;

    @ColumnInfo(name="Duree")
    @SerializedName("duree")
    private Integer Duree;

    @ColumnInfo(name="IdClient")
    @SerializedName("idClient")
    private String IdClient;

    @ColumnInfo(name="Mois")
    @SerializedName("mois")
    private Integer Mois;

    @ColumnInfo(name="TypeCycle")
    @SerializedName("typeCycle")
    private String TypeCycle;

    @ColumnInfo(name="Cloture")
    @SerializedName("cloture")
    private Boolean Cloture;

    @ColumnInfo(name= "Recupere")
    @SerializedName("recupere")
    private Boolean Recupere;

    @ColumnInfo(name= "Nouveau")
    @SerializedName("nouveau")
    private Boolean Nouveau;

    @ColumnInfo(name= "Valide")
    @SerializedName("valide")
    private Boolean Valide;

    public Cycles() {
    }

    @NonNull
    public String getNumeroCycle() {
        return NumeroCycle;
    }

    public void setNumeroCycle(@NonNull String numeroCycle) {
        NumeroCycle = numeroCycle;
    }

    public Date getDateDebut() {
        return DateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        DateDebut = dateDebut;
    }

    public Date getDateFin() {
        return DateFin;
    }

    public void setDateFin(Date dateFin) {
        DateFin = dateFin;
    }

    public Integer getDuree() {
        return Duree;
    }

    public void setDuree(Integer dure) {
        Duree = dure;
    }

    public String getIdClient() {
        return IdClient;
    }

    public void setIdClient(String client) {
        IdClient = client;
    }

    public Integer getMois() {
        return Mois;
    }

    public void setMois(Integer mois) {
        Mois = mois;
    }

    public String getTypeCycle() {
        return TypeCycle;
    }

    public void setTypeCycle(String typeCycle) {
        TypeCycle = typeCycle;
    }

    public Boolean getCloture() {
        return Cloture;
    }

    public void setCloture(Boolean cloture) {
        Cloture = cloture;
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

    public Boolean getValide() {
        return Valide;
    }

    public void setValide(Boolean valide) {
        Valide = valide;
    }

    @Override
    public String toString() {
        return "Cycle{" +
                "NumeroCycle=" + NumeroCycle +
                ", DateDebut=" + DateDebut +
                ", DateFin=" + DateFin +
                ", Dure=" + Duree +
                ", Client='" + IdClient + '\'' +
                ", Mois=" + Mois +
                ", TypeCycle='" + TypeCycle + '\'' +
                ", Cloture=" + Cloture +
                ", Recupere=" + Recupere +
                ", Nouveau=" + Nouveau +
                ", Valide=" + Valide +
                '}';
    }
}
