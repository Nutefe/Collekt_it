package com.perfekxia.collekt_it.viewModel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.perfekxia.collekt_it.api_manager.GetDataService;
import com.perfekxia.collekt_it.api_manager.RetrofitClientInstance;
import com.perfekxia.collekt_it.model.Cycles;
import com.perfekxia.collekt_it.model.Equipements;
import com.perfekxia.collekt_it.repository.CycleRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CycleViewModel extends AndroidViewModel {
    private CycleRepository mRepository;

    private LiveData<List<Cycles>> mAllCycle;
    private LiveData<List<Cycles>> mAllNewCycle;
    private LiveData<List<Cycles>> mAllCycleClot;
    private LiveData<List<Cycles>> mAllCycleEnCours;
    private LiveData<List<Cycles>> mAllCycleCompte;
    private LiveData<List<Cycles>> mAllCycleValide;
    private LiveData<List<Cycles>> mAllCycleValideNew;
    private LiveData<List<Cycles>> mAllCycleValideOld;
    private LiveData<Cycles> myCycle;

    Context context;


    public CycleViewModel (Context context, Application application,String idCompte) {
        super(application);

        this.context = context;

        mRepository = new CycleRepository(application,idCompte);
        mAllCycle = mRepository.getAllCycle();
        myCycle = mRepository.getCycleByAccount();
        mAllNewCycle = mRepository.getAllNewCycle();
        mAllCycleClot = mRepository.getAllCycleClot();
        mAllCycleEnCours = mRepository.getAllCycleEnCours();
        mAllCycleCompte = mRepository.getAllCycleByCompte();
        mAllCycleValide = mRepository.getAllCycleValide();
        mAllCycleValideNew = mRepository.getAllCycleValideNew();
        mAllCycleValideOld = mRepository.getAllOldCycleEnCours();
    }

   public LiveData<List<Cycles>> getAllCycle() { return mAllCycle; }

   public LiveData<List<Cycles>> getAllOldCycleEnCours() { return mAllCycleValideOld; }

   public LiveData<List<Cycles>> getmAllCycleCompte() { return mAllCycleCompte; }

   public LiveData<List<Cycles>> getmAllCycleValide() { return mAllCycleValide; }

   public LiveData<List<Cycles>> getmAllCycleValideNew() { return mAllCycleValideNew; }

   public LiveData<List<Cycles>> getmAllCycleEnCours() { return mAllCycleEnCours; }

   public LiveData<List<Cycles>> getAllCycleClot() { return mAllCycleClot; }

   public LiveData<List<Cycles>> getAllNewCycle() { return mAllNewCycle; }

   public LiveData<Cycles> getCycleByAccount() { return myCycle; }

    public Long insert(Cycles cycle) { return mRepository.insert(cycle); }

    public void update(Cycles cycle) { mRepository.update(cycle); }

    public void delete(Cycles cycle) { mRepository.delete(cycle); }

    public void deleteAll() { mRepository.deleteAll(); }

    public void updateCycle(Cycles cycles){
        RetrofitClientInstance instance = new RetrofitClientInstance(context);
        GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
        Call<Cycles> call = service.updateCycle(cycles.getNumeroCycle(), cycles);

        call.enqueue(new Callback<Cycles>() {
            @Override
            public void onResponse(Call<Cycles> call, Response<Cycles> response) {
                if (response.isSuccessful()){
                    Log.i("save :", response.message());
                } else {
                    Log.i("error :", response.message());
                }
            }

            @Override
            public void onFailure(Call<Cycles> call, Throwable t) {
                Log.i("Error :", t.getMessage());
            }
        });
    }
}
