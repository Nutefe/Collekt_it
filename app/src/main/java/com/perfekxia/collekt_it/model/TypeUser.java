package com.perfekxia.collekt_it.model;

public class TypeUser {
    private int id;
    private  String libelle;

    public TypeUser() {
    }

    public TypeUser(int id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
