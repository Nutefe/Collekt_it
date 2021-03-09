package com.perfekxia.collekt_it.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;


import com.perfekxia.collekt_it.dao.LoginDao;
import com.perfekxia.collekt_it.model.Login;
import com.perfekxia.collekt_it.model.LoginRequest;
import com.perfekxia.collekt_it.sql.AppDatabase;

import java.util.List;

public class LoginRepository {
    private LoginDao loginDao;
    private LiveData<List<Login>> mAllLogin;

    public LoginRepository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        loginDao = db.loginDao();
        mAllLogin = loginDao.findAllLogin();
    }

    public LiveData<List<Login>> getAllLogin() {
        return mAllLogin;
    }

    public Long insert (Login login) {
        try {
            return new insertAsyncTask(loginDao).execute(login).get();
        }catch (Exception ex){
            return  null;
        }
    }

    private static class insertAsyncTask extends AsyncTask<Login, Void, Long> {

        private LoginDao mAsyncTaskDao;

        insertAsyncTask(LoginDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Long doInBackground(final Login... params) {
            try {
                Log.i("insert login admin", "ok");
                return mAsyncTaskDao.insertLogin(params[0]);

            }catch (Exception ex){
                return null;
            }
        }
    }

    public void updateLogin(Login login) {
        try {
            new updateAsyncTask(loginDao).execute(login).get();
        }catch (Exception ex){

        }
    }

    private static class updateAsyncTask extends AsyncTask<Login, Void, Void> {

        private LoginDao mAsyncTaskDao;

        updateAsyncTask(LoginDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Login... params) {
             mAsyncTaskDao.updateLogin(params[0]);
             return null;
        }
    }

    public Login selectLogin(LoginRequest login) {
        try {
            return new loginAsyncTask(loginDao).execute(login).get();
        }catch (Exception ex){
            return  null;
        }
    }

    private static class loginAsyncTask extends AsyncTask<LoginRequest, Void, Login> {

        private LoginDao mAsyncTaskDao;

        loginAsyncTask(LoginDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Login doInBackground(final LoginRequest... params) {
            return mAsyncTaskDao.findLoginByUsernameAndPassword(params[0].getNomUtilisateur(), params[0].getMotDePasse());
        }
    }
}
