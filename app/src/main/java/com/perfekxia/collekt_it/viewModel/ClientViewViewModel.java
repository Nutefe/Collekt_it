package com.perfekxia.collekt_it.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.perfekxia.collekt_it.model.ClientView;
import com.perfekxia.collekt_it.repository.ClientViewRepository;

import java.util.List;

public class ClientViewViewModel extends AndroidViewModel {
    private ClientViewRepository mRepository;

    private LiveData<List<ClientView>> mAllClientView;
    private LiveData<List<ClientView>> mAllClientViewProduit;
    private LiveData<ClientView> mClientView;

    public ClientViewViewModel (Application application,String id,String idProduit) {
        super(application);
        mRepository = new ClientViewRepository(application,id,idProduit);
        mAllClientView = mRepository.getAllClientView();
        mClientView = mRepository.getmClientViewById();
        mAllClientViewProduit = mRepository.getAllClientViewByProduit();
    }

    public LiveData<List<ClientView>> getAllClientView() { return mAllClientView; }
    public LiveData<List<ClientView>> getAllClientViewProuit() { return mAllClientViewProduit; }
    public LiveData<ClientView> getClientViewById() { return mClientView; }

}
