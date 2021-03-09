package com.perfekxia.collekt_it.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.perfekxia.collekt_it.dao.CycleDao;
import com.perfekxia.collekt_it.dao.MiseDao;
import com.perfekxia.collekt_it.model.Mises;
import com.perfekxia.collekt_it.sql.AppDatabase;

import java.util.List;

public class MiseRepository {
    private MiseDao miseDao;
    private LiveData<List<Mises>> mAllMise;
    private LiveData<List<Mises>> mAllNewMise;
    private LiveData<List<Mises>> mAllNewMiseCycle;
    private LiveData<Mises> mMise;

    public MiseRepository(Application application,String numeroCycle) {
        AppDatabase db = AppDatabase.getDatabase(application);
        miseDao = db.miseDao();
        mAllMise = miseDao.findAllMise();
        mMise = miseDao.findMiseByCycle(numeroCycle);
        mAllNewMiseCycle = miseDao.findAllMiseByCycle(numeroCycle);
        mAllNewMise = miseDao.findAllNewMise();
    }

    public LiveData<List<Mises>> getAllMise() {
        return mAllMise;
    }

    public LiveData<List<Mises>> getAllNewMise() {
        return mAllNewMise;
    }

    public LiveData<List<Mises>> getAllNewMiseByCycle() {
        return mAllNewMiseCycle;
    }

    public LiveData<Mises> getMiseByCycle() {
        return mMise;
    }

    public void insert (Mises mise) {
        new insertAsyncTask(miseDao).execute(mise);
    }

    public void update (Mises mise) {
        new updateAsyncTask(miseDao).execute(mise);
    }

    public void delete (Mises mise) {
        new deleteAsyncTask(miseDao).execute(mise);
    }

    public void deleteAll () {
        new deleteAllAsyncTask(miseDao).execute();
    }

    private static class updateAsyncTask extends AsyncTask<Mises, Void, Void> {

        private MiseDao mAsyncTaskDao;

        updateAsyncTask(MiseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Mises... params) {
            mAsyncTaskDao.updateMise(params[0]);
            Log.i("update mise", "ok");
            return null;
        }
    }

    private static class insertAsyncTask extends AsyncTask<Mises, Void, Void> {

        private MiseDao mAsyncTaskDao;

        insertAsyncTask(MiseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Mises... params) {
            mAsyncTaskDao.insertMise(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Mises, Void, Void> {

        private MiseDao mAsyncTaskDao;

        deleteAsyncTask(MiseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Mises... params) {
            mAsyncTaskDao.deleteMise(params[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private MiseDao mAsyncTaskDao;

        deleteAllAsyncTask(MiseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllMise();
            return null;
        }
    }
}
