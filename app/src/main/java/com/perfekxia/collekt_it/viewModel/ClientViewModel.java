package com.perfekxia.collekt_it.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.perfekxia.collekt_it.model.Clients;
import com.perfekxia.collekt_it.repository.ClientRepository;

import java.util.List;

public class ClientViewModel extends AndroidViewModel {
    private ClientRepository mRepository;

    private LiveData<List<Clients>> mAllClient;
    private LiveData<Clients> mClient;

    public ClientViewModel (Application application,String idClient) {
        super(application);
        mRepository = new ClientRepository(application,idClient);
        mAllClient = mRepository.getAllClient();
        mClient = mRepository.getClientById();
    }

   public LiveData<List<Clients>> getAllClient() { return mAllClient; }

   public LiveData<Clients> getClient() { return mClient; }

    public void insert(Clients client) { mRepository.insert(client); }

    public void update(Clients client) { mRepository.update(client); }

    public void deleteAll() { mRepository.deleteAll(); }
}
