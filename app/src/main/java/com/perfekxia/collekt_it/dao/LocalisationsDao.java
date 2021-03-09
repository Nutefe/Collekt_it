package com.perfekxia.collekt_it.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.perfekxia.collekt_it.model.Localisations;

import java.util.List;

@Dao
public interface LocalisationsDao {

    @Query("SELECT * FROM Localisations")
    LiveData<List<Localisations>> findAllLocalisations();

    @Insert
    void insertLocalisations(Localisations localisations);

}
