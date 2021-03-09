package com.perfekxia.collekt_it.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;


import com.perfekxia.collekt_it.dao.ProduitDao;
import com.perfekxia.collekt_it.dao.RetraitDao;
import com.perfekxia.collekt_it.model.Produits;
import com.perfekxia.collekt_it.sql.AppDatabase;

import java.util.List;

public class ProduitRepository {
    private ProduitDao produitDao;
    private LiveData<List<Produits>> mAllProduit;

    public ProduitRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        produitDao = db.produitDao();
        mAllProduit = produitDao.findAllProduit();
    }

    public LiveData<List<Produits>> getAllProduit() {
        return mAllProduit;
    }

    public void insert (Produits produit) {
        new insertAsyncTask(produitDao).execute(produit);
    }

    private static class insertAsyncTask extends AsyncTask<Produits, Void, Void> {

        private ProduitDao mAsyncTaskDao;

        insertAsyncTask(ProduitDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Produits... params) {
            mAsyncTaskDao.insertProduit(params[0]);
            return null;
        }
    }

    public void deleteAll() {
        try {
            new deleteAsyncTask(produitDao).execute();
        }catch (Exception ex){

        }

    }

    private static class deleteAsyncTask extends AsyncTask<Void, Void, Void> {

        private ProduitDao mAsyncTaskDao;

        deleteAsyncTask(ProduitDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllProduit();
//            Log.i("delete produit", "success");
            return null;
        }
    }
}
