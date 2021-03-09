package com.perfekxia.collekt_it.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"NomUtilisateur"},
        unique = true)})
public class Login {

    @PrimaryKey(autoGenerate= true)
    @NonNull
    private Integer IdLogin;

    @ColumnInfo(name="NomUtilisateur")
    private String NomUtilisateur;

    @ColumnInfo(name="MotDePasse")
    private String MotDePasse;

    @ColumnInfo(name="Pin")
    private Integer Pin;

    @ColumnInfo(name="NumeroEquipement")
    private String NumeroEquipement;

    @ColumnInfo(name="IdZone")
    private String IdZone;

    @ColumnInfo(name="NomZone")
    private String NomZone;

    @ColumnInfo(name="IdTitulaire")
    private String IdTitulaire;

    @ColumnInfo(name="Role")
    private String Role;

    @ColumnInfo(name="NomComplet")
    private String NomComplet;

    @ColumnInfo(name="DateConnexion")
    private String DateConnexion;

    @ColumnInfo(name="DateDebutCycle")
    private String DateDebutCycle;

    @ColumnInfo(name="DateFinCycle")
    private String DateFinCycle;

    @ColumnInfo(name="Actif")
    private Boolean Actif;

    public Login() {
    }

    @NonNull
    public Integer getIdLogin() {
        return IdLogin;
    }

    public void setIdLogin(@NonNull Integer idLogin) {
        IdLogin = idLogin;
    }

    public String getNomUtilisateur() {
        return NomUtilisateur;
    }

    public void setNomUtilisateur(String loginUtilisateur) {
        NomUtilisateur = loginUtilisateur;
    }

    public String getMotDePasse() {
        return MotDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        MotDePasse = motDePasse;
    }

    public Integer getPin() {
        return Pin;
    }

    public void setPin(Integer pin) {
        Pin = pin;
    }

    public String getNumeroEquipement() {
        return NumeroEquipement;
    }

    public void setNumeroEquipement(String numeroEquipement) {
        NumeroEquipement = numeroEquipement;
    }

    public String getIdZone() {
        return IdZone;
    }

    public void setIdZone(String idZone) {
        IdZone = idZone;
    }

    public String getNomZone() {
        return NomZone;
    }

    public void setNomZone(String nomZone) {
        NomZone = nomZone;
    }

    public String getIdTitulaire() {
        return IdTitulaire;
    }

    public void setIdTitulaire(String titulaire) {
        IdTitulaire = titulaire;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getNomComplet() {
        return NomComplet;
    }

    public void setNomComplet(String nomComplet) {
        NomComplet = nomComplet;
    }

    public String getDateConnexion() {
        return DateConnexion;
    }

    public void setDateConnexion(String dateConnexion) {
        DateConnexion = dateConnexion;
    }

    public String getDateDebutCycle() {
        return DateDebutCycle;
    }

    public void setDateDebutCycle(String dateDebutCycle) {
        DateDebutCycle = dateDebutCycle;
    }

    public String getDateFinCycle() {
        return DateFinCycle;
    }

    public void setDateFinCycle(String dateFinCycle) {
        DateFinCycle = dateFinCycle;
    }

    public Boolean getActif() {
        return Actif;
    }

    public void setActif(Boolean actif) {
        Actif = actif;
    }

    @Override
    public String toString() {
        return "Login{" +
                "IdLogin=" + IdLogin +
                ", LoginUtilisateur='" + NomUtilisateur + '\'' +
                ", MotDePasse='" + MotDePasse + '\'' +
                ", Pin=" + Pin +
                ", NumeroEquipement='" + NumeroEquipement + '\'' +
                ", IdZone='" + IdZone + '\'' +
                ", NomZone='" + NomZone + '\'' +
                ", Titulaire='" + IdTitulaire + '\'' +
                ", Role='" + Role + '\'' +
                ", NomComplet='" + NomComplet + '\'' +
                ", DateConnexion='" + DateConnexion + '\'' +
                ", DateDebutCycle='" + DateDebutCycle + '\'' +
                ", DateFinCycle='" + DateFinCycle + '\'' +
                ", Actif=" + Actif +
                '}';
    }
}
