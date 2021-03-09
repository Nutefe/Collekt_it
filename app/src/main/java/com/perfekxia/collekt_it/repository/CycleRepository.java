package com.perfekxia.collekt_it.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.perfekxia.collekt_it.dao.CycleDao;
import com.perfekxia.collekt_it.model.Cycles;
import com.perfekxia.collekt_it.sql.AppDatabase;

import java.util.List;

public class CycleRepository {
    private CycleDao cycleDao;
    private LiveData<List<Cycles>> mAllCycle;
    private LiveData<List<Cycles>> mAllCycleEnCours;
    private LiveData<List<Cycles>> mAllCycleClot;
    private LiveData<List<Cycles>> mAllNewCycle;
    private LiveData<List<Cycles>> mAllCycleCompte;
    private LiveData<List<Cycles>> mAllCycleValide;
    private LiveData<List<Cycles>> mAllCycleValideNew;
    private LiveData<List<Cycles>> mAllCycleValideOld;
    private LiveData<Cycles> cycles;
    private long id;

    public CycleRepository(Context context, String idCompte) {

        Log.i("idCompte", idCompte);
        AppDatabase db = AppDatabase.getDatabase(context);
        cycleDao = db.cycleDao();
        mAllCycle = cycleDao.findAllCycle();
        cycles = cycleDao.findCycleByAccount(idCompte);
        mAllCycleCompte = cycleDao.findAllCycleByAccount(idCompte);
        mAllNewCycle = cycleDao.findAllNewCycle();
        mAllCycleClot = cycleDao.findAllCycleCloture();
        mAllCycleEnCours = cycleDao.findAllCycleEnCours();
        mAllCycleValide = cycleDao.findAllCycleByValide();
        mAllCycleValideNew = cycleDao.findAllCycleByValideNew();
        mAllCycleValideOld = cycleDao.findAllOldCycleEnCours();
    }

    public LiveData<List<Cycles>> getAllCycle() {
        return mAllCycle;
    }

    public LiveData<List<Cycles>> getAllOldCycleEnCours() {
        return mAllCycleValideOld;
    }

    public LiveData<List<Cycles>> getAllCycleEnCours() {
        return mAllCycleEnCours;
    }

    public LiveData<List<Cycles>> getAllCycleValide() {
        return mAllCycleValide;
    }

    public LiveData<List<Cycles>> getAllCycleValideNew() {
        return mAllCycleValideNew;
    }

    public LiveData<List<Cycles>> getAllCycleClot() {
        return mAllCycleClot;
    }

    public LiveData<List<Cycles>> getAllNewCycle() {
        return mAllNewCycle;
    }

    public LiveData<List<Cycles>> getAllCycleByCompte() {
        return mAllCycleCompte;
    }

    public LiveData<Cycles> getCycleByAccount() {
        return cycles;
    }

    public Long insert (Cycles cycle) {
        try{
            return new insertAsyncTask(cycleDao).execute(cycle).get();
        }catch (Exception e){
            return null;
        }
    }

    public void update (Cycles cycle) {
        new updateAsyncTask(cycleDao).execute(cycle);
    }

    public void delete (Cycles cycle) {
        new deleteAsyncTask(cycleDao).execute(cycle);
    }

    public void deleteAll () {
        new deleteAllAsyncTask(cycleDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<Cycles, Void, Long> {

        private CycleDao mAsyncTaskDao;

        insertAsyncTask(CycleDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Long doInBackground(final Cycles... params) {

            return mAsyncTaskDao.insertCycle(params[0]);
        }
    }

    private static class updateAsyncTask extends AsyncTask<Cycles, Void, Void> {

        private CycleDao mAsyncTaskDao;

        updateAsyncTask(CycleDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Cycles... params) {
            mAsyncTaskDao.updateCycle(params[0]);
            Log.i("update cycle", "ok");
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Cycles, Void, Void> {

        private CycleDao mAsyncTaskDao;

        deleteAsyncTask(CycleDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Cycles... params) {
            mAsyncTaskDao.deleteCycle(params[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private CycleDao mAsyncTaskDao;

        deleteAllAsyncTask(CycleDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllCycle();
            return null;
        }
    }
}
