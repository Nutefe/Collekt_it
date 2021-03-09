package com.perfekxia.collekt_it.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.perfekxia.collekt_it.model.Cycles;

import java.util.List;

@Dao
public interface CycleDao {


    @Query("SELECT * FROM Cycles")
    LiveData<List<Cycles>> findAllCycle();


    @Query("SELECT * FROM Cycles WHERE Nouveau = 1")
    LiveData<List<Cycles>> findAllNewCycle();

    @Query("SELECT * FROM Cycles WHERE Cloture = 0")
    LiveData<List<Cycles>> findAllCycleCloture();

    @Query("SELECT * FROM Cycles WHERE Cloture = 1")
    LiveData<List<Cycles>> findAllCycleEnCours();

    @Query("SELECT * FROM Cycles WHERE Cloture = 1 AND Nouveau = 0")
    LiveData<List<Cycles>> findAllOldCycleEnCours();

    @Query("SELECT * FROM Cycles WHERE NumeroCycle = :Numero")
    List<Cycles> findCycleById(Integer Numero);

    @Query("SELECT * FROM Cycles WHERE IdClient = :Numero AND Cloture = 1")
    LiveData<Cycles> findCycleByAccount(String Numero);

    @Query("SELECT * FROM Cycles WHERE IdClient = :Numero")
    LiveData<List<Cycles>> findAllCycleByAccount(String Numero);

    @Query("SELECT * FROM Cycles WHERE Valide = 1 AND Nouveau = 1")
    LiveData<List<Cycles>> findAllCycleByValide();

    @Query("SELECT * FROM Cycles WHERE Valide = 1 AND Nouveau = 0")
    LiveData<List<Cycles>> findAllCycleByValideNew();

    @Insert
    long insertCycle(Cycles cycle);

    @Update
    void updateCycle(Cycles cycle);

    @Insert
    void insertCycles(List<Cycles> cycles);

    @Delete
    void deleteCycle(Cycles cy);

    @Query("DELETE FROM Cycles")
    void deleteAllCycle();

}

