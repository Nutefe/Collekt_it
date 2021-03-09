package com.perfekxia.collekt_it.ui.recuperation;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;


import com.perfekxia.collekt_it.R;
import com.perfekxia.collekt_it.api_manager.GetDataService;
import com.perfekxia.collekt_it.api_manager.RetrofitClientInstance;
import com.perfekxia.collekt_it.model.AdminPins;
import com.perfekxia.collekt_it.model.Clients;
import com.perfekxia.collekt_it.model.Collectes;
import com.perfekxia.collekt_it.model.Comptes;
import com.perfekxia.collekt_it.model.Cycles;
import com.perfekxia.collekt_it.model.Mises;
import com.perfekxia.collekt_it.model.Produits;
import com.perfekxia.collekt_it.model.Retraits;
import com.perfekxia.collekt_it.model.Utilisateurs;
import com.perfekxia.collekt_it.preference.PrefManager;
import com.perfekxia.collekt_it.viewModel.ClientViewModel;
import com.perfekxia.collekt_it.viewModel.CollecteViewModel;
import com.perfekxia.collekt_it.viewModel.CompteViewModel;
import com.perfekxia.collekt_it.viewModel.CycleViewModel;
import com.perfekxia.collekt_it.viewModel.MiseViewModel;
import com.perfekxia.collekt_it.viewModel.ProduitViewModel;
import com.perfekxia.collekt_it.viewModel.RetraitViewModel;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecuperationViewModel extends ViewModel {
    Application application;
    Context context;
    private CycleViewModel cycleViewModel;
    private ClientViewModel clientViewModel;
    private MiseViewModel miseViewModel;
    private CollecteViewModel collecteViewModel;
    private CompteViewModel compteViewModel;
    private ProduitViewModel produitViewModel;
    private RetraitViewModel retraitViewModel;
    private MutableLiveData<String> mText;

    ProgressDialog pd;
    PrefManager prefManager;

    public RecuperationViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is notifications fragment");
    }

    public void init(Context context,Application application){
        prefManager = new PrefManager(context);
        pd = new ProgressDialog(context);
        pd.setMessage("Chargement des données en cours...");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        produitViewModel = new ProduitViewModel(application);
        retraitViewModel = new RetraitViewModel(application);

        cycleViewModel = new CycleViewModel(context, application,"A");
        miseViewModel = new MiseViewModel(application,"A");
        collecteViewModel = new CollecteViewModel(application,"A");
        cycleViewModel.deleteAll();
        miseViewModel.deleteAll();
        collecteViewModel.deleteAll();
        compteViewModel = new CompteViewModel(application);
        compteViewModel.getAllCompte().observe((LifecycleOwner) context, new Observer<List<Comptes>>() {
            @Override
            public void onChanged(List<Comptes> comptes) {
                if(comptes.size()!=0){
                    for (Comptes co: comptes
                    ) {
                        loadCycles(context,application,co.getIdCompte());
                    }
                } else {
                    pd.dismiss();
                }
            }
        });


    }

    public void recupererTout(Context context2,Application application2){

        pd = new ProgressDialog(context2);
        pd.setMessage("Chargement des données en cours...");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        prefManager = new PrefManager(context2);
        clientViewModel = new ClientViewModel(application2,"A");
        compteViewModel = new CompteViewModel(application2);
        cycleViewModel = new CycleViewModel(context2, application2,"A");
        miseViewModel = new MiseViewModel(application2,"A");
        collecteViewModel = new CollecteViewModel(application2,"A");
        produitViewModel = new ProduitViewModel(application2);
        retraitViewModel = new RetraitViewModel(application2);


        produitViewModel.deleteAll();
        retraitViewModel.deleteAll();
        clientViewModel.deleteAll();
        compteViewModel.deleteAll();
        cycleViewModel.deleteAll();
        miseViewModel.deleteAll();
        collecteViewModel.deleteAll();

        loadProduit(context2);
        recupAllClients(context2, application2,prefManager.getLogin().getIdZone());



//        cycleViewModel = new CycleViewModel(application2,"A");
//        miseViewModel = new MiseViewModel(application2,0);
//        collecteViewModel = new CollecteViewModel(application2,0);
//        cycleViewModel.deleteAll();
//        miseViewModel.deleteAll();
//        collecteViewModel.deleteAll();
//        compteViewModel = new CompteViewModel(application2);
//        compteViewModel.getAllCompte().observe((LifecycleOwner) context2, new Observer<List<Comptes>>() {
//            @Override
//            public void onChanged(List<Comptes> comptes) {
//                if(comptes.size()!=0){
//                    for (Comptes co: comptes
//                    ) {
//                        recupAllCycle(co.getIdCompte(),context2);
//                    }
//                } else {
//                    pd.dismiss();
//                }
//            }
//        });
//        cycleViewModel.getAllCycle().observe((LifecycleOwner) context, new Observer<List<Cycles>>() {
//            @Override
//            public void onChanged(List<Cycles> cycles) {
//                if(cycles.size()!=0){
//                    for (Cycles cy: cycles
//                    ) {
//                       recupAllCollectes(cy.getNumeroCycle());
//                    }
//                    for (Cycles cyc: cycles
//                    ) {
//                        recupAllMise(cyc.getNumeroCycle());
//                    }
//                }
//            }
//        });
    }

    public LiveData<String> getText() {
        return mText;
    }


    public void checkAdminPin(Integer pin,Context context1,Application application1){
        RetrofitClientInstance instance = new RetrofitClientInstance(context1);
        GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
        Call<AdminPins> call = service.getPin(pin);

        call.enqueue(new Callback<AdminPins>() {
            @Override
            public void onResponse(Call<AdminPins> call, Response<AdminPins> response) {
                if (response.isSuccessful()){
                    Log.i("good pin:", response.message());
                  recupererTout(context1,application1);
                } else {
                    Log.i("bad pin:", response.message());
                RecuperationFragment.errorAdmin.setText("Vous n'etes pas authorisé");
                }
            }

            @Override
            public void onFailure(Call<AdminPins> call, Throwable t) {
//                Log.i("Error :", t.getMessage());
            }
        });
    }

    public void updateUtilisateur(Utilisateurs utilisateurs){
        RetrofitClientInstance instance = new RetrofitClientInstance(context);
        GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
        Call<Utilisateurs> call = service.updateUtilisateur(utilisateurs.getNomUtilisateur(),utilisateurs);

        call.enqueue(new Callback<Utilisateurs>() {
            @Override
            public void onResponse(Call<Utilisateurs> call, Response<Utilisateurs> response) {
                if (response.isSuccessful()){
                    Log.i("update utilisateur:", response.message());
                } else {
                    Log.i("error utilixateur:", response.message());
                }
            }
            @Override
            public void onFailure(Call<Utilisateurs> call, Throwable t) {
//                Log.i("Error :", t.getMessage());
            }
        });
    }
    public void recupAllCycle(String idCompte, Context context1){

        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(context1);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<List<Cycles>> call = service.getActiveCycleRecoveredByCompte(idCompte);


            call.enqueue(new Callback<List<Cycles>>() {
                @Override
                public void onResponse(Call<List<Cycles>> call, Response<List<Cycles>> response) {
                    if (response.isSuccessful()){

                        List<Cycles> cyclesList = response.body();

                        Log.i("Liste cycles :", cyclesList.toString());
                        for (Cycles cycles:
                                response.body()) {
                            cycleViewModel.insert(cycles);
                        }
                        if(cyclesList.size()!=0){
                            for (Cycles cy: cyclesList
                            ) {
                                recupAllCollectes(cy.getNumeroCycle(),context1);
                            }
                            for (Cycles cyc: cyclesList
                            ) {
                                recupAllMise(cyc.getNumeroCycle(),context1);
                            }
                            pd.dismiss();
                        } else {
                            pd.dismiss();
                        }
                    } else {
                        Log.i("error :", response.message());
                        pd.dismiss();
                        RecuperationFragment.error.setText(R.string.error_sync);
                    }
                }

                @Override
                public void onFailure(Call<List<Cycles>> call, Throwable t) {
                    pd.dismiss();
                    RecuperationFragment.error.setText(R.string.error_sync);
                }
            });
        }catch (Exception ex){
            RecuperationFragment.error.setText("Impossible de joindre le serveur.Veuillez recommencer1");
            pd.dismiss();
        }
    }

    public void recupAllMise(String numeroCycle,Context contex1){

        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(contex1);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<Mises> call = service.getRecoveredMiseByCycle(numeroCycle);


            call.enqueue(new Callback<Mises>() {
                @Override
                public void onResponse(Call<Mises> call, Response<Mises> response) {
                    if (response.isSuccessful()){

                        Log.i("Liste mises :", response.body().toString());
                        miseViewModel.insert(response.body());


                    } else {
                        Log.i("error :", response.message());
                        RecuperationFragment.error.setText(R.string.error_sync);
                    }
                }

                @Override
                public void onFailure(Call<Mises> call, Throwable t) {

                    RecuperationFragment.error.setText(R.string.error_sync);
                }
            });
        }catch (Exception ex){
            RecuperationFragment.error.setText("Impossible de joindre le serveur.Veuillez recommencer2");
        }
    }

    public void  recupAllCollectes(String numeroCycle,Context context1){
        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(context1);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<List<Collectes>> call = service.getCollecteRecoveredByCycle(numeroCycle);


            call.enqueue(new Callback<List<Collectes>>() {
                @Override
                public void onResponse(Call<List<Collectes>> call, Response<List<Collectes>> response) {
                    if (response.isSuccessful()){

                        List<Collectes> collectesList = response.body();

                        Log.i("Liste collectes :", collectesList.toString());
                        for (Collectes collectes:
                                response.body()) {
                            collecteViewModel.insert(collectes);
                        }
                    } else {
                        Log.i("error :", response.message());
                        RecuperationFragment.error.setText(R.string.error_sync);
                    }
                }

                @Override
                public void onFailure(Call<List<Collectes>> call, Throwable t) {

                    RecuperationFragment.error.setText(R.string.error_sync);
                }
            });
        }catch (Exception ex){
            RecuperationFragment.error.setText("Impossible de joindre le serveur.Veuillez recommencer3");
        }
    }

    public void loadRetrait(Context context){

        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(context);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<List<Retraits>> call = service.listRetraits();

            call.enqueue(new Callback<List<Retraits>>() {
                @Override
                public void onResponse(Call<List<Retraits>> call, Response<List<Retraits>> response) {
                    if (response.isSuccessful()){

                        List<Retraits> retraits = response.body();

                        Log.i("Liste retraits :", retraits.toString());
                        if (response.body().size() > 0){
                            for (Retraits retraits1:
                                    response.body()) {
                                retraitViewModel.insert(retraits1);
                            }
                        } else {
                            pd.dismiss();
//                            DashboardFragment.error.setText(R.string.success_sync);
                        }

                    } else {
                        Log.i("error :", response.message());
                        pd.dismiss();
//                        DashboardFragment.error.setText(R.string.success_sync);
                    }
                }

                @Override
                public void onFailure(Call<List<Retraits>> call, Throwable t) {
                    pd.dismiss();
//                    DashboardFragment.error.setText(R.string.success_sync);
                }
            });
        }catch (Exception ex){
            pd.dismiss();
//            DashboardFragment.error.setText(R.string.success_sync);
        }
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
                        for (Produits produits1:
                                response.body()) {
                            produitViewModel.insert(produits1);
                        }

                    } else {
                        Log.i("error :", response.message());
                        RecuperationFragment.error.setText(R.string.error_sync);
                    }
                }

                @Override
                public void onFailure(Call<List<Produits>> call, Throwable t) {

                    RecuperationFragment.error.setText(R.string.error_sync);
                }
            });
        }catch (Exception ex){
            RecuperationFragment.error.setText("Impossible de joindre le serveur.Veuillez recommencer4");
            Log.i("Ecxception error: ",ex.getMessage());
        }
    }

    public void recupAllClients(Context context, Application application,String idZone){
        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(context);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<List<Clients>> call = service.getClientRecoveredByZone(idZone);

            call.enqueue(new Callback<List<Clients>>() {
                @Override
                public void onResponse(Call<List<Clients>> call, Response<List<Clients>> response) {
                    if (response.isSuccessful()){

                        List<Clients> clients = response.body();

                       Log.i("Liste clients :", response.body().toString());
                        if (response.body().size() > 0){
                            for (Clients clients1:
                                    response.body()) {
                               // Log.i("Clients", clients1.toString());
                                clientViewModel.insert(clients1);
                                recupAllComptes(context, application, clients1.getIdClient());
                            }

                        } else {
                            pd.dismiss();
//                            DashboardFragment.error.setText(R.string.success_sync);
                        }
                        loadRetrait(context);
                    } else {
                        Log.i("error :", response.message());
                        pd.dismiss();
//                        DashboardFragment.error.setText(R.string.error_sync_first);
                    }
                }

                @Override
                public void onFailure(Call<List<Clients>> call, Throwable t) {
                    pd.dismiss();
//                    DashboardFragment.error.setText(R.string.error_sync_first);
                }
            });
        }catch (Exception ex){
            Log.i("Ecxception error: ",ex.getMessage());
            pd.dismiss();
//            DashboardFragment.error.setText(R.string.error_sync_first);
        }
    }

    public void recupAllComptes(Context context, Application application,String idClient){
        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(context);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<List<Comptes>> call = service.getCompteRecoveredByClient(idClient);


            call.enqueue(new Callback<List<Comptes>>() {
                @Override
                public void onResponse(Call<List<Comptes>> call, Response<List<Comptes>> response) {
                    if (response.isSuccessful()){

                        List<Comptes> comptes = response.body();

                        Log.i("Liste comptes :", comptes.toString());
                        if (response.body().size() > 0){
                            for (Comptes comptes1:
                                    response.body()) {
                                compteViewModel.insert(comptes1);
                                recupAllCycle(comptes1.getIdCompte(),context);
                            }

                        }else {
                            pd.dismiss();
//                            DashboardFragment.error.setText(R.string.success_sync);
                        }

                    } else {

                        Log.i("error :", response.message());
                        pd.dismiss();
//                        DashboardFragment.error.setText(R.string.error_sync_first);
                    }
                }

                @Override
                public void onFailure(Call<List<Comptes>> call, Throwable t) {
                    pd.dismiss();
//                    DashboardFragment.error.setText(R.string.error_sync_first);
                }
            });
        }catch (Exception ex){
            Log.i("Ecxception error: ",ex.getMessage());
            pd.dismiss();
//            DashboardFragment.error.setText(R.string.error_sync_first);
        }
    }


    public void loadClients(Context context, Application application,String idZone){
        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(context);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<List<Clients>> call = service.getClientZone(idZone);
            pd.show();

            call.enqueue(new Callback<List<Clients>>() {
                @Override
                public void onResponse(Call<List<Clients>> call, Response<List<Clients>> response) {
                    if (response.isSuccessful()){

                        List<Clients> clients = response.body();

//                        Log.i("Liste clients :", clients.toString());
                        if (response.body().size() > 0){
                            for (Clients clients1:
                                    response.body()) {
                                Log.i("Clients", clients1.toString());
                                clientViewModel.insert(clients1);
                                loadComptes(context, application, clients1.getIdClient());
                            }

                        } else {
                            pd.dismiss();
//                            DashboardFragment.error.setText(R.string.success_sync);
                        }
                        loadRetrait(context);
                    } else {
                        Log.i("error :", response.message());
                        pd.dismiss();
//                        DashboardFragment.error.setText(R.string.error_sync_first);
                    }
                }

                @Override
                public void onFailure(Call<List<Clients>> call, Throwable t) {
                    pd.dismiss();
//                    DashboardFragment.error.setText(R.string.error_sync_first);
                }
            });
        }catch (Exception ex){
            Log.i("Ecxception error: ",ex.getMessage());
            pd.dismiss();
//            DashboardFragment.error.setText(R.string.error_sync_first);
        }
    }

    public void loadComptes(Context context, Application application,String idClient){
        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(context);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<List<Comptes>> call = service.getCompteByClient(idClient);


            call.enqueue(new Callback<List<Comptes>>() {
                @Override
                public void onResponse(Call<List<Comptes>> call, Response<List<Comptes>> response) {
                    if (response.isSuccessful()){

                        List<Comptes> comptes = response.body();

                        Log.i("Liste comptes :", comptes.toString());
                        if (response.body().size() > 0){
                            for (Comptes comptes1:
                                    response.body()) {
                                compteViewModel.insert(comptes1);
                                loadCycles(context,application, comptes1.getIdCompte());
                            }

                        }else {
                            pd.dismiss();
//                            DashboardFragment.error.setText(R.string.success_sync);
                        }

                    } else {

                        Log.i("error :", response.message());
                        pd.dismiss();
//                        DashboardFragment.error.setText(R.string.error_sync_first);
                    }
                }

                @Override
                public void onFailure(Call<List<Comptes>> call, Throwable t) {
                    pd.dismiss();
//                    DashboardFragment.error.setText(R.string.error_sync_first);
                }
            });
        }catch (Exception ex){
            Log.i("Ecxception error: ",ex.getMessage());
            pd.dismiss();
//            DashboardFragment.error.setText(R.string.error_sync_first);
        }
    }

    public void loadCycles(Context context, Application application,String idCompte){
        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(context);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<List<Cycles>> call = service.getActiveCycleByCompte(idCompte);

            call.enqueue(new Callback<List<Cycles>>() {
                @Override
                public void onResponse(Call<List<Cycles>> call, Response<List<Cycles>> response) {
                    if (response.isSuccessful()){

                        List<Cycles> cycles = response.body();

                        Log.i("Liste cycles :", cycles.toString());
                        if (response.body().size() > 0){
                            for (Cycles cycles1:
                                    response.body()) {
                                cycleViewModel.insert(cycles1);
                                loadCollectes(context, application, cycles1.getNumeroCycle(), cycles);

                            }
                            for (Cycles item :
                                    cycles) {

                                loadMises(context, item.getNumeroCycle());
                            }
                        } else {
                            pd.dismiss();
//                            DashboardFragment.error.setText(R.string.success_sync);
                        }


                    } else {
                        Log.i("error :", response.message());
                        pd.dismiss();
//                        DashboardFragment.error.setText(R.string.error_sync_first);
                    }
                }

                @Override
                public void onFailure(Call<List<Cycles>> call, Throwable t) {
                    pd.dismiss();
//                    DashboardFragment.error.setText(R.string.error_sync_first);
                }
            });
        }catch (Exception ex){
            Log.i("Ecxception error: ",ex.getMessage());
            pd.dismiss();
//            DashboardFragment.error.setText(R.string.error_sync_first);
        }
    }

    public void loadMises(Context context, String numeroCycle){
        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(context);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<Mises> call = service.getMiseByCycle(numeroCycle);


            call.enqueue(new Callback<Mises>() {
                @Override
                public void onResponse(Call<Mises> call, Response<Mises> response) {
                    if (response.isSuccessful()){

//                        List<Mises> mises = response.body();
                        if (response.body() != null){
                            Log.i("Liste mises :", response.body().toString());
                            miseViewModel.insert(response.body());
//                            DashboardFragment.error.setText(R.string.success_sync);
                            pd.dismiss();
                        } else {
//                            DashboardFragment.error.setText(R.string.success_sync);
                            pd.dismiss();
                        }
                        Log.i("Success","ok");

                    } else {
                        Log.i("error mise", response.message());
                        pd.dismiss();
//                        DashboardFragment.error.setText(R.string.error_sync_first);
                    }
                }

                @Override
                public void onFailure(Call<Mises> call, Throwable t) {
//                    Log.i("Ecxception error ",t.getMessage());
                    pd.dismiss();
//                    DashboardFragment.error.setText(R.string.error_sync_first);
                }
            });
        }catch (Exception ex){
            Log.i("Ecxception error: ",ex.getMessage());
            pd.dismiss();
//            DashboardFragment.error.setText(R.string.error_sync_first);
        }
    }

    public void loadCollectes(Context context, Application application, String numeroCycle, List<Cycles> cycles){
        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(context);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<List<Collectes>> call = service.getCollecteByCycle(numeroCycle);


            call.enqueue(new Callback<List<Collectes>>() {
                @Override
                public void onResponse(Call<List<Collectes>> call, Response<List<Collectes>> response) {
                    if (response.isSuccessful()){

                        List<Collectes> collectes = response.body();

                        if (response.body().size() > 0){
                            Log.i("Liste collectes :", collectes.toString());
                            for (Collectes collectes1:
                                    response.body()) {
                                collecteViewModel.insert(collectes1);
                            }
                            CycleViewModel cycleViewModel1 = new CycleViewModel(context, application,"A");
                            cycleViewModel1.getAllCycleClot().observe((LifecycleOwner) context, new Observer<List<Cycles>>() {
                                @Override
                                public void onChanged(List<Cycles> cycles) {
                                    for (Cycles c:cycles
                                    ) {
                                        CollecteViewModel collecteViewModel = new CollecteViewModel(application,c.getNumeroCycle());
                                        MiseViewModel miseViewModel1 = new MiseViewModel(application,c.getNumeroCycle());
                                        miseViewModel1.getMiseByCycle().observe((LifecycleOwner) context, new Observer<Mises>() {
                                            @Override
                                            public void onChanged(Mises mises) {
                                                miseViewModel1.delete(mises);
                                            }
                                        });
                                        collecteViewModel.getmAllCollecteByCycle().observe((LifecycleOwner) context, new Observer<List<Collectes>>() {
                                            @Override
                                            public void onChanged(List<Collectes> collectes) {
                                                for (Collectes co: collectes
                                                ) {
                                                    collecteViewModel.delete(co);
                                                }
                                            }
                                        });
                                        cycleViewModel1.delete(c);
                                    }
                                }
                            });

//                            for (Cycles item :
//                                    cycles) {
//
//                                loadMises(context, item.getNumeroCycle());
//                            }
                            Log.i("Success :", response.message());
                        } else {
//                            DashboardFragment.error.setText(R.string.success_sync);
                            pd.dismiss();
                        }

                    } else {
                        Log.i("error :", response.message());
                        pd.dismiss();
//                        DashboardFragment.error.setText(R.string.error_sync_first);
                    }
                }

                @Override
                public void onFailure(Call<List<Collectes>> call, Throwable t) {
                    pd.dismiss();
//                    DashboardFragment.error.setText(R.string.error_sync_first);
                }
            });
        }catch (Exception ex){
            Log.i("Ecxception error: ",ex.getMessage());
            pd.dismiss();
//            DashboardFragment.error.setText(R.string.error_sync_first);
        }
    }


}