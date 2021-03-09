package com.perfekxia.collekt_it.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.perfekxia.collekt_it.model.Clients;

import java.util.List;

@Dao
public interface ClientDao {
    @Query("SELECT * FROM Clients")
    LiveData<List<Clients>> findAllClients();

    @Query("SELECT * FROM Clients WHERE IdClient = :idClient")
    LiveData<Clients> findClientsById(String idClient);

    @Query("SELECT * FROM Clients WHERE ZoneClient = :ZoneC")
    List<Clients> findClientsByZone(String ZoneC);

    @Query("SELECT * FROM Clients WHERE ZoneClient = :ZoneC")
    LiveData<List<Clients>> findClientsByZ(String ZoneC);

    @Delete
    void deleteClients(Clients client);

    @Insert
    void insertClient(Clients client);

    @Insert
    void insertClients(List<Clients> clients);

    @Update
    void updateClients(Clients clients);

    @Query("DELETE FROM Clients")
    void deleteAllClient();

}
