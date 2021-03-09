package com.perfekxia.collekt_it.model;

import androidx.room.ColumnInfo;

public class LoginRequest {

    private String NomUtilisateur;

    private String MotDePasse;

    public LoginRequest() {
    }

    public LoginRequest(String nomUtilisateur, String motDePasse) {
        NomUtilisateur = nomUtilisateur;
        MotDePasse = motDePasse;
    }

    public String getNomUtilisateur() {
        return NomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        NomUtilisateur = nomUtilisateur;
    }

    public String getMotDePasse() {
        return MotDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        MotDePasse = motDePasse;
    }
}
