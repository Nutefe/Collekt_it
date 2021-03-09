package com.perfekxia.collekt_it.ui.clientsVisites;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.perfekxia.collekt_it.model.ClientView;

import java.util.List;

public class ClientsVisitesViewModel {
    private MutableLiveData<List<ClientView>> mCltView;

    public ClientsVisitesViewModel(List<ClientView> cltView) {
        mCltView = new MutableLiveData<>();
        mCltView.setValue(cltView);
    }

    public LiveData<List<ClientView>> getClt() {
        return mCltView;
    }
}
