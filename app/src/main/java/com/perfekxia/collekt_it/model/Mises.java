package com.perfekxia.collekt_it.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity
public class Mises {
    @PrimaryKey(autoGenerate= true)
    @NonNull
    @SerializedName("numeroMise")
    public Integer NumeroMise;

    @ColumnInfo(name="MontantMise")
    @SerializedName("montantMise")
    public Integer MontantMise;

    @ColumnInfo(name="DateMise")
    @SerializedName("dateMise")
    public Date DateMise;

    @ColumnInfo(name="IdClient")
    @SerializedName("idClient")
    public String IdClient;

    @ColumnInfo(name="Mois")
    @SerializedName("mois")
    public Integer Mois;

    @ColumnInfo(name="Annee")
    @SerializedName("annee")
    public Integer Annee;

    @ColumnInfo(name="IdCycle")
    @SerializedName("idCycle")
    public String IdCycle;

    @ColumnInfo(name= "IdTitulaire")
    @SerializedName("idTitulaire")
    public String IdTitulaire;

    @ColumnInfo(name= "IdUtilisateur")
    @SerializedName("idUtilisateur")
    public String IdUtilisateur;

    @ColumnInfo(name= "ZoneT")
    @SerializedName("zoneT")
    public String ZoneT;

    @ColumnInfo(name= "Recupere")
    @SerializedName("recupere")
    public Boolean Recupere;

    @ColumnInfo(name= "Nouveau")
    @SerializedName("nouveau")
    public Boolean Nouveau;

    public Mises() {
    }

    @NonNull
    public Integer getNumeroMise() {
        return NumeroMise;
    }

    public void setNumeroMise(@NonNull Integer numeroMise) {
        NumeroMise = numeroMise;
    }

    public Integer getMontantMise() {
        return MontantMise;
    }

    public void setMontantMise(Integer montantMise) {
        MontantMise = montantMise;
    }

    public Date getDateMise() {
        return DateMise;
    }

    public void setDateMise(Date dateMise) {
        DateMise = dateMise;
    }

    public String getClient() {
        return IdClient;
    }

    public void setClient(String client) {
        IdClient = client;
    }

    public Integer getMois() {
        return Mois;
    }

    public void setMois(Integer mois) {
        Mois = mois;
    }

    public Integer getAnnee() {
        return Annee;
    }

    public void setAnnee(Integer annee) {
        Annee = annee;
    }

    public String getCycle() {
        return IdCycle;
    }

    public void setCycle(String cycle) {
        IdCycle = cycle;
    }

    public String getTitulaire() {
        return IdTitulaire;
    }

    public void setTitulaire(String titulaire) {
        IdTitulaire = titulaire;
    }

    public String getUtilisateur() {
        return IdUtilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        IdUtilisateur = utilisateur;
    }

    public String getZoneT() {
        return ZoneT;
    }

    public void setZoneT(String zoneT) {
        ZoneT = zoneT;
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
        return "Mise{" +
                "NumeroMise=" + NumeroMise +
                ", MontantMise=" + MontantMise +
                ", DateMise=" + DateMise +
                ", Client='" + IdClient + '\'' +
                ", Mois=" + Mois +
                ", Annee=" + Annee +
                ", Cycle=" + IdCycle +
                ", Titulaire='" + IdTitulaire + '\'' +
                ", Utilisateur='" + IdUtilisateur + '\'' +
                ", ZoneT='" + ZoneT + '\'' +
                ", Recupere=" + Recupere +
                ", Nouveau=" + Nouveau +
                '}';
    }
}
