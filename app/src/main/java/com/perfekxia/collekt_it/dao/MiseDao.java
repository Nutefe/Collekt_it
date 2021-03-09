package com.perfekxia.collekt_it.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.perfekxia.collekt_it.model.Mises;

import java.util.List;

@Dao
public interface MiseDao {

    @Query("SELECT * FROM Mises")
    LiveData<List<Mises>> findAllMise();

    @Query("SELECT * FROM Mises WHERE Nouveau=1")
    LiveData<List<Mises>> findAllNewMise();

    @Query("SELECT * FROM Mises WHERE NumeroMise = :NumMise")
    Mises findMiseById(Integer NumMise);

    @Query("SELECT * FROM Mises WHERE IdCycle = :NumeroCycle")
    LiveData<Mises> findMiseByCycle(String NumeroCycle);

    @Query("SELECT * FROM Mises WHERE IdCycle = :NumeroCycle")
    LiveData<List<Mises>> findAllMiseByCycle(String NumeroCycle);

    @Insert
    void insertMise(Mises mise);

    @Insert
    void insertMises(List<Mises> mises);

    @Update
    void updateMise(Mises mise);

    @Delete
    void deleteMise(Mises mise);

    @Query("DELETE FROM Mises")
    void deleteAllMise();

}
