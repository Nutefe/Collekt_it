package com.perfekxia.collekt_it.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.perfekxia.collekt_it.model.Comptes;
import com.perfekxia.collekt_it.repository.CompteRepository;

import java.util.List;

public class CompteViewModel extends AndroidViewModel {
    private CompteRepository mRepository;

    private LiveData<List<Comptes>> mAllCompte;

    public CompteViewModel (Application application) {
        super(application);
        mRepository = new CompteRepository(application);
        mAllCompte = mRepository.getAllCompte();
    }

    public LiveData<List<Comptes>> getAllCompte() { return mAllCompte; }

    public void insert(Comptes compte) { mRepository.insert(compte); }

    public void deleteAll() { mRepository.deleteAll(); }
}
