package com.perfekxia.collekt_it.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.perfekxia.collekt_it.dao.LocalisationsDao;
import com.perfekxia.collekt_it.model.Localisations;
import com.perfekxia.collekt_it.sql.AppDatabase;

import java.util.List;


public class LocalisationsRepository {
    private LocalisationsDao localisationsDao;
    private LiveData<List<Localisations>> mAllLocalisations;

    public LocalisationsRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        localisationsDao = db.localisationsDao();
        mAllLocalisations = localisationsDao.findAllLocalisations();
    }
    public LiveData<List<Localisations>> getAllLocalisations() {
        return mAllLocalisations;
    }

    public void insert (Localisations localisations) {
        new insertAsyncTask(localisationsDao).execute(localisations);
    }

    private static class insertAsyncTask extends AsyncTask<Localisations, Void, Void> {

        private LocalisationsDao mAsyncTaskDao;

        insertAsyncTask(LocalisationsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Localisations... params) {
            mAsyncTaskDao.insertLocalisations(params[0]);
            return null;
        }
    }
}
