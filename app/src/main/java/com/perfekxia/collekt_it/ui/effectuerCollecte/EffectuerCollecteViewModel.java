package com.perfekxia.collekt_it.ui.effectuerCollecte;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class EffectuerCollecteViewModel {
    private MutableLiveData<String> mCltAccount;
    private MutableLiveData<String> mDateF;
    private MutableLiveData<String> mDateD;
    private MutableLiveData<Integer> mMontantMise;
    private MutableLiveData<Integer> mMontantSommeCollecte;
    private MutableLiveData<Integer> mMontantRestant;

    public EffectuerCollecteViewModel(String cltAccount,Integer montantMise,Integer montantSommeCollecte,Integer montantRestant,String dateDebut,String dateFin) {
        mCltAccount = new MutableLiveData<>();
        mMontantRestant = new MutableLiveData<>();
        mMontantMise = new MutableLiveData<>();
        mMontantSommeCollecte = new MutableLiveData<>();
        mDateD = new MutableLiveData<>();
        mDateF = new MutableLiveData<>();

        mCltAccount.setValue(cltAccount);
        mMontantMise.setValue(montantMise);
        mMontantSommeCollecte.setValue(montantSommeCollecte);
        mMontantRestant.setValue(montantRestant);
        mDateD.setValue(dateDebut);
        mDateF.setValue(dateFin);
    }

    public LiveData<String> getText() {
        return mCltAccount;
    }
    public LiveData<String> getTextDateD() {
        return mDateD;
    }
    public LiveData<String> getTextDateF() {
        return mDateF;
    }
    public LiveData<Integer> getTextMise() { return mMontantMise; }
    public LiveData<Integer> getTextSommeCollecte() { return mMontantSommeCollecte; }
    public LiveData<Integer> getTextSommeRestante() { return mMontantRestant; }
}
