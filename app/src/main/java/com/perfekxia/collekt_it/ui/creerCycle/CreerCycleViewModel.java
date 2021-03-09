package com.perfekxia.collekt_it.ui.creerCycle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class CreerCycleViewModel {
    private MutableLiveData<String> mCltAccount;
    private MutableLiveData<String> mDateDebut;
    private MutableLiveData<String> mDateFin;

    public CreerCycleViewModel(String cltAccount,String dateDebut, String dateFin) {
        mCltAccount = new MutableLiveData<>();
        mDateDebut = new MutableLiveData<>();
        mDateFin = new MutableLiveData<>();
        mCltAccount.setValue(cltAccount);
        mDateDebut.setValue(dateDebut);
        mDateFin.setValue(dateFin);
    }

    public LiveData<String> getText() {
        return mCltAccount;
    }
    public LiveData<String> getTextDateDebut() {
        return mDateDebut;
    }
    public LiveData<String> getTextDateFin() {
        return mDateFin;
    }
}
