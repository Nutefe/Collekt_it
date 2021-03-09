package com.perfekxia.collekt_it.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.perfekxia.collekt_it.model.Retraits;
import com.perfekxia.collekt_it.repository.RetraitRepository;

import java.util.List;

public class RetraitViewModel extends AndroidViewModel {
    private RetraitRepository mRepository;

    private LiveData<List<Retraits>> mAllRetrait;

    public RetraitViewModel (Application application) {
        super(application);
        mRepository = new RetraitRepository(application);
        mAllRetrait = mRepository.getAllRetrait();
    }

    LiveData<List<Retraits>> getAllRetrait() { return mAllRetrait; }

    public void insert(Retraits retrait) { mRepository.insert(retrait); }

    public void deleteAll() { mRepository.deleteAll(); }
}
