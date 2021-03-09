package com.perfekxia.collekt_it.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.perfekxia.collekt_it.model.Collectes;

import java.util.List;

@Dao
public interface CollecteDao {

    @Query("SELECT * FROM collectes WHERE NumeroCollecte = :NumCol")
    LiveData<List<Collectes>> findCollecteById(Integer NumCol);

    @Query("SELECT * FROM collectes WHERE IdCycle = :NumeroCycle")
    LiveData<List<Collectes>> findCollecteByCycle(String NumeroCycle);

    @Query("SELECT SUM(MontantCollecte) FROM Collectes WHERE IdCycle = :NumeroCycle")
    LiveData<Integer> getSumCollectesByCycle(String NumeroCycle);

    @Query("SELECT DISTINCT IdClient FROM Collectes WHERE Nouveau =1 AND  date(datetime(DateCollecte / 1000 , 'unixepoch')) = date('now')")
    LiveData<List<String>> getDistinctCollectesByClient();

    @Insert
    void insertCollecte(Collectes collecte);

    @Insert
    void insertCollectes(List<Collectes> collectes);


    @Query("SELECT * FROM collectes")
    LiveData<List<Collectes>> findAllCollecte();

    @Query("SELECT * FROM collectes WHERE Nouveau=1 AND  date(datetime(DateCollecte / 1000 , 'unixepoch')) = date('now')")
    LiveData<List<Collectes>> findAllNewCollecte();

    @Query("SELECT * FROM collectes WHERE Nouveau=1")
    LiveData<List<Collectes>> findAllCollecteCy();


    @Update
    void updateCollecte(Collectes collecte);

    @Delete
    void deleteCollecte(Collectes col);

    @Query("DELETE FROM collectes")
    void deleteAllCollecte();

}
