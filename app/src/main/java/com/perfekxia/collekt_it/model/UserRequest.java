package com.perfekxia.collekt_it.model;

public class UserRequest {
    private String NomUtilisateur;
    private Integer Pin;
    private String MotDePasse;

    public UserRequest() {
    }

    public String getNomUtilisateur() {
        return NomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        NomUtilisateur = nomUtilisateur;
    }

    public Integer getPin() {
        return Pin;
    }

    public void setPin(Integer pin) {
        Pin = pin;
    }

    public String getMotDePasse() {
        return MotDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        MotDePasse = motDePasse;
    }

    public UserRequest(String nomUtilisateur, Integer pin, String motDePasse) {
        NomUtilisateur = nomUtilisateur;
        Pin = pin;
        MotDePasse = motDePasse;
    }
}
