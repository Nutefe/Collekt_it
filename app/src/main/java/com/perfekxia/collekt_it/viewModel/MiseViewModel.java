package com.perfekxia.collekt_it.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.perfekxia.collekt_it.model.Mises;
import com.perfekxia.collekt_it.repository.MiseRepository;

import java.util.List;

public class MiseViewModel extends AndroidViewModel {
    private MiseRepository mRepository;

    private LiveData<List<Mises>> mAllMise;
    private LiveData<List<Mises>> mAllNewMise;
    private LiveData<List<Mises>> mAllNewMiseByCycle;
    private LiveData<Mises> mMise;

    public MiseViewModel (Application application,String numeroCycle) {
        super(application);
        mRepository = new MiseRepository(application,numeroCycle);
        mAllMise = mRepository.getAllMise();
        mMise = mRepository.getMiseByCycle();
        mAllMise = mRepository.getAllNewMise();
        mAllNewMiseByCycle = mRepository.getAllNewMiseByCycle();
        mAllNewMise = mRepository.getAllNewMise();
    }

    public LiveData<List<Mises>> getAllMise() { return mAllMise; }

    public LiveData<List<Mises>> getAllNewMise() { return mAllNewMise; }

    public LiveData<List<Mises>> getAllNewMiseByCyle() { return mAllNewMiseByCycle; }

    public LiveData<Mises> getMiseByCycle() { return mMise; }

    public void insert(Mises mise) { mRepository.insert(mise); }

    public void update(Mises mise) { mRepository.update(mise); }

    public void delete(Mises mise) { mRepository.delete(mise); }

    public void deleteAll() { mRepository.deleteAll(); }
}
