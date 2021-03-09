package com.perfekxia.collekt_it.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.perfekxia.collekt_it.model.Retraits;

import java.util.List;

@Dao
public interface RetraitDao {
    @Query("SELECT * FROM Retraits")
    LiveData<List<Retraits>> findAllRetrait();

    @Query("SELECT * FROM Retraits WHERE IdClient=:idClient")
    LiveData<List<Retraits>> findAllRetraitByComte(String idClient);

    @Insert
    void insertRetrait(Retraits Retrait);

    @Insert
    void insertRetraits(List<Retraits> retraits);

    @Update
    void updateRetrait(Retraits retrait);

    @Query("DELETE FROM Retraits")
    void deleteAllRetrait();

}
