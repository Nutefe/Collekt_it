package com.perfekxia.collekt_it.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
@Entity
public class Utilisateurs {
    @PrimaryKey()
    @NonNull
    @SerializedName("nomUtilisateur")
    private String NomUtilisateur;

    @ColumnInfo(name="MotDePasse")
    @SerializedName("motDePasse")
    private String MotDePasse;

    @ColumnInfo(name="Roles")
    @SerializedName("roles")
    private String Roles;

    @ColumnInfo(name="DateCreation")
    @SerializedName("dateCreation")
    private Date DateCreation;

    @ColumnInfo(name="Etat")
    @SerializedName("etat")
    private Boolean Etat;

    @ColumnInfo(name="NomComplet")
    @SerializedName("nomComplet")
    private String NomComplet;

    @ColumnInfo(name="Statut")
    @SerializedName("statut")
    private Boolean Statut;

    @ColumnInfo(name="IdTitulaire")
    @SerializedName("idTitulaire")
    private String IdTitulaire;

    @ColumnInfo(name= "Recupere")
    @SerializedName("recupere")
    private Boolean Recupere;

    @ColumnInfo(name= "Nouveau")
    @SerializedName("nouveau")
    private Boolean Nouveau;

    @ColumnInfo(name= "Pin")
    @SerializedName("pin")
    private Integer Pin;

    public Utilisateurs() {
    }

    @NonNull
    public String getNomUtilisateur() {
        return NomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateu) {
        NomUtilisateur = nomUtilisateu;
    }

    public String getMotDePasse() {
        return MotDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        MotDePasse = motDePasse;
    }

    public String getRoles() {
        return Roles;
    }

    public void setRoles(String roles) {
        Roles = roles;
    }

    public Date getDateCreation() {
        return DateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        DateCreation = dateCreation;
    }

    public Boolean getEtat() {
        return Etat;
    }

    public void setEtat(Boolean etat) {
        Etat = etat;
    }

    public String getNomComplet() {
        return NomComplet;
    }

    public void setNomComplet(String nomComplet) {
        NomComplet = nomComplet;
    }

    public Boolean getStatut() {
        return Statut;
    }

    public void setStatut(Boolean statut) {
        Statut = statut;
    }

    public String getIdTitulaire() {
        return IdTitulaire;
    }

    public void setIdTitulaire(String titulaire) {
        IdTitulaire = titulaire;
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

    public Integer getPin() {
        return Pin;
    }

    public void setPin(Integer pin) {
        Pin = pin;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "NomUtilisateur='" + NomUtilisateur + '\'' +
                ", MotDePasse='" + MotDePasse + '\'' +
                ", Roles='" + Roles + '\'' +
                ", DateCreation=" + DateCreation +
                ", Etat=" + Etat +
                ", NomComplet='" + NomComplet + '\'' +
                ", Statut=" + Statut +
                ", Titulaire='" + IdTitulaire + '\'' +
                ", Recupere=" + Recupere +
                ", Nouveau=" + Nouveau +
                ", Pin=" + Pin +
                '}';
    }
}
