package com.perfekxia.collekt_it.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.perfekxia.collekt_it.model.Equipements;
import com.perfekxia.collekt_it.repository.EquipementRepository;

import java.util.List;

public class EquipementViewModel extends AndroidViewModel {
    private EquipementRepository equipementRepository;

    private LiveData<List<Equipements>> mAllEquipement;

    public EquipementViewModel (Application application) {
        super(application);
        equipementRepository = new EquipementRepository(application);
        mAllEquipement = equipementRepository.getAllEquipement();
    }

    LiveData<List<Equipements>> getAllEquipement() { return mAllEquipement; }

    public void insert(Equipements equipement) { equipementRepository.insert(equipement); }
}
