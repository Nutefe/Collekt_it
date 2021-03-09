package com.perfekxia.collekt_it.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.perfekxia.collekt_it.dao.ClientViewDao;
import com.perfekxia.collekt_it.dao.TitulaireViewDao;
import com.perfekxia.collekt_it.model.ClientView;
import com.perfekxia.collekt_it.model.TitulaireView;
import com.perfekxia.collekt_it.sql.AppDatabase;

import java.util.List;

public class TitulaireViewRepository {
    private TitulaireViewDao titulaireViewDao;
    private LiveData<List<TitulaireView>> titulaireView;

    public TitulaireViewRepository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        titulaireViewDao = db.titulaireViewDao();
        titulaireView = titulaireViewDao.findAllTitulaireView();
    }

    public LiveData<List<TitulaireView>> getAllClientView() {
        return titulaireView;
    }


    public List<TitulaireView> getAllClientV() {
        try {
            return titulaireViewDao.findAllTitulaireV();
        }catch (Exception ex){
            return  null;
        }
    }

}
