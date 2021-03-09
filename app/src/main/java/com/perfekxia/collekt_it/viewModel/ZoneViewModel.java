package com.perfekxia.collekt_it.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.perfekxia.collekt_it.model.Zones;
import com.perfekxia.collekt_it.repository.ZoneRepository;

import java.util.List;

public class ZoneViewModel extends AndroidViewModel {
    private ZoneRepository mRepository;

    private LiveData<List<Zones>> mAllZone;

    public ZoneViewModel (Application application) {
        super(application);
        mRepository = new ZoneRepository(application);
        mAllZone = mRepository.getAllZone();
    }

    LiveData<List<Zones>> getAllZone() { return mAllZone; }

    public void insert(Zones zone) { mRepository.insert(zone); }
}
