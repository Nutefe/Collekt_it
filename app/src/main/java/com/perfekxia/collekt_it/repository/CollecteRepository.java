package com.perfekxia.collekt_it.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.perfekxia.collekt_it.dao.AffectationDao;
import com.perfekxia.collekt_it.dao.CollecteDao;
import com.perfekxia.collekt_it.model.Collectes;
import com.perfekxia.collekt_it.sql.AppDatabase;

import java.util.List;

public class CollecteRepository {
    private CollecteDao collecteDao;
    private LiveData<List<Collectes>> mAllCollecte;
    private LiveData<List<Collectes>> mAllCollecteByCycle;
    private LiveData<List<Collectes>> mAllNewCollecte;
    private LiveData<Integer> sommeCollecte;
    private LiveData<List<String>> mlisteDistinctClients;

    public CollecteRepository(Application application,String idCycle) {
        AppDatabase db = AppDatabase.getDatabase(application);
        collecteDao = db.collecteDao();
        mAllCollecte = collecteDao.findAllCollecte();
        sommeCollecte = collecteDao.getSumCollectesByCycle(idCycle);
        mlisteDistinctClients = collecteDao.getDistinctCollectesByClient();
        mAllNewCollecte = collecteDao.findAllNewCollecte();
        mAllCollecteByCycle = collecteDao.findCollecteByCycle(idCycle);
    }

    public LiveData<List<Collectes>> getAllCollecte() {
        return mAllCollecte;
    }

    public LiveData<List<Collectes>> getAllCollecteByCcycle() {
        return mAllCollecteByCycle;
    }

    public LiveData<List<Collectes>> getAllNewCollecte() {
        return mAllNewCollecte;
    }

    public LiveData<List<String>> getDistinctClients() {
        return mlisteDistinctClients;
    }

    public LiveData<Integer> getSumCollecte() {
        return sommeCollecte;
    }

    public void insert (Collectes collecte) {
        new insertAsyncTask(collecteDao).execute(collecte);
    }

    public void update (Collectes collecte) {
        new updateAsyncTask(collecteDao).execute(collecte);
    }

    public void delete (Collectes collecte) {
        new deleteAsyncTask(collecteDao).execute(collecte);
    }

    public void deleteAll ( ) {
        new deleteAllAsyncTask(collecteDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<Collectes, Void, Void> {

        private CollecteDao mAsyncTaskDao;

        insertAsyncTask(CollecteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Collectes... params) {
            mAsyncTaskDao.insertCollecte(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Collectes, Void, Void> {

        private CollecteDao mAsyncTaskDao;

        updateAsyncTask(CollecteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Collectes... params) {
            mAsyncTaskDao.updateCollecte(params[0]);
            Log.i("update collectes", "ok");
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Collectes, Void, Void> {

        private CollecteDao mAsyncTaskDao;

        deleteAsyncTask(CollecteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Collectes... params) {
            mAsyncTaskDao.deleteCollecte(params[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private CollecteDao mAsyncTaskDao;

        deleteAllAsyncTask(CollecteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllCollecte();
            return null;
        }
    }
}
