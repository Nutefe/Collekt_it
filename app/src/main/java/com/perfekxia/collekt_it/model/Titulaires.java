package com.perfekxia.collekt_it.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Titulaires {
    @PrimaryKey()
    @NonNull
    private String IdTitulaire;

    @ColumnInfo(name="NomTitulaire")
    private String NomTitulaire;

    @ColumnInfo(name="PrenomTitulaire")
    private String PrenomTitulaire;

    @ColumnInfo(name="Sexe")
    private String Sexe;

    @ColumnInfo(name="DateEmbauche")
    private Date DateEmbauche;

    @ColumnInfo(name="Telephone")
    private String Telephone;

    @ColumnInfo(name="Messagerie")
    private String Messagerie;

    @ColumnInfo(name="Adresse")
    private String Adresse;

    @ColumnInfo(name="Photo")
    private String Photo;

    @ColumnInfo(name="PAP")
    private String PAP;

    @ColumnInfo(name="TelephonePAP")
    private String TelephonePAP;

    @ColumnInfo(name="Caution")
    private Integer Caution;

    @ColumnInfo(name= "Recupere")
    private Boolean Recupere;

    @ColumnInfo(name= "Nouveau")
    private Boolean Nouveau;

    public Titulaires() {
    }

    @NonNull
    public String getIdTitulaire() {
        return IdTitulaire;
    }

    public void setIdTitulaire(@NonNull String idTitulaire) {
        IdTitulaire = idTitulaire;
    }

    public String getNomTitulaire() {
        return NomTitulaire;
    }

    public void setNomTitulaire(String nomTitulaire) {
        NomTitulaire = nomTitulaire;
    }

    public String getPrenomTitulaire() {
        return PrenomTitulaire;
    }

    public void setPrenomTitulaire(String prenomTitulaire) {
        PrenomTitulaire = prenomTitulaire;
    }

    public String getSexe() {
        return Sexe;
    }

    public void setSexe(String sexe) {
        Sexe = sexe;
    }

    public Date getDateEmbauche() {
        return DateEmbauche;
    }

    public void setDateEmbauche(Date dateEmbauche) {
        DateEmbauche = dateEmbauche;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String tel) {
        Telephone = tel;
    }

    public String getMessagerie() {
        return Messagerie;
    }

    public void setMessagerie(String messagerie) {
        Messagerie = messagerie;
    }

    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String adresse) {
        Adresse = adresse;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getPAP() {
        return PAP;
    }

    public void setPAP(String PAP) {
        this.PAP = PAP;
    }

    public String getTelephonePAP() {
        return TelephonePAP;
    }

    public void setTelephonePAP(String telP) {
        TelephonePAP = telP;
    }

    public Integer getCaution() {
        return Caution;
    }

    public void setCaution(Integer caution) {
        Caution = caution;
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
        return "Titulaire{" +
                "IdTitulaire='" + IdTitulaire + '\'' +
                ", NomTitulaire='" + NomTitulaire + '\'' +
                ", PrenomTitulaire='" + PrenomTitulaire + '\'' +
                ", Sexe='" + Sexe + '\'' +
                ", DateEmbauche=" + DateEmbauche +
                ", Tel='" + Telephone + '\'' +
                ", Messagerie='" + Messagerie + '\'' +
                ", Adresse='" + Adresse + '\'' +
                ", Photo='" + Photo + '\'' +
                ", PAP='" + PAP + '\'' +
                ", TelP='" + TelephonePAP + '\'' +
                ", Caution=" + Caution +
                ", Recupere=" + Recupere +
                ", Nouveau=" + Nouveau +
                '}';
    }
}
