package com.perfekxia.collekt_it.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.perfekxia.collekt_it.model.Comptes;

import java.util.List;

@Dao
public interface CompteDao {
    @Query("SELECT * FROM Comptes")
    LiveData<List<Comptes>> findAllCompte();

    @Query("SELECT * FROM Comptes WHERE IdCompte = :IdCompte")
    List<Comptes> findCompteByProduit(String IdCompte);

    @Insert
    void insertCompte(Comptes compte);

    @Insert
    void insertComptes(List<Comptes> comptes);

    @Update
    void updateCompte(Comptes compte);

    @Delete
    void deleteCompte(Comptes compte);

    @Query("DELETE FROM Comptes")
    void deleteAllCompte();
}
