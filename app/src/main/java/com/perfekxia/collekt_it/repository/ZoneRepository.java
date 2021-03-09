package com.perfekxia.collekt_it.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.perfekxia.collekt_it.dao.AffectationDao;
import com.perfekxia.collekt_it.dao.ZoneDao;
import com.perfekxia.collekt_it.model.Zones;
import com.perfekxia.collekt_it.sql.AppDatabase;

import java.util.List;

public class ZoneRepository{
    private ZoneDao zoneDao;
    private LiveData<List<Zones>> mAllZone;

    public ZoneRepository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        zoneDao = db.zoneDao();
        mAllZone = zoneDao.findAllZones();
    }

    public LiveData<List<Zones>> getAllZone() {
        return mAllZone;
    }

    public Zones getZone(String idZones) {
        try {
            return zoneDao.findZoneById(idZones);
        }catch (Exception ex){
//            Log.i("Zone error", ex.getMessage());
            return  null;
        }
    }

    public List<Zones> getZoneV() {
        try {
            return zoneDao.findAllZone();
        }catch (Exception ex){
            return  null;
        }
    }

    public void insert (Zones zone) {
        try {
            new insertAsyncTask(zoneDao).execute(zone);
        }catch (Exception ex){

        }
    }

    private static class insertAsyncTask extends AsyncTask<Zones, Void, Void> {

        private ZoneDao mAsyncTaskDao;

        insertAsyncTask(ZoneDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Zones... params) {
            mAsyncTaskDao.insertZone(params[0]);
            return null;
        }
    }

    public void deleteAll() {
        try {
            new deleteAsyncTask(zoneDao).execute();
        }catch (Exception ex){

        }

    }

    private static class deleteAsyncTask extends AsyncTask<Void, Void, Void> {

        private ZoneDao mAsyncTaskDao;

        deleteAsyncTask(ZoneDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllZones();
//            Log.i("delete Zone", "success");
            return null;
        }
    }
}
