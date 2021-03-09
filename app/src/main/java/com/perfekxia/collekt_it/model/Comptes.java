package com.perfekxia.collekt_it.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity
public class Comptes {
    @PrimaryKey()
    @NonNull
    @SerializedName("idCompte")
    private String IdCompte;

    @ColumnInfo(name="IdClient")
    @SerializedName("idClient")
    private String IdClient;

    @ColumnInfo(name="DateOuverture")
    @SerializedName("dateOuverture")
    private Date DateOuverture;

    @ColumnInfo(name="IdProduit")
    @SerializedName("idProduit")
    private String IdProduit;

    @ColumnInfo(name="CarnetC")
    @SerializedName("carnetC")
    private String CarnetC;

    @ColumnInfo(name= "Recupere")
    @SerializedName("recupere")
    private Boolean Recupere;

    @ColumnInfo(name= "Nouveau")
    @SerializedName("nouveau")
    private Boolean Nouveau;

    public Comptes() {
    }

    @NonNull
    public String getIdCompte() {
        return IdCompte;
    }

    public void setIdCompte(@NonNull String idCompte) {
        IdCompte = idCompte;
    }

    public String getIdClient() {
        return IdClient;
    }

    public void setIdClient(String client) {
        IdClient = client;
    }

    public Date getDateOuverture() {
        return DateOuverture;
    }

    public void setDateOuverture(Date dateOuverture) {
        DateOuverture = dateOuverture;
    }

    public String getIdProduit() {
        return IdProduit;
    }

    public void setIdProduit(String produit) {
        IdProduit = produit;
    }

    public String getCarnetC() {
        return CarnetC;
    }

    public void setCarnetC(String carnetC) {
        CarnetC = carnetC;
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
        return "Compte{" +
                "IdCompte='" + IdCompte + '\'' +
                ", Client='" + IdClient + '\'' +
                ", DateOuverture='" + DateOuverture + '\'' +
                ", Produit='" + IdProduit + '\'' +
                ", CarnetC='" + CarnetC + '\'' +
                ", Recupere=" + Recupere +
                ", Nouveau=" + Nouveau +
                '}';
    }
}
