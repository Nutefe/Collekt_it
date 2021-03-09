package com.perfekxia.collekt_it.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.perfekxia.collekt_it.model.ClientView;

import java.util.List;

@Dao
public interface ClientViewDao {
    @Query("SELECT * FROM ClientView")
    LiveData<List<ClientView>> findAllClientView();

    @Query("SELECT * FROM ClientView WHERE IdClient= :NumeroClient")
    LiveData<ClientView> findClientViewById(String NumeroClient);

    @Query("SELECT * FROM ClientView WHERE Produit= :NumeroProduit")
    LiveData<List<ClientView>> findClientViewByProduit(String NumeroProduit);
}
