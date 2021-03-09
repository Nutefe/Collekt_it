package com.perfekxia.collekt_it.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;


import com.perfekxia.collekt_it.dao.UtilisateurDao;
import com.perfekxia.collekt_it.model.UserRequest;
import com.perfekxia.collekt_it.model.Utilisateurs;
import com.perfekxia.collekt_it.sql.AppDatabase;

import java.util.List;

public class UtilisateurRepository {
    private UtilisateurDao utilisateurDao;
    private LiveData<List<Utilisateurs>> mAllUtilisateur;
    private LiveData<List<Utilisateurs>> mAllUpdatedUtilisateur;

    public UtilisateurRepository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        utilisateurDao = db.utilisateurDao();
        mAllUtilisateur = utilisateurDao.findAllUtilisateur();
        mAllUpdatedUtilisateur = utilisateurDao.findUpdatedUser();
    }

    public LiveData<List<Utilisateurs>> getAllUtilisateur() {
        return mAllUtilisateur;
    }

    public LiveData<List<Utilisateurs>> getAllUpdatedUtilisateur() {
        return mAllUpdatedUtilisateur;
    }



    public void deleteAll () {
        try {
            new deleteAsyncTask(utilisateurDao).execute();
        }catch (Exception ex){

        }

    }

    public void insert (Utilisateurs utilisateur) {
        try {
            new insertAsyncTask(utilisateurDao).execute(utilisateur);
        }catch (Exception ex){

        }

    }

    private static class insertAsyncTask extends AsyncTask<Utilisateurs, Void, Void> {

        private UtilisateurDao mAsyncTaskDao;

        insertAsyncTask(UtilisateurDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Utilisateurs... params) {
            mAsyncTaskDao.insertUtilisateur(params[0]);
            return null;
        }
    }

    public void update(Utilisateurs utilisateur) {
        try {
            new updateAsyncTask(utilisateurDao).execute(utilisateur);
        }catch (Exception ex){

        }

    }

    private static class updateAsyncTask extends AsyncTask<Utilisateurs, Void, Void> {

        private UtilisateurDao mAsyncTaskDao;

        updateAsyncTask(UtilisateurDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Utilisateurs... params) {
            mAsyncTaskDao.updateUtilisateur(params[0]);
            return null;
        }
    }

    public void updatePassword(UserRequest utilisateur) {
        try {
            new updatePasswordAsyncTask(utilisateurDao).execute(utilisateur);
//            Log.i("update password", String.valueOf(utilisateur.getPin()));
        }catch (Exception ex){

        }

    }

    private static class updatePasswordAsyncTask extends AsyncTask<UserRequest, Void, Void> {

        private UtilisateurDao mAsyncTaskDao;

        updatePasswordAsyncTask(UtilisateurDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final UserRequest... params) {
            mAsyncTaskDao.updatePassword(params[0].getMotDePasse(), true, params[0].getNomUtilisateur());
            Log.i("update password", params[0].getMotDePasse());
            return null;
        }
    }

    public void updatePin(UserRequest utilisateur) {
        try {
            new updatePinAsyncTask(utilisateurDao).execute(utilisateur);
            Log.i("update pin", String.valueOf(utilisateur.getPin()));
        }catch (Exception ex){

        }

    }

    private static class updatePinAsyncTask extends AsyncTask<UserRequest, Void, Void> {

        private UtilisateurDao mAsyncTaskDao;

        updatePinAsyncTask(UtilisateurDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final UserRequest... params) {
            mAsyncTaskDao.updatePin(params[0].getPin(), true, params[0].getNomUtilisateur());
            Log.i("update pin", String.valueOf(params[0].getPin()));
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Void, Void, Void> {

        private UtilisateurDao mAsyncTaskDao;

        deleteAsyncTask(UtilisateurDao dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllUtilisateur();
            Log.i("delete :", "success");
            return null;
        }
    }


}
