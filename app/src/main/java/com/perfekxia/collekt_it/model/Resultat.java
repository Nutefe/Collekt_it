package com.perfekxia.collekt_it.model;


public class Resultat {

    Boolean status;

    public Resultat(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Resultat{" +
                "status=" + status +
                '}';
    }
}
