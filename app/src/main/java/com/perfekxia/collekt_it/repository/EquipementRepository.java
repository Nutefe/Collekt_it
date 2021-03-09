package com.perfekxia.collekt_it.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.perfekxia.collekt_it.dao.EquipementDao;
import com.perfekxia.collekt_it.model.Equipements;
import com.perfekxia.collekt_it.sql.AppDatabase;

import java.util.List;

public class EquipementRepository {
    private EquipementDao equipementDao;
    private LiveData<List<Equipements>> mAllEquipement;

    public EquipementRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        equipementDao = db.equipementDao();
        mAllEquipement = equipementDao.findAllEquipement();
    }

    public LiveData<List<Equipements>> getAllEquipement() {
        return mAllEquipement;
    }

    public void insert (Equipements equipement) {
        new insertAsyncTask(equipementDao).execute(equipement);
    }

    private static class insertAsyncTask extends AsyncTask<Equipements, Void, Void> {

        private EquipementDao mAsyncTaskDao;

        insertAsyncTask(EquipementDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Equipements... params) {
            mAsyncTaskDao.insertEquipement(params[0]);
            return null;
        }
    }
}
