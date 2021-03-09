package com.perfekxia.collekt_it.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity
public class Retraits {
    @PrimaryKey()
    @NonNull
    @SerializedName("numeroRetrait")
    private Integer NumeroRetrait;

    @ColumnInfo(name="DateRetrait")
    @SerializedName("dateRetrait")
    private Date DateRetrait;

    @ColumnInfo(name="MontantRetrait")
    @SerializedName("montantRetrait")
    private Integer MontantRetrait;

    @ColumnInfo(name="MoisRetrait")
    @SerializedName("moisRetrait")
    private Integer MoisRetrait;

    @ColumnInfo(name="Annee")
    @SerializedName("annee")
    private Integer Annee;

    @ColumnInfo(name="IdClient")
    @SerializedName("idClient")
    private String IdClient;

    @ColumnInfo(name="TypeRetrait")
    @SerializedName("typeRetrait")
    private String TypeRetrait;

    @ColumnInfo(name="IdUtilisateur")
    @SerializedName("idUtilisateur")
    private String IdUtilisateur;

    @ColumnInfo(name="ZoneT")
    @SerializedName("zoneT")
    private String ZoneT;

    @ColumnInfo(name="CompteEp")
    @SerializedName("compteEp")
    private String CompteEp;

    @ColumnInfo(name="Remboursement")
    @SerializedName("remboursement")
    private Boolean Remboursement;

    @ColumnInfo(name="Numero")
    @SerializedName("numero")
    private String Numero;

    @ColumnInfo(name="Recupere")
    @SerializedName("recupere")
    private Boolean Recupere;

    @ColumnInfo(name= "Nouveau")
    @SerializedName("nouveau")
    private Boolean Nouveau;

    public Retraits() {
    }

    @NonNull
    public Integer getNumeroRetrait() {
        return NumeroRetrait;
    }

    public void setNumeroRetrait(@NonNull Integer numeroRetrait) {
        NumeroRetrait = numeroRetrait;
    }

    public Date getDateRetrait() {
        return DateRetrait;
    }

    public void setDateRetrait(Date dateRetrait) {
        DateRetrait = dateRetrait;
    }

    public Integer getMontantRetrait() {
        return MontantRetrait;
    }

    public void setMontantRetrait(Integer montantRetrait) {
        MontantRetrait = montantRetrait;
    }

    public Integer getMoisRetrait() {
        return MoisRetrait;
    }

    public void setMoisRetrait(Integer moisRetrait) {
        MoisRetrait = moisRetrait;
    }

    public Integer getAnnee() {
        return Annee;
    }

    public void setAnnee(Integer annee) {
        Annee = annee;
    }

    public String getIdClient() {
        return IdClient;
    }

    public void setIdClient(String client) {
        IdClient = client;
    }

    public String getTypeRetrait() {
        return TypeRetrait;
    }

    public void setTypeRetrait(String typeRetrait) {
        TypeRetrait = typeRetrait;
    }

    public String getIdUtilisateur() {
        return IdUtilisateur;
    }

    public void setIdUtilisateur(String utilisateur) {
        IdUtilisateur = utilisateur;
    }

    public String getZoneT() {
        return ZoneT;
    }

    public void setZoneT(String zoneT) {
        ZoneT = zoneT;
    }

    public String getCompteEp() {
        return CompteEp;
    }

    public void setCompteEp(String compteEp) {
        CompteEp = compteEp;
    }

    public Boolean getRemboursement() {
        return Remboursement;
    }

    public void setRemboursement(Boolean remboursement) {
        Remboursement = remboursement;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String numero) {
        Numero = numero;
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
        return "Retrait{" +
                "NumeroRetrait=" + NumeroRetrait +
                ", DateRetrait=" + DateRetrait +
                ", MontantRetrait=" + MontantRetrait +
                ", MoisRetrait=" + MoisRetrait +
                ", Annee=" + Annee +
                ", Client='" + IdClient + '\'' +
                ", TypeRetrait='" + TypeRetrait + '\'' +
                ", Utilisateur='" + IdUtilisateur + '\'' +
                ", ZoneT='" + ZoneT + '\'' +
                ", CompteEp='" + CompteEp + '\'' +
                ", Remboursement=" + Remboursement +
                ", Numero=" + Numero +
                ", Recupere=" + Recupere +
                ", Nouveau=" + Nouveau +
                '}';
    }
}
