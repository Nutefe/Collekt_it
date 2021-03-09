package com.perfekxia.collekt_it.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.perfekxia.collekt_it.model.Produits;

import java.util.List;

@Dao
public interface ProduitDao {

    @Query("SELECT * FROM Produits")
    LiveData<List<Produits>> findAllProduit();

    @Query("SELECT * FROM Produits WHERE IdProduit = :id")
    List<Produits> findProduitbyLibelle(String id);

    @Insert
    void insertProduit(Produits produit);

    @Insert
    void insertProduits(List<Produits> produits);

    @Update
    void updateProduit(Produits produit);

    @Query("DELETE FROM Produits")
    void deleteAllProduit();

}
