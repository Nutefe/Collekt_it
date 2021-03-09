package com.perfekxia.collekt_it.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.perfekxia.collekt_it.model.Affectations;
import com.perfekxia.collekt_it.repository.AffectationRepository;

import java.util.List;

public class AffectationViewModel extends AndroidViewModel {
    private AffectationRepository mRepository;

    private LiveData<List<Affectations>> mAllAffectation;

    public AffectationViewModel (Application application) {
        super(application);
        mRepository = new AffectationRepository(application);
        mAllAffectation = mRepository.getAllAffectation();
    }

    LiveData<List<Affectations>> getAllAffectation() { return mAllAffectation; }

    public void insert(Affectations affectations) { mRepository.insert(affectations); }
}
