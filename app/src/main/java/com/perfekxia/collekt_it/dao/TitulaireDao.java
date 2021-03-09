package com.perfekxia.collekt_it.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.perfekxia.collekt_it.model.Titulaires;

import java.util.List;

@Dao
public interface TitulaireDao {

    @Query("SELECT * FROM Titulaires")
    LiveData<List<Titulaires>> findAllTitulaire();

    @Query("SELECT * FROM Titulaires WHERE IdTitulaire = :IdTitulaire")
    Titulaires findTitulaireById(Integer IdTitulaire);

    @Insert
    void insertTitulaire(Titulaires titulaire);

    @Insert
    void insertTitulaires(List<Titulaires> titulaires);

    @Query("SELECT * FROM Titulaires WHERE NomTitulaire = :NomTit AND PrenomTitulaire = :PrenomTit")
    Titulaires findIdTitulaireByNameAndFirstName(String NomTit, String PrenomTit);

    @Update
    void updateTitulaire(Titulaires titulaire);
}
