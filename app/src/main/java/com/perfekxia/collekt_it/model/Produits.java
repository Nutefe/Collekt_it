package com.perfekxia.collekt_it.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity
public class Produits {
    @PrimaryKey()
    @NonNull
    @SerializedName("idProduit")
    private String IdProduit;

    @ColumnInfo(name="Libelle")
    @SerializedName("libelle")
    private String Libelle;

    @ColumnInfo(name="Description")
    @SerializedName("description")
    private String Description;

    @ColumnInfo(name= "DateDebut")
    @SerializedName("dateDebut")
    private Date DateDebut;

    @ColumnInfo(name= "Recupere")
    @SerializedName("recupere")
    private Boolean Recupere;

    @ColumnInfo(name= "Nouveau")
    @SerializedName("nouveau")
    private Boolean Nouveau;

    public Produits() {
    }

    @NonNull
    public String getIdProduit() {
        return IdProduit;
    }

    public void setIdProduit(@NonNull String idProduit) {
        IdProduit = idProduit;
    }

    public String getLibelle() {
        return Libelle;
    }

    public void setLibelle(String libelle) {
        Libelle = libelle;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Date getDateDebut() {
        return DateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        DateDebut = dateDebut;
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
        return Libelle;
    }
}
