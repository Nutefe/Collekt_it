package com.perfekxia.collekt_it.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;


import com.perfekxia.collekt_it.dao.RetraitDao;
import com.perfekxia.collekt_it.dao.ZoneDao;
import com.perfekxia.collekt_it.model.Retraits;
import com.perfekxia.collekt_it.sql.AppDatabase;

import java.util.List;

public class RetraitRepository {
    private RetraitDao retraitDao;
    private LiveData<List<Retraits>> mAllRetrait;

    public RetraitRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        retraitDao = db.retraitDao();
        mAllRetrait = retraitDao.findAllRetrait();
    }

    public LiveData<List<Retraits>> getAllRetrait() {
        return mAllRetrait;
    }

    public void insert (Retraits retrait) {
        new insertAsyncTask(retraitDao).execute(retrait);
    }

    private static class insertAsyncTask extends AsyncTask<Retraits, Void, Void> {

        private RetraitDao mAsyncTaskDao;

        insertAsyncTask(RetraitDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Retraits... params) {
            mAsyncTaskDao.insertRetrait(params[0]);
            return null;
        }
    }

    public void deleteAll() {
        try {
            new deleteAsyncTask(retraitDao).execute();
        }catch (Exception ex){

        }

    }

    private static class deleteAsyncTask extends AsyncTask<Void, Void, Void> {

        private RetraitDao mAsyncTaskDao;

        deleteAsyncTask(RetraitDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllRetrait();
//            Log.i("delete retrait", "success");
            return null;
        }
    }
}
