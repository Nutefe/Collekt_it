package com.perfekxia.collekt_it.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.perfekxia.collekt_it.dao.AffectationDao;
import com.perfekxia.collekt_it.model.Affectations;
import com.perfekxia.collekt_it.sql.AppDatabase;

import java.util.List;

public class AffectationRepository {
    private AffectationDao affectationDao;
    private LiveData<List<Affectations>> mAllAffectation;

   public AffectationRepository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        affectationDao = db.affectationDao();
        mAllAffectation = affectationDao.findAllAffectation();
    }

    public LiveData<List<Affectations>> getAllAffectation() {
        return mAllAffectation;
    }

    public void insert (Affectations affectations) {
        try {
            new insertAsyncTask(affectationDao).execute(affectations);
        }catch (Exception ex){

        }

    }

    public void deleteAll () {
        try {
            new deleteAsyncTask(affectationDao).execute();
        }catch (Exception ex){

        }
    }

    private static class insertAsyncTask extends AsyncTask<Affectations, Void, Void> {

        private AffectationDao mAsyncTaskDao;

        insertAsyncTask(AffectationDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Affectations... params) {
            mAsyncTaskDao.insertAffectation(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Void, Void, Void> {

        private AffectationDao mAsyncTaskDao;

        deleteAsyncTask(AffectationDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllAffectation();
            return null;
        }
    }
}
