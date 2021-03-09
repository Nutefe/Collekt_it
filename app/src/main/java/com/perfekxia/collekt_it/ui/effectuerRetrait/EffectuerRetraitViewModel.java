package com.perfekxia.collekt_it.ui.effectuerRetrait;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.perfekxia.collekt_it.api_manager.GetDataService;
import com.perfekxia.collekt_it.api_manager.RetrofitClientInstance;
import com.perfekxia.collekt_it.model.Cycles;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EffectuerRetraitViewModel extends AndroidViewModel {
    ProgressDialog pd;
    public EffectuerRetraitViewModel(@NonNull Application application) {
        super(application);
    }
    public void init(Context context,Application application,String idCompte){
        pd = new ProgressDialog(context);
        pd.setMessage("Chargement des données en cours...");
        pd.setCanceledOnTouchOutside(false);
        loadCycles(context,application,idCompte);
    }

    public void loadCycles(Context context, Application application,String idCompte){
        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(context);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<List<Cycles>> call = service.getActiveCycle(idCompte);
            pd.show();
            call.enqueue(new Callback<List<Cycles>>() { 
                @Override
                public void onResponse(Call<List<Cycles>> call, Response<List<Cycles>> response) {
                    if (response.isSuccessful()){

                        List<Cycles> cycles = response.body();

                        Log.i("Liste cycles :", cycles.toString());
                        if (response.body().size() > 0){
                           EffectuerRetraitActivity.adapter.setCycles(cycles);
                            pd.dismiss();
                        }else {
                            pd.dismiss();
                        }

                    } else {
                        Log.i("error :", response.message());
                        pd.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<List<Cycles>> call, Throwable t) {
                    pd.dismiss();
                }
            });
        }catch (Exception ex){
            Log.i("Ecxception error: ",ex.getMessage());
            pd.dismiss();

        }
    }
}
