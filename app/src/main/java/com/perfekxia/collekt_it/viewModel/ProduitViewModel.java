package com.perfekxia.collekt_it.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.perfekxia.collekt_it.model.Produits;
import com.perfekxia.collekt_it.repository.ProduitRepository;

import java.util.List;

public class ProduitViewModel extends AndroidViewModel {
    private ProduitRepository mRepository;

    private LiveData<List<Produits>> mAllProduit;

    public ProduitViewModel (Application application) {
        super(application);
        mRepository = new ProduitRepository(application);
        mAllProduit = mRepository.getAllProduit();
    }

   public LiveData<List<Produits>> getAllProduit() { return mAllProduit; }

    public void insert(Produits produit) { mRepository.insert(produit); }

    public void deleteAll() { mRepository.deleteAll(); }
}
