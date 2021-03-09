package com.perfekxia.collekt_it.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity
public class Equipements {
    @PrimaryKey(autoGenerate= true)
    @NonNull
    @SerializedName("idEquipement")
    private Integer IdEquipement;

    @ColumnInfo(name="NumeroEquipement")
    @SerializedName("numeroEquipement")
    private String NumeroEquipement;

    @ColumnInfo(name= "Autorisation")
    @SerializedName("autorisation")
    private Boolean Autorisation;

    @ColumnInfo(name="DateCreation")
    @SerializedName("dateCreation")
    private Date DateCreation;

    @ColumnInfo(name="DateFin")
    @SerializedName("dateFin")
    private String DateFin;

    @ColumnInfo(name= "IdZone")
    @SerializedName("idZone")
    private String IdZone;

    @ColumnInfo(name= "IdTitulaire")
    @SerializedName("idTitulaire")
    private String IdTitulaire;

    @ColumnInfo(name= "Etat")
    @SerializedName("etat")
    private Boolean Etat;

    public Equipements() {
    }

    @NonNull
    public Integer getIdEquipement() {
        return IdEquipement;
    }

    public void setIdEquipement(@NonNull Integer idEquipement) {
        IdEquipement = idEquipement;
    }

    public String getNumeroEquipement() {
        return NumeroEquipement;
    }

    public void setNumeroEquipement(String numeroEquipement) {
        NumeroEquipement = numeroEquipement;
    }

    public Boolean getAutorisation() {
        return Autorisation;
    }

    public void setAutorisation(Boolean authorisation) {
        Autorisation = authorisation;
    }

    public Date getDateCreation() {
        return DateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        DateCreation = dateCreation;
    }

    public String getDateFin() {
        return DateFin;
    }

    public void setDateFin(String dateFin) {
        DateFin = dateFin;
    }

    public String getIdZone() {
        return IdZone;
    }

    public void setIdZone(String zone) {
        IdZone = zone;
    }

    public String getIdTitulaire() {
        return IdTitulaire;
    }

    public void setIdTitulaire(String idTitulaire) {
        IdTitulaire = idTitulaire;
    }

    public Boolean getEtat() {
        return Etat;
    }

    public void setEtat(Boolean etat) {
        Etat = etat;
    }

    @Override
    public String toString() {
        return "Equipement{" +
                "IdEquipement=" + IdEquipement +
                ", NumeroEquipement='" + NumeroEquipement + '\'' +
                ", Authorisation=" + Autorisation +
                ", DateCreation=" + DateCreation +
                ", DateFin='" + DateFin + '\'' +
                ", Zone='" + IdZone + '\'' +
                ", IdTitulaire='" + IdTitulaire + '\'' +
                ", Etat=" + Etat +
                '}';
    }
}
