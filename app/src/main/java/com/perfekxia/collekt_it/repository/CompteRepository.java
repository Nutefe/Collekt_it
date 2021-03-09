package com.perfekxia.collekt_it.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.perfekxia.collekt_it.dao.ClientDao;
import com.perfekxia.collekt_it.dao.CompteDao;
import com.perfekxia.collekt_it.model.Comptes;
import com.perfekxia.collekt_it.sql.AppDatabase;

import java.util.List;

public class CompteRepository {
    private CompteDao compteDao;
    private LiveData<List<Comptes>> mAllCompte;

    public CompteRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        compteDao = db.compteDao();
        mAllCompte = compteDao.findAllCompte();
    }

    public LiveData<List<Comptes>> getAllCompte() {
        return mAllCompte;
    }

    public void insert (Comptes compte) {
        new insertAsyncTask(compteDao).execute(compte);
    }

    private static class insertAsyncTask extends AsyncTask<Comptes, Void, Void> {

        private CompteDao mAsyncTaskDao;

        insertAsyncTask(CompteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Comptes... params) {
            mAsyncTaskDao.insertCompte(params[0]);
            return null;
        }
    }

    public void deleteAll() {
        try {
            new deleteAsyncTask(compteDao).execute();
        }catch (Exception ex){

        }

    }

    private static class deleteAsyncTask extends AsyncTask<Void, Void, Void> {

        private CompteDao mAsyncTaskDao;

        deleteAsyncTask(CompteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllCompte();
//            Log.i("delete compte", "success");
            return null;
        }
    }
}
