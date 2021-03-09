package com.perfekxia.collekt_it.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity
public class Clients {
    @PrimaryKey()
    @NonNull
    @SerializedName("idClient")
    private String IdClient;

    @ColumnInfo(name= "Nom")
    @SerializedName("nom")
    private String Nom;

    @ColumnInfo(name= "Prenom")
    @SerializedName("prenom")
    private String Prenom;

    @ColumnInfo(name= "Sexe")
    @SerializedName("sexe")
    private String Sexe;

    @ColumnInfo(name= "Profession")
    @SerializedName("profession")
    private String Profession;

    @ColumnInfo(name= "ZoneClient")
    @SerializedName("zoneClient")
    private String ZoneClient;

    @ColumnInfo(name= "Adresse")
    @SerializedName("adresse")
    private String Adresse;

    @ColumnInfo(name="Messagerie")
    @SerializedName("messagerie")
    private String Messagerie;

    @ColumnInfo(name="Telephone")
    @SerializedName("telephone")
    private String Telephone;

    @ColumnInfo(name="DateAdhesion")
    @SerializedName("dateAdhesion")
    private Date DateAdhesion;

    @ColumnInfo(name="Photo")
    @SerializedName("photo")
    private String Photo;

    @ColumnInfo(name="PAP")
    @SerializedName("pap")
    private String PAP;

    @ColumnInfo(name="TelephonePAP")
    @SerializedName("telephonePap")
    private String TelephonePAP;

    @ColumnInfo(name="CompteEp")
    @SerializedName("compteEp")
    private String CompteEp;

    @ColumnInfo(name="Recupere")
    @SerializedName("recupere")
    private Boolean Recupere;

    @ColumnInfo(name="Nouveau")
    @SerializedName("nouveau")
    private Boolean Nouveau;

    @ColumnInfo(name="longitude")
    @SerializedName("longitude")
    private Double Longitude;

    @ColumnInfo(name="latitude")
    @SerializedName("latitude")
    private Double Latitude;

    public Clients() {
    }

    @NonNull
    public String getIdClient() {
        return IdClient;
    }

    public void setIdClient(@NonNull String idClient) {
        IdClient = idClient;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getSexe() {
        return Sexe;
    }

    public void setSexe(String sexe) {
        Sexe = sexe;
    }

    public String getProfession() {
        return Profession;
    }

    public void setProfession(String profession) {
        Profession = profession;
    }

    public String getZoneClient() {
        return ZoneClient;
    }

    public void setZoneClient(String zoneC) {
        ZoneClient = zoneC;
    }

    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String adresse) {
        Adresse = adresse;
    }

    public String getMessagerie() {
        return Messagerie;
    }

    public void setMessagerie(String messagerie) {
        Messagerie = messagerie;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String tel) {
        Telephone = tel;
    }

    public Date getDateAdhesion() {
        return DateAdhesion;
    }

    public void setDateAdhesion(Date dateAdhesion) {
        DateAdhesion = dateAdhesion;
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

    public String getCompteEp() {
        return CompteEp;
    }

    public void setCompteEp(String compteEp) {
        CompteEp = compteEp;
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

    @Override
    public String toString() {
        return "Clients{" +
                "IdClient='" + IdClient + '\'' +
                ", Nom='" + Nom + '\'' +
                ", Prenom='" + Prenom + '\'' +
                ", Sexe='" + Sexe + '\'' +
                ", Profession='" + Profession + '\'' +
                ", ZoneClient='" + ZoneClient + '\'' +
                ", Adresse='" + Adresse + '\'' +
                ", Messagerie='" + Messagerie + '\'' +
                ", Telephone='" + Telephone + '\'' +
                ", DateAdhesion=" + DateAdhesion +
                ", Photo='" + Photo + '\'' +
                ", PAP='" + PAP + '\'' +
                ", TelephonePAP='" + TelephonePAP + '\'' +
                ", CompteEp='" + CompteEp + '\'' +
                ", Recupere=" + Recupere +
                ", Nouveau=" + Nouveau +
                ", Longitude=" + Longitude +
                ", Latitude=" + Latitude +
                '}';
    }
}
