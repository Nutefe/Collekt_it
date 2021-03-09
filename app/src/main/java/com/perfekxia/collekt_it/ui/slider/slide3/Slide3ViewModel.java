package com.perfekxia.collekt_it.ui.slider.slide3;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.perfekxia.collekt_it.api_manager.GetDataService;
import com.perfekxia.collekt_it.api_manager.RetrofitClientInstance;
import com.perfekxia.collekt_it.model.Affectations;
import com.perfekxia.collekt_it.model.Equipements;
import com.perfekxia.collekt_it.model.TitulaireView;
import com.perfekxia.collekt_it.model.Utilisateurs;
import com.perfekxia.collekt_it.model.Zones;
import com.perfekxia.collekt_it.repository.AffectationRepository;
import com.perfekxia.collekt_it.repository.TitulaireViewRepository;
import com.perfekxia.collekt_it.repository.UtilisateurRepository;
import com.perfekxia.collekt_it.repository.ZoneRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Slide3ViewModel extends ViewModel {
    Context context;
    String deviceID = Build.ID;
    private UtilisateurRepository utilisateurRepository;
    private AffectationRepository affectationRepository;
    private TitulaireViewRepository titulaireViewRepository;
    private ZoneRepository zoneRepository;

    private LiveData<List<Utilisateurs>> mAllUtilisateur;
    private LiveData<List<Affectations>> mAllAffectation;
    private LiveData<List<TitulaireView>> titulaires;
    private List<TitulaireView> titulaire1;
    private LiveData<List<Zones>> zones;
    private List<Zones> zone1;



    public void init(Context context) {
        this.context = context;
        utilisateurRepository = new UtilisateurRepository(context);
        affectationRepository = new AffectationRepository(context);
        titulaireViewRepository = new TitulaireViewRepository(context);
        zoneRepository = new ZoneRepository(context);
        titulaires = titulaireViewRepository.getAllClientView();
        titulaire1 = titulaireViewRepository.getAllClientV();
        zones = zoneRepository.getAllZone();
        zone1 = zoneRepository.getZoneV();

    }

    public LiveData<List<TitulaireView>> getAllTitulaireView() { return titulaires; }

    public List<TitulaireView> getAllTitulaireV() { return titulaire1; }

    public LiveData<List<Zones>> getAllZone() { return zones; }

    public List<Zones> getAllZoneV() { return zone1; }

    private void saveEquipement(Equipements equipement){
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
                Log.i("Error :", t.getMessage());
            }
        });
    }

    private void updateEquipement(Equipements equipement, int id){
        RetrofitClientInstance instance = new RetrofitClientInstance(context);
        GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
        Call<Equipements> call = service.updateEquipement(id, equipement);

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
                Log.i("Error :", t.getMessage());
            }
        });
    }

    private void checkEquipementExit(){

        RetrofitClientInstance instance = new RetrofitClientInstance(context);
        GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
        Call<Boolean> call = service.checkEquipementExist(deviceID);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()){
                    Log.i("Success :", response.message());
                } else {
                    Log.i("error :", response.message());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.i("Error :", t.getMessage());
            }
        });
    }

    private void checkEquipementUse(){

        RetrofitClientInstance instance = new RetrofitClientInstance(context);
        GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
        Call<Boolean> call = service.checkEquipementUse(deviceID);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()){
                    Log.i("Success :", response.message());
                } else {
                    Log.i("error :", response.message());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.i("Error :", t.getMessage());
            }
        });
    }

    private void checkEquipementAssignTitulaire(String idDevice,String idTitulaire){

        RetrofitClientInstance instance = new RetrofitClientInstance(context);
        GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
        Call<Boolean> call = service.checkConfirmTitulaire(idDevice, idTitulaire);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()){
                    Log.i("Success :", response.message());
                } else {
                    Log.i("error :", response.message());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.i("Error :", t.getMessage());
            }
        });
    }

    private void checkTitulaireHaveEquipement(String idTitulaire){

        RetrofitClientInstance instance = new RetrofitClientInstance(context);
        GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
        Call<Boolean> call = service.checkEquipementAssigned( idTitulaire);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()){
                    Log.i("Success :", response.message());
                } else {
                    Log.i("error :", response.message());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.i("Error :", t.getMessage());
            }
        });
    }

    private void checkMultipleUsage(String idDevice, String idTitulaire){

        RetrofitClientInstance instance = new RetrofitClientInstance(context);
        GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
        Call<Boolean> call = service.checkMultipleUsage(idDevice, idTitulaire);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()){
                    Log.i("Success :", response.message());
                } else {
                    Log.i("error :", response.message());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.i("Error :", t.getMessage());
            }
        });
    }

    private void checkEquipementById(String id){

        RetrofitClientInstance instance = new RetrofitClientInstance(context);
        GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
        Call<Equipements> call = service.getEquipementExist(id);

        call.enqueue(new Callback<Equipements>() {
            @Override
            public void onResponse(Call<Equipements> call, Response<Equipements> response) {
                if (response.isSuccessful()){
                    Log.i("Success :", response.message());
                } else {
                    Log.i("error :", response.message());
                }
            }

            @Override
            public void onFailure(Call<Equipements> call, Throwable t) {
                Log.i("Error :", t.getMessage());
            }
        });
    }

    private void checkEquipementById(String idDevice, String idTitulaire){

        RetrofitClientInstance instance = new RetrofitClientInstance(context);
        GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
        Call<Equipements> call = service.updateEquipement(idDevice, idTitulaire);

        call.enqueue(new Callback<Equipements>() {
            @Override
            public void onResponse(Call<Equipements> call, Response<Equipements> response) {
                if (response.isSuccessful()){
                    Log.i("Success :", response.message());
                } else {
                    Log.i("error :", response.message());
                }
            }

            @Override
            public void onFailure(Call<Equipements> call, Throwable t) {
                Log.i("Error :", t.getMessage());
            }
        });
    }


}
