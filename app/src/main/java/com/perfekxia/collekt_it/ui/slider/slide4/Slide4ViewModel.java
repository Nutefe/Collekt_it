package com.perfekxia.collekt_it.ui.slider.slide4;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.perfekxia.collekt_it.R;
import com.perfekxia.collekt_it.api_manager.GetDataService;
import com.perfekxia.collekt_it.api_manager.RetrofitClientInstance;
import com.perfekxia.collekt_it.model.Affectations;
import com.perfekxia.collekt_it.model.Equipements;
import com.perfekxia.collekt_it.model.Login;
import com.perfekxia.collekt_it.model.TitulaireView;
import com.perfekxia.collekt_it.model.Utilisateurs;
import com.perfekxia.collekt_it.model.Zones;
import com.perfekxia.collekt_it.preference.PrefManager;
import com.perfekxia.collekt_it.repository.AffectationRepository;
import com.perfekxia.collekt_it.repository.LoginRepository;
import com.perfekxia.collekt_it.repository.TitulaireViewRepository;
import com.perfekxia.collekt_it.repository.UtilisateurRepository;
import com.perfekxia.collekt_it.repository.ZoneRepository;
import com.perfekxia.collekt_it.ui.login.LoginActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Slide4ViewModel extends ViewModel {
    Context context;
    String deviceID = Build.ID;
    private UtilisateurRepository utilisateurRepository;
    private AffectationRepository affectationRepository;
    private TitulaireViewRepository titulaireViewRepository;
    private ZoneRepository zoneRepository;
    private LoginRepository loginRepository;

    private LiveData<List<Utilisateurs>> mAllUtilisateur;
    private LiveData<List<Affectations>> mAllAffectation;
    private LiveData<List<TitulaireView>> titulaires;
    private LiveData<List<Zones>> zones;

    PrefManager prefManager;

    public void init(Context context) {
        this.context = context;
        utilisateurRepository = new UtilisateurRepository(context);
        affectationRepository = new AffectationRepository(context);
        titulaireViewRepository = new TitulaireViewRepository(context);
        titulaires = titulaireViewRepository.getAllClientView();
        zoneRepository = new ZoneRepository(context);
        loginRepository = new LoginRepository(context);
        zones = zoneRepository.getAllZone();
        prefManager = new PrefManager(context);
    }

    public LiveData<List<TitulaireView>> getAllTitulaireView() { return titulaires; }

    public LiveData<List<Zones>> getAllZone() { return zones; }

    public  Long insertLogin(Login login){
        return  loginRepository.insert(login);
    }

    public void saveEquipement(Equipements equipement){
        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(context);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<Equipements> call = service.saveEquipement(equipement);

            call.enqueue(new Callback<Equipements>() {
                @Override
                public void onResponse(Call<Equipements> call, Response<Equipements> response) {
                    if (response.isSuccessful()){
                        Log.i("save :", response.message());
                    } else {
                        Log.i("error :", response.message());
                    }
                }

                @Override
                public void onFailure(Call<Equipements> call, Throwable t) {
//                    Log.i("Error :", t.getMessage());
                }
            });
        }catch (Exception ex){

        }
    }

    private void getAuthorization(){
        prefManager.setFirstTimeLaunch(false);
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    public void checkAuthorization(String idDevice, String idTitulaire) {
        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(context);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<Equipements> call = service.getEquipement(idDevice, idTitulaire);

            call.enqueue(new Callback<Equipements>() {
                @Override
                public void onResponse(Call<Equipements> call, Response<Equipements> response) {
                    if (response.isSuccessful()) {
                        Log.i("Success :", response.message());
                        Equipements equipements = response.body();
                        if (equipements != null) {
                        Log.i("Get equipement", response.body().toString());
                            if (equipements.getAutorisation() && equipements.getEtat()) {
                                prefManager.setEquipement(equipements);
                                getAuthorization();
                            }
                        } else {
//                            checkEquipementAssignTitulaire(deviceID, prefManager.getEquipement().getIdTitulaire());
                            Slide4Fragment.txtError.setVisibility(View.VISIBLE);
                            Slide4Fragment.txtError.setText(R.string.error_auth);
                        }
                        Log.i("Get equipement", response.message());
                    } else {
                        Log.i("error :", response.message());
                    }
                }

                @Override
                public void onFailure(Call<Equipements> call, Throwable t) {
//                    Log.i("Error :", t.getMessage());
                }
            });
        } catch (Exception ex) {

        }
    }
}
