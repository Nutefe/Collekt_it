package com.perfekxia.collekt_it.ui.confirmerCollecte;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.perfekxia.collekt_it.model.ClientView;

public class ConfirmerCollecteViewModel {
    private MutableLiveData<String> mMontant;
    private MutableLiveData<ClientView> clientViewMutableLiveData;



    public ConfirmerCollecteViewModel(String montantCollecte, ClientView cltView) {
        mMontant = new MutableLiveData<>();
        clientViewMutableLiveData = new MutableLiveData<>();
        mMontant.setValue(montantCollecte);
        clientViewMutableLiveData.setValue(cltView);

    }

    public LiveData<String> getText() {
        return mMontant;
    }

    public LiveData<ClientView> getClientView() {
        return clientViewMutableLiveData;
    }
}
