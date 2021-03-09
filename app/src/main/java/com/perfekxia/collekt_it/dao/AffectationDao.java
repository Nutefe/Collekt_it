package com.perfekxia.collekt_it.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.perfekxia.collekt_it.model.Affectations;

import java.util.List;

@Dao
public interface  AffectationDao {
    @Query("SELECT * FROM Affectations")
    LiveData<List<Affectations>> findAllAffectation();

    @Insert
    void insertAffectation(Affectations affectations);

    @Insert
    void insertAffectations(List<Affectations> affectations);

    @Update
    void updateAffectation(Affectations affectations);

    @Delete
    void deleteAffectation(Affectations affectations);

    @Query("DELETE FROM Affectations")
    void deleteAllAffectation();

}
