package com.perfekxia.collekt_it.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.perfekxia.collekt_it.model.Login;

import java.util.List;

@Dao
public interface LoginDao {
    @Query("SELECT * FROM Login")
    LiveData<List<Login>> findAllLogin();

    @Insert
    Long insertLogin(Login login);

    @Insert
    void insertLogins(List<Login> logins);

    @Update
    void updateLogin(Login login);

    @Query("SELECT * FROM Login WHERE  NomUtilisateur= :userLogin AND MotDePasse = :password")
    Login findLoginByUsernameAndPassword(String userLogin, String password);

}
