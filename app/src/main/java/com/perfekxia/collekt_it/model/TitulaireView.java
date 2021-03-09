package com.perfekxia.collekt_it.model;

import androidx.room.DatabaseView;

@DatabaseView("SELECT a.IdAffectation AS IdTitulaires," +
        " a.TitulaireAffectation AS OldID," +
        "a.ZoneAffectation AS ZoneTitulaires," +
        "u.NomUtilisateur AS LoginTitulaire," +
        "u.MotDePasse AS PassWordsTitulaire," +
        "u.NomComplet AS FullNameTitulaires," +
        "u.Pin AS TitulairesPin," +
        "u.roles AS TitulairesRole," +
        "z.NomZone AS TitulairesNomZ, " +
        "u.Statut AS Statut FROM Affectations a INNER JOIN Utilisateurs u ON a.TitulaireAffectation=u.IdTitulaire INNER JOIN Zones z ON a.ZoneAffectation=z.IdZone")

public class TitulaireView {
    public Integer IdTitulaires;
    public String OldID;
    public String ZoneTitulaires;
    public String LoginTitulaire;
    public String PassWordsTitulaire;
    public String FullNameTitulaires;
    public Integer TitulairesPin;
    public String TitulairesRole;
    public String TitulairesNomZ;
    public Boolean Statut;

//    public TitulaireView(Integer idTitulaires, String oldID, String zoneTitulaires, String loginTitulaire, String passWordsTitulaire, String fullNameTitulaires, Integer titulairesPin, String titulairesRole, String titulairesNomZ) {
//        IdTitulaires = idTitulaires;
//        OldID = oldID;
//        ZoneTitulaires = zoneTitulaires;
//        LoginTitulaire = loginTitulaire;
//        PassWordsTitulaire = passWordsTitulaire;
//        FullNameTitulaires = fullNameTitulaires;
//        TitulairesPin = titulairesPin;
//        TitulairesRole = titulairesRole;
//        TitulairesNomZ = titulairesNomZ;
//    }

    @Override
    public String toString() {
        return "TitulaireView{" +
                "IdTitulaires=" + IdTitulaires +
                ", OldID='" + OldID + '\'' +
                ", ZoneTitulaires='" + ZoneTitulaires + '\'' +
                ", LoginTitulaire='" + LoginTitulaire + '\'' +
                ", PassWordsTitulaire='" + PassWordsTitulaire + '\'' +
                ", FullNameTitulaires='" + FullNameTitulaires + '\'' +
                ", TitulairesPin=" + TitulairesPin +
                ", TitulairesRole='" + TitulairesRole + '\'' +
                ", TitulairesNomZ='" + TitulairesNomZ + '\'' +
                ", Statut='" + Statut + '\'' +
                '}';
    }
}
