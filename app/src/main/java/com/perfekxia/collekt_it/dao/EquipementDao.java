package com.perfekxia.collekt_it.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.perfekxia.collekt_it.model.Equipements;

import java.util.List;

@Dao
public interface EquipementDao {

    @Query("SELECT * FROM Equipements")
    LiveData<List<Equipements>> findAllEquipement();

    @Query("SELECT * FROM Equipements WHERE NumeroEquipement = :deviceId")
    Equipements findEquipementByNumero(String deviceId);

    @Insert
    void insertEquipement(Equipements equipement);

    @Insert
    void insertEquipements(List<Equipements> list);

    @Update
    void updateEquipement(Equipements equipement);

}
