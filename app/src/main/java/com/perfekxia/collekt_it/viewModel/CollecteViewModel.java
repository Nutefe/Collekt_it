package com.perfekxia.collekt_it.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.perfekxia.collekt_it.model.Collectes;
import com.perfekxia.collekt_it.repository.CollecteRepository;

import java.util.List;

public class CollecteViewModel extends AndroidViewModel {
    private CollecteRepository mRepository;

    private LiveData<List<Collectes>> mAllCollecte;
    private LiveData<List<Collectes>> mAllCollecteByCycle;
    private LiveData<List<Collectes>> mAllNewCollecte;
    private LiveData<List<String>> mListeDistinctsCollectes;
    private LiveData<Integer> mSumCollecte;

    public CollecteViewModel (Application application, String idCycle) {
        super(application);
        mRepository = new CollecteRepository(application,idCycle);
        mAllCollecte = mRepository.getAllCollecte();
        mSumCollecte = mRepository.getSumCollecte();
        mListeDistinctsCollectes = mRepository.getDistinctClients();
        mAllNewCollecte = mRepository.getAllNewCollecte();
        mAllCollecteByCycle = mRepository.getAllCollecteByCcycle();
    }

   public LiveData<List<Collectes>> getAllCollecte() { return mAllCollecte; }

   public LiveData<List<Collectes>> getmAllCollecteByCycle() { return mAllCollecteByCycle; }

   public LiveData<List<Collectes>> getAllNewCollecte() { return mAllNewCollecte; }

   public LiveData<List<String>> getmListeDistinctsCollectes() { return mListeDistinctsCollectes; }

   public LiveData<Integer> getSumCollecte() { return mSumCollecte; }

    public void insert(Collectes collecte) { mRepository.insert(collecte); }

    public void update(Collectes collecte) { mRepository.update(collecte); }

    public void delete(Collectes collecte) {
        Log.i("co", collecte.toString());
        mRepository.delete(collecte);
    }

    public void deleteAll() { mRepository.deleteAll(); }
}
