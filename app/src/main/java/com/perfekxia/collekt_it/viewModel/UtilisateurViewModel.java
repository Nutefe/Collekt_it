package com.perfekxia.collekt_it.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.perfekxia.collekt_it.model.Utilisateurs;
import com.perfekxia.collekt_it.repository.UtilisateurRepository;

import java.util.List;

public class UtilisateurViewModel extends AndroidViewModel {
    private UtilisateurRepository mRepository;

    private LiveData<List<Utilisateurs>> mAllUtilisateur;

    private LiveData<List<Utilisateurs>> mAllUpdatedUtilisateur;

    public UtilisateurViewModel (Application application) {
        super(application);
        mRepository = new UtilisateurRepository(application);
        mAllUtilisateur = mRepository.getAllUtilisateur();
        mAllUpdatedUtilisateur = mRepository.getAllUpdatedUtilisateur();
    }

    public LiveData<List<Utilisateurs>> getAllUpdatedUtilisateur() { return mAllUpdatedUtilisateur; }

    public LiveData<List<Utilisateurs>> getAllUtilisateur() { return mAllUtilisateur; }

    public void insert(Utilisateurs utilisateur) { mRepository.insert(utilisateur); }
}
