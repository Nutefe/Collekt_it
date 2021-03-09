package com.perfekxia.collekt_it.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.perfekxia.collekt_it.dao.ClientDao;
import com.perfekxia.collekt_it.dao.ProduitDao;
import com.perfekxia.collekt_it.model.Clients;
import com.perfekxia.collekt_it.sql.AppDatabase;

import java.util.List;

public class ClientRepository {

    private ClientDao clientDao;
    private LiveData<List<Clients>> mAllclients;
    private LiveData<Clients> mClients;

    public ClientRepository(Application application,String idClient) {
        AppDatabase db = AppDatabase.getDatabase(application);
        clientDao = db.clientDao();
        mAllclients = clientDao.findAllClients();
        mClients = clientDao.findClientsById(idClient);
    }

    public LiveData<List<Clients>> getAllClient() {
        return mAllclients;
    }

    public LiveData<Clients> getClientById() {
        return mClients;
    }

    public LiveData<List<Clients>> getAllClientZone(String zone) {
        try {
            return clientDao.findClientsByZ(zone);
        }catch (Exception ex){
            return null;
        }
    }

    public void insert (Clients client) {
        new insertAsyncTask(clientDao).execute(client);
    }

    private static class insertAsyncTask extends AsyncTask<Clients, Void, Void> {

        private ClientDao mAsyncTaskDao;

        insertAsyncTask(ClientDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Clients... params) {
            mAsyncTaskDao.insertClient(params[0]);
//            Log.i("insert client", params[0].toString());
            return null;
        }
    }

    public void update (Clients client) {
        new updateAsyncTask(clientDao).execute(client);
    }

    private static class updateAsyncTask extends AsyncTask<Clients, Void, Void> {

        private ClientDao mAsyncTaskDao;

        updateAsyncTask(ClientDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Clients... params) {
            mAsyncTaskDao.updateClients(params[0]);
//            Log.i("insert client", params[0].toString());
            return null;
        }
    }

    public void deleteAll() {
        try {
            new deleteAsyncTask(clientDao).execute();
        }catch (Exception ex){

        }

    }

    private static class deleteAsyncTask extends AsyncTask<Void, Void, Void> {

        private ClientDao mAsyncTaskDao;

        deleteAsyncTask(ClientDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllClient();
//            Log.i("delete client", "success");
            return null;
        }
    }
}
