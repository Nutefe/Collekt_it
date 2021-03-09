package com.perfekxia.collekt_it.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity
public class Affectations {
    @PrimaryKey()
    @NonNull
    @SerializedName("idAffectation")
    private Integer IdAffectation;

    @ColumnInfo(name="TitulaireAffectation")
    @SerializedName("titulaireAffectation")
    private String TitulaireAffectation;

    @ColumnInfo(name= "ZoneAffectation")
    @SerializedName("zoneAffectation")
    private String ZoneAffectation;

    @ColumnInfo(name= "Motif")
    @SerializedName("motif")
    private String Motif;

    @ColumnInfo(name= "DateAffectation")
    @SerializedName("dateAffectation")
    private Date DateAffectation;

    @ColumnInfo(name= "Etat")
    @SerializedName("etat")
    private Boolean Etat;

    @ColumnInfo(name= "Recupere")
    @SerializedName("recupere")
    private Boolean Recupere;

    @ColumnInfo(name= "Nouveau")
    @SerializedName("nouveau")
    private Boolean Nouveau;

    public Affectations() {
    }


    @NonNull
    public Integer getIdAffectation() {
        return IdAffectation;
    }

    public void setIdAffectation(@NonNull Integer idAffectation) {
        IdAffectation = idAffectation;
    }

    public String getTitulaireAffectation() {
        return TitulaireAffectation;
    }

    public void setTitulaireAffectation(String titulaire) {
        TitulaireAffectation = titulaire;
    }

    public String getZoneAffectation() {
        return ZoneAffectation;
    }

    public void setZoneAffectation(String zoneA) {
        ZoneAffectation = zoneA;
    }

    public String getMotif() {
        return Motif;
    }

    public void setMotif(String motif) {
        Motif = motif;
    }

    public Date getDateAffectation() {
        return DateAffectation;
    }

    public void setDateAffectation(Date dateAffectation) {
        DateAffectation = dateAffectation;
    }

    public Boolean getEtat() {
        return Etat;
    }

    public void setEtat(Boolean etat) {
        Etat = etat;
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
        return "Affectation{" +
                "IdAffectation=" + IdAffectation +
                ", TitulaireAffectation='" + TitulaireAffectation + '\'' +
                ", ZoneAffectation='" + ZoneAffectation + '\'' +
                ", Motif='" + Motif + '\'' +
                ", DateAffectation='" + DateAffectation + '\'' +
                ", Etat=" + Etat +
                ", isRecup=" + Recupere +
                ", isNew=" + Nouveau +
                '}';
    }
}
