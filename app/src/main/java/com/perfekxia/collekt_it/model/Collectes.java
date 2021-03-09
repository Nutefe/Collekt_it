package com.perfekxia.collekt_it.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity
public class Collectes {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @SerializedName("numeroCollecte")
    private Integer NumeroCollecte;

    @ColumnInfo(name="DateCollecte")
    @SerializedName("dateCollecte")
    private Date DateCollecte;

    @ColumnInfo(name="MontantCollecte")
    @SerializedName("montantCollecte")
    private Integer MontantCollecte;

    @ColumnInfo(name="IdTitulaire")
    @SerializedName("idTitulaire")
    private String IdTitulaire;

    @ColumnInfo(name="ZoneT")
    @SerializedName("zoneT")
    private String ZoneT;

    @ColumnInfo(name="IdClient")
    @SerializedName("idClient")
    private String IdClient;

    @ColumnInfo(name="MoisCollecte")
    @SerializedName("moisCollecte")
    private Integer MoisCollecte;

    @ColumnInfo(name="IdCycle")
    @SerializedName("idCycle")
    private String IdCycle;

    @ColumnInfo(name="Carnet")
    @SerializedName("carnet")
    private String Carnet;

    @ColumnInfo(name="Utilisateur")
    @SerializedName("idUtilisateur")
    private String Utilisateur;

    @ColumnInfo(name="Recupere")
    @SerializedName("recupere")
    private Boolean Recupere;

    @ColumnInfo(name="Nouveau")
    @SerializedName("nouveau")
    private Boolean Nouveau;

    @ColumnInfo(name="Manquant")
    @SerializedName("manquant")
    private Boolean Manquant;

    public Collectes() {
    }

    @NonNull
    public Integer getNumeroCollecte() {
        return NumeroCollecte;
    }

    public void setNumeroCollecte(@NonNull Integer numeroCollecte) {
        NumeroCollecte = numeroCollecte;
    }

    public Date getDateCollecte() {
        return DateCollecte;
    }

    public void setDateCollecte(Date dateCollecte) {
        DateCollecte = dateCollecte;
    }

    public Integer getMontantCollecte() {
        return MontantCollecte;
    }

    public void setMontantCollecte(Integer montantCollecte) {
        MontantCollecte = montantCollecte;
    }

    public String getIdTitulaire() {
        return IdTitulaire;
    }

    public void setIdTitulaire(String titulaire) {
        IdTitulaire = titulaire;
    }

    public String getZoneT() {
        return ZoneT;
    }

    public void setZoneT(String zoneT) {
        ZoneT = zoneT;
    }

    public String getIdClient() {
        return IdClient;
    }

    public void setIdClient(String client) {
        IdClient = client;
    }

    public Integer getMoisCollecte() {
        return MoisCollecte;
    }

    public void setMoisCollecte(Integer moisCollecte) {
        MoisCollecte = moisCollecte;
    }

    public String getIdCycle() {
        return IdCycle;
    }

    public void setIdCycle(String cycle) {
        IdCycle = cycle;
    }

    public String getCarnet() {
        return Carnet;
    }

    public void setCarnet(String carnet) {
        Carnet = carnet;
    }

    public String getUtilisateur() {
        return Utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        Utilisateur = utilisateur;
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

    public Boolean getManquant() {
        return Manquant;
    }

    public void setManquant(Boolean manquant) {
        Manquant = manquant;
    }

    @Override
    public String toString() {
        return "Collecte{" +
                "NumeroCollecte=" + NumeroCollecte +
                ", DateCollecte='" + DateCollecte + '\'' +
                ", MontantCollecte=" + MontantCollecte +
                ", Titulaire='" + IdTitulaire + '\'' +
                ", ZoneT='" + ZoneT + '\'' +
                ", Client='" + IdClient + '\'' +
                ", MoisCollecte=" + MoisCollecte +
                ", Cycle=" + IdCycle +
                ", Carnet='" + Carnet + '\'' +
                ", Utilisateur='" + Utilisateur + '\'' +
                ", Recupere=" + Recupere +
                ", Nouveau=" + Nouveau +
                ", Manquant=" + Manquant +
                '}';
    }
}
