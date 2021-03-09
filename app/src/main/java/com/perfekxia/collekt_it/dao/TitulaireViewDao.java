package com.perfekxia.collekt_it.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.perfekxia.collekt_it.model.ClientView;
import com.perfekxia.collekt_it.model.TitulaireView;

import java.util.List;

@Dao
public interface TitulaireViewDao {
    @Query("SELECT * FROM TitulaireView")
    LiveData<List<TitulaireView>> findAllTitulaireView();

    @Query("SELECT * FROM TitulaireView")
    List<TitulaireView> findAllTitulaireV();
}
