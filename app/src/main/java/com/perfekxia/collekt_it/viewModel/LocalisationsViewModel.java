package com.perfekxia.collekt_it.viewModel;

import android.app.Application;


import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.perfekxia.collekt_it.model.Localisations;
import com.perfekxia.collekt_it.repository.LocalisationsRepository;

import java.util.List;

public class LocalisationsViewModel extends AndroidViewModel {

    private LocalisationsRepository mRepository;
    private LiveData<List<Localisations>> mAllLocalisations;

    public LocalisationsViewModel(Application application) {
        super(application);
        mRepository = new LocalisationsRepository(application);
        mAllLocalisations = mRepository.getAllLocalisations();
    }

   public LiveData<List<Localisations>> getAllLocalisations() { return mAllLocalisations; }

    public void insert(Localisations localisations) { mRepository.insert(localisations); }
}
