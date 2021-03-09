package com.perfekxia.collekt_it.model;

import androidx.room.DatabaseView;

import java.io.Serializable;

@DatabaseView("SELECT p.IdCompte AS IdClient, c.IdClient AS OldID, c.CompteEp AS CompteEp, p.IdProduit AS Produit," +
               "c.DateAdhesion AS DateOuv, c.Nom AS Nom, p.CarnetC AS CarnetC, c.Prenom AS Prenom, c.Sexe AS Sexe," +
              "c.Profession AS Profession, c.ZoneClient AS ZoneC, c.Adresse AS Adresse, c.Messagerie AS Messagerie," +
              "c.Telephone AS Tel, c.Photo AS Photo, c.PAP AS PAP, c.TelephonePAP AS TelP, c.Recupere AS isRecup, c.Nouveau AS isNew FROM Clients c INNER JOIN Comptes p ON c.IdClient=p.IdClient")

public class ClientView implements Serializable {
    public String IdClient;
    public String OldID;
    public String CompteEp;
    public String Produit;
    public String DateOuv;
    public String Nom;
    public String CarnetC;
    public String Prenom;
    public String Sexe;
    public String Profession;
    public String ZoneC;
    public String Adresse;
    public String Messagerie;
    public String Tel;
    public String Photo;
    public String PAP;
    public String TelP;
    public Boolean isRecup;
    public Boolean isNew;

    public ClientView() {
    }
//    public ClientView(String idClient, String oldID, String compteEp, String produit, String dateOuv, String nom, String carnetC, String prenom, String sexe, String profession, String zoneC, String adresse, String messagerie, String tel, String photo, String PAP, String telP, Boolean isRecup, Boolean isNew) {
//        IdClient = idClient;
//        OldID = oldID;
//        CompteEp = compteEp;
//        Produit = produit;
//        DateOuv = dateOuv;
//        Nom = nom;
//        CarnetC = carnetC;
//        Prenom = prenom;
//        Sexe = sexe;
//        Profession = profession;
//        ZoneC = zoneC;
//        Adresse = adresse;
//        Messagerie = messagerie;
//        Tel = tel;
//        Photo = photo;
//        this.PAP = PAP;
//        TelP = telP;
//        this.isRecup = isRecup;
//        this.isNew = isNew;
//    }

    @Override
    public String toString() {
        return "ClientView{" +
                "IdClient='" + IdClient + '\'' +
                ", OldID='" + OldID + '\'' +
                ", CompteEp='" + CompteEp + '\'' +
                ", Produit='" + Produit + '\'' +
                ", DateOuv='" + DateOuv + '\'' +
                ", Nom='" + Nom + '\'' +
                ", CarnetC='" + CarnetC + '\'' +
                ", Prenom='" + Prenom + '\'' +
                ", Sexe='" + Sexe + '\'' +
                ", Profession='" + Profession + '\'' +
                ", ZoneC='" + ZoneC + '\'' +
                ", Adresse='" + Adresse + '\'' +
                ", Messagerie='" + Messagerie + '\'' +
                ", Tel='" + Tel + '\'' +
                ", Photo='" + Photo + '\'' +
                ", PAP='" + PAP + '\'' +
                ", TelP='" + TelP + '\'' +
                ", isRecup=" + isRecup +
                ", isNew=" + isNew +
                '}';
    }

    public String getIdClient() {
        return IdClient;
    }

    public void setIdClient(String idClient) {
        IdClient = idClient;
    }

    public String getOldID() {
        return OldID;
    }

    public void setOldID(String oldID) {
        OldID = oldID;
    }

    public String getCompteEp() {
        return CompteEp;
    }

    public void setCompteEp(String compteEp) {
        CompteEp = compteEp;
    }

    public String getProduit() {
        return Produit;
    }

    public void setProduit(String produit) {
        Produit = produit;
    }

    public String getDateOuv() {
        return DateOuv;
    }

    public void setDateOuv(String dateOuv) {
        DateOuv = dateOuv;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getCarnetC() {
        return CarnetC;
    }

    public void setCarnetC(String carnetC) {
        CarnetC = carnetC;
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

    public String getZoneC() {
        return ZoneC;
    }

    public void setZoneC(String zoneC) {
        ZoneC = zoneC;
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

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
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

    public String getTelP() {
        return TelP;
    }

    public void setTelP(String telP) {
        TelP = telP;
    }

    public Boolean getRecup() {
        return isRecup;
    }

    public void setRecup(Boolean recup) {
        isRecup = recup;
    }

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }
}
