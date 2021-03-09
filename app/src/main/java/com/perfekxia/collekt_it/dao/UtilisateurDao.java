package com.perfekxia.collekt_it.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.perfekxia.collekt_it.model.Utilisateurs;

import java.util.List;

@Dao
public interface UtilisateurDao {

    @Query("SELECT * FROM Utilisateurs")
    LiveData<List<Utilisateurs>> findAllUtilisateur();

    @Insert
    void insertUtilisateur(Utilisateurs utilisateur);

    @Insert
    void insertUtilisateurs(List<Utilisateurs> utilisateurs);

    @Update
    void updateUtilisateur(Utilisateurs utilisateur);

    @Query("SELECT * FROM Utilisateurs WHERE NomUtilisateur = :userLogin AND MotDePasse = :passWords")
    Utilisateurs findUserByUsernameAndPassword(String userLogin, String passWords);

    @Query("SELECT * FROM Utilisateurs WHERE Nouveau = 1")
    LiveData<List<Utilisateurs>> findUpdatedUser();

    @Query("UPDATE Utilisateurs SET MotDePasse = :password, Nouveau= :nouveau WHERE NomUtilisateur =:nomUtilisateur")
    void updatePassword(String password, Boolean nouveau, String nomUtilisateur);

    @Query("UPDATE Utilisateurs SET Pin = :pin, Nouveau= :nouveau WHERE NomUtilisateur =:nomUtilisateur")
    void updatePin(Integer pin, Boolean nouveau, String nomUtilisateur);

    @Query("DELETE FROM Utilisateurs")
    void deleteAllUtilisateur();

}
