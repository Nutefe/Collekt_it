package com.perfekxia.collekt_it.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import com.perfekxia.collekt_it.dao.TitulaireDao;
import com.perfekxia.collekt_it.model.Titulaires;
import com.perfekxia.collekt_it.sql.AppDatabase;

import java.util.List;

public class TitulaireRepository {
    private TitulaireDao titulaireDao;
    private LiveData<List<Titulaires>> mAllTitulaire;

    public TitulaireRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        titulaireDao = db.titulaireDao();
        mAllTitulaire = titulaireDao.findAllTitulaire();
    }

    public LiveData<List<Titulaires>> getAllTitulaire() {
        return mAllTitulaire;
    }

    public void insert (Titulaires titulaire) {
        new insertAsyncTask(titulaireDao).execute(titulaire);
    }

    private static class insertAsyncTask extends AsyncTask<Titulaires, Void, Void> {

        private TitulaireDao mAsyncTaskDao;

        insertAsyncTask(TitulaireDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Titulaires... params) {
            mAsyncTaskDao.insertTitulaire(params[0]);
            return null;
        }
    }
}
