package com.perfekxia.collekt_it.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.perfekxia.collekt_it.dao.ClientViewDao;
import com.perfekxia.collekt_it.model.ClientView;
import com.perfekxia.collekt_it.sql.AppDatabase;

import java.util.List;

public class ClientViewRepository {
    private ClientViewDao clientViewDao;
    private LiveData<List<ClientView>> mAllClientView;
    private LiveData<ClientView> mAllClientViewById;
    private LiveData<List<ClientView>> mAllClientViewByProduit;

    public ClientViewRepository(Application application,String id,String idProduit) {
        AppDatabase db = AppDatabase.getDatabase(application);
        clientViewDao = db.clientViewDao();
        mAllClientView = clientViewDao.findAllClientView();
        mAllClientViewById = clientViewDao.findClientViewById(id);
        mAllClientViewByProduit = clientViewDao.findClientViewByProduit(idProduit);
    }

    public LiveData<List<ClientView>> getAllClientView() {
        return mAllClientView;
    }

    public LiveData<List<ClientView>> getAllClientViewByProduit() {
        return mAllClientViewByProduit;
    }

    public LiveData<ClientView> getmClientViewById() {
        return mAllClientViewById;
    }

}
