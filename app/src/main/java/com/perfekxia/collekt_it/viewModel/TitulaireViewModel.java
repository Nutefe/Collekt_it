package com.perfekxia.collekt_it.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.perfekxia.collekt_it.model.Titulaires;
import com.perfekxia.collekt_it.repository.TitulaireRepository;

import java.util.List;

public class TitulaireViewModel extends AndroidViewModel {
    private TitulaireRepository mRepository;

    private LiveData<List<Titulaires>> mAllTitulaire;

    public TitulaireViewModel (Application application) {
        super(application);
        mRepository = new TitulaireRepository(application);
        mAllTitulaire = mRepository.getAllTitulaire();
    }

    public LiveData<List<Titulaires>> getAllTitulaire() { return mAllTitulaire; }

    public void insert(Titulaires titulaire) { mRepository.insert(titulaire); }
}
