package com.perfekxia.collekt_it.ui.slider.slide2;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.perfekxia.collekt_it.R;
import com.perfekxia.collekt_it.api_manager.GetDataService;
import com.perfekxia.collekt_it.api_manager.RetrofitClientInstance;
import com.perfekxia.collekt_it.model.Affectations;
import com.perfekxia.collekt_it.model.Utilisateurs;
import com.perfekxia.collekt_it.model.Zones;
import com.perfekxia.collekt_it.repository.AffectationRepository;
import com.perfekxia.collekt_it.repository.UtilisateurRepository;
import com.perfekxia.collekt_it.repository.ZoneRepository;
import com.perfekxia.collekt_it.ui.configuration.WelcomeActivity;
import com.perfekxia.collekt_it.ui.slider.slide1.Slide1Fragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Slide2ViewModel extends ViewModel {

    Context context;

    private UtilisateurRepository mRepository;

    private LiveData<List<Utilisateurs>> mAllUtilisateur;

    private AffectationRepository affectationRepository;

    private LiveData<List<Affectations>> mAllAffectation;

    private ZoneRepository zoneRepository;

    private LiveData<List<Zones>> mAllZones;

    ProgressDialog progressDialog;

    public void init(Context context) {
        this.context = context;
        mRepository = new UtilisateurRepository(context);
        affectationRepository = new AffectationRepository(context);
        zoneRepository = new ZoneRepository(context);

        mRepository.deleteAll();
        affectationRepository.deleteAll();
        zoneRepository.deleteAll();

        loadUser();


    }

    public void insert(Utilisateurs utilisateur) { mRepository.insert(utilisateur); }

    public void insert(Affectations affectations) { affectationRepository.insert(affectations); }

    public void insert(Zones zones) { zoneRepository.insert(zones); }

    public void loadUser(){
        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(context);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<List<Utilisateurs>> call = service.listUtilisateur();

            Slide2Fragment.progressBar.setVisibility(View.VISIBLE);
            WelcomeActivity.btnNext.setVisibility(View.GONE);
            WelcomeActivity.btnSkip.setVisibility(View.GONE);

            call.enqueue(new Callback<List<Utilisateurs>>() {
                @Override
                public void onResponse(Call<List<Utilisateurs>> call, Response<List<Utilisateurs>> response) {
                    if (response.isSuccessful()){

                        List<Utilisateurs> utilisateurs = response.body();

                        for (Utilisateurs utilisateur:
                                response.body()) {
                            insert(utilisateur);
                        }
                        loadAffectation();
                        Log.i("Success :", response.message());
                    } else {
                        Log.i("error :", response.message());
                        Slide2Fragment.textError.setText(R.string.error_sync);
                        Slide2Fragment.progressBar.setVisibility(View.GONE);
//                        WelcomeActivity.btnNext.setVisibility(View.VISIBLE);
                        WelcomeActivity.btnSkip.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<Utilisateurs>> call, Throwable t) {
//                Log.i("Error :", t.getMessage());
                    Slide2Fragment.textError.setText(R.string.error_sync);
                    Slide2Fragment.progressBar.setVisibility(View.GONE);
//                    WelcomeActivity.btnNext.setVisibility(View.VISIBLE);
                    WelcomeActivity.btnSkip.setVisibility(View.VISIBLE);
                }
            });
        }catch (Exception ex){
            Slide2Fragment.textError.setText(R.string.error_address_correct);
        }
    }

    private void loadAffectation(){

        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(context);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<List<Affectations>> call = service.listAffectation();

            call.enqueue(new Callback<List<Affectations>>() {
                @Override
                public void onResponse(Call<List<Affectations>> call, Response<List<Affectations>> response) {
                    if (response.isSuccessful()){
                        for (Affectations affectations:
                                response.body()) {
                            insert(affectations);
//                            Log.i("affectations :", affectations.toString());d
                        }
                        Log.i("Success :", response.message());
                        Log.i("Affectation :", response.message());
                        loadZone();
//                        Slide2Fragment.textError.setText(R.string.success_sync);
//                        Slide2Fragment.progressBar.setVisibility(View.GONE);
//                        WelcomeActivity.btnNext.setVisibility(View.VISIBLE);
//                        WelcomeActivity.btnSkip.setVisibility(View.VISIBLE);
                    } else {
                        Log.i("error :", response.message());
                        Slide2Fragment.textError.setText(R.string.error_sync);
                        Slide2Fragment.progressBar.setVisibility(View.GONE);
//                        WelcomeActivity.btnNext.setVisibility(View.VISIBLE);
                        WelcomeActivity.btnSkip.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<Affectations>> call, Throwable t) {
//                Log.i("Error :", t.getMessage());
                    Slide2Fragment.textError.setText(R.string.error_sync);
                    Slide2Fragment.progressBar.setVisibility(View.GONE);
//                    WelcomeActivity.btnNext.setVisibility(View.VISIBLE);
                    WelcomeActivity.btnSkip.setVisibility(View.VISIBLE);
                }
            });
        }catch (Exception ex){
            Slide2Fragment.textError.setText(R.string.error_address_correct);
        }


    }

    private void loadZone(){

        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(context);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<List<Zones>> call = service.listZones();

            call.enqueue(new Callback<List<Zones>>() {
                @Override
                public void onResponse(Call<List<Zones>> call, Response<List<Zones>> response) {
                    if (response.isSuccessful()){
                        for (Zones zones:
                                response.body()) {
                            insert(zones);
//                            Log.i("affectations :", affectations.toString());
                        }
                        Log.i("Success :", response.message());
                        Log.i("Zone :", response.message());
                        Slide2Fragment.textError.setTextColor(context.getResources().getColor(R.color.blue));
                        Slide2Fragment.textError.setText(R.string.success_sync);

                        Slide2Fragment.progressBar.setVisibility(View.GONE);
                        WelcomeActivity.btnNext.setVisibility(View.VISIBLE);
                        WelcomeActivity.btnSkip.setVisibility(View.VISIBLE);
                    } else {
                        Log.i("error :", response.message());
                        Slide2Fragment.textError.setText(R.string.error_sync);
                        Slide2Fragment.progressBar.setVisibility(View.GONE);
//                        WelcomeActivity.btnNext.setVisibility(View.VISIBLE);
                        WelcomeActivity.btnSkip.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<Zones>> call, Throwable t) {
//                Log.i("Error :", t.getMessage());
                    Slide2Fragment.textError.setText(R.string.error_sync);
                    Slide2Fragment.progressBar.setVisibility(View.GONE);
                    WelcomeActivity.btnNext.setVisibility(View.VISIBLE);
                    WelcomeActivity.btnSkip.setVisibility(View.VISIBLE);
                }
            });
        }catch (Exception ex){
            Slide2Fragment.textError.setText(R.string.error_address_correct);
        }


    }
}