package com.perfekxia.collekt_it.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.perfekxia.collekt_it.model.Titulaires;
import com.perfekxia.collekt_it.model.Zones;

import java.util.List;

@Dao
public interface ZoneDao {
    @Query("SELECT * FROM Zones")
    LiveData<List<Zones>> findAllZones();

    @Query("SELECT * FROM Zones")
    List<Zones> findAllZone();

    @Query("SELECT * FROM Zones WHERE IdZone = :IdZone")
    Zones findZoneById(String IdZone);

    @Insert
    void insertZone(Zones zone);

    @Insert
    void insertZones(List<Zones> zones);

    @Update
    void updateZone(Zones zones);

    @Query("DELETE FROM Zones")
    void deleteAllZones();
}

