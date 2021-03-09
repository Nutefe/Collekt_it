package com.perfekxia.collekt_it.ui.listescomptespourretraits;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.perfekxia.collekt_it.R;
import com.perfekxia.collekt_it.api_manager.GetDataService;
import com.perfekxia.collekt_it.api_manager.RetrofitClientInstance;
import com.perfekxia.collekt_it.model.Comptes;
import com.perfekxia.collekt_it.model.Produits;
import com.perfekxia.collekt_it.model.Zones;
import com.perfekxia.collekt_it.ui.dashboard.DashboardFragment;
import com.perfekxia.collekt_it.ui.effectuerRetrait.EffectuerRetraitActivity;
import com.perfekxia.collekt_it.ui.listeClients.ListeClientsActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListeComptesRetraitsViewModel extends AndroidViewModel {
    ProgressDialog pd;
    private LiveData<List<Comptes>> data;
    public ListeComptesRetraitsViewModel(@NonNull Application application) {
        super(application);
    }
    public void init(Context context){
        pd = new ProgressDialog(context);
        pd.setMessage("Chargement des données en cours...");
        pd.setCanceledOnTouchOutside(false);
        loadProduit(context);
    }

    public void loadComptes(Context context){
        pd = new ProgressDialog(context);
        pd.setMessage("Chargement des données en cours...");
        pd.setCanceledOnTouchOutside(false);
    }

    public void loadProduit(Context context){
        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(context);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<List<Produits>> call = service.listProduits();
            pd.show();

            call.enqueue(new Callback<List<Produits>>() {
                @Override
                public void onResponse(Call<List<Produits>> call, Response<List<Produits>> response) {
                    if (response.isSuccessful()){

                        List<Produits> produits = response.body();
                        Log.i("Liste produits :", response.body().toString());
                        if (produits.size()>0){
                            ArrayAdapter aa = new ArrayAdapter(context,android.R.layout.simple_spinner_item,produits);
                            ListeComptesRetraitsActivity.spinnerProduit.setAdapter(aa);
                            pd.dismiss();
                        }else{
                            pd.dismiss();
                        }
                    } else {
                        Log.i("error :", response.message());
                        Toast.makeText(context, "Erreur d'accès aux données!Veuillez rafraichir la page",
                                Toast.LENGTH_LONG).show();
                        pd.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<List<Produits>> call, Throwable t) {
                    pd.dismiss();
                }
            });
        }catch (Exception ex){
            pd.dismiss();
            Log.i("Ecxception error: ",ex.getMessage());

        }
    }

    public void loadZones(Context context){
        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(context);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<List<Zones>> call = service.listZones();


            call.enqueue(new Callback<List<Zones>>() {
                @Override
                public void onResponse(Call<List<Zones>> call, Response<List<Zones>> response) {
                    if (response.isSuccessful()){

                        List<Zones> zones = response.body();
                        Log.i("Liste Zones :", response.body().toString());
                        if (zones.size()>0){
                            ArrayAdapter aa = new ArrayAdapter(context,android.R.layout.simple_spinner_item,zones);
                           // ListeComptesRetraitsActivity.spinnerZone.setAdapter(aa);
                            pd.dismiss();
                        }else{
                            pd.dismiss();
                        }
                    } else {
                        Log.i("error :", response.message());
                        pd.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<List<Zones>> call, Throwable t) {
                    pd.dismiss();
                }
            });
        }catch (Exception ex){
            pd.dismiss();
            Log.i("Ecxception error: ",ex.getMessage());
        }
    }

//    public void recherche(Context context){
//
//        Intent activity2Intent = new Intent(context, EffectuerRetraitActivity.class);
//        activity2Intent.putExtra("EXTRA_COMPTES",comptes.getIdCompte());
//        context.startActivity(activity2Intent);
//    }

    public void loadComptes(Context context,String idProuit){
        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(context);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<List<Comptes>> call = service.accountsByProducts(idProuit);

            call.enqueue(new Callback<List<Comptes>>() {
                @Override
                public void onResponse(Call<List<Comptes>> call, Response<List<Comptes>> response) {
                    if (response.isSuccessful()){

                        List<Comptes> comptes = response.body();
                        ListeComptesRetraitsActivity.comptesList = comptes;
                        Log.i("Liste comptes :", response.body().toString());
                        if (comptes.size()>0){
                         ListeComptesRetraitsActivity.adapter.setCompteView(comptes);
                            pd.dismiss();
                        }else{
                            pd.dismiss();
                        }
                    } else {
                        Log.i("error :", response.message());
                        pd.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<List<Comptes>> call, Throwable t) {
                    pd.dismiss();
                }
            });
        }catch (Exception ex){
            pd.dismiss();
            Log.i("Ecxception error: ",ex.getMessage());
        }
    }
}
