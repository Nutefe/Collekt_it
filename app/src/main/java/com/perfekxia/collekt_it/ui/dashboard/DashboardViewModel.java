package com.perfekxia.collekt_it.ui.dashboard;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.perfekxia.collekt_it.MainActivity;
import com.perfekxia.collekt_it.R;
import com.perfekxia.collekt_it.api_manager.GetDataService;
import com.perfekxia.collekt_it.api_manager.RetrofitClientInstance;
import com.perfekxia.collekt_it.model.AdminPins;
import com.perfekxia.collekt_it.model.ClientView;
import com.perfekxia.collekt_it.model.Clients;
import com.perfekxia.collekt_it.model.Collectes;
import com.perfekxia.collekt_it.model.Comptes;
import com.perfekxia.collekt_it.model.Cycles;
import com.perfekxia.collekt_it.model.Mises;
import com.perfekxia.collekt_it.model.Produits;
import com.perfekxia.collekt_it.model.Retraits;
import com.perfekxia.collekt_it.model.Utilisateurs;
import com.perfekxia.collekt_it.preference.PrefManager;
import com.perfekxia.collekt_it.ui.recuperation.RecuperationFragment;
import com.perfekxia.collekt_it.viewModel.ClientViewModel;
import com.perfekxia.collekt_it.viewModel.ClientViewViewModel;
import com.perfekxia.collekt_it.viewModel.CollecteViewModel;
import com.perfekxia.collekt_it.viewModel.CompteViewModel;
import com.perfekxia.collekt_it.viewModel.CycleViewModel;
import com.perfekxia.collekt_it.viewModel.MiseViewModel;
import com.perfekxia.collekt_it.viewModel.ProduitViewModel;
import com.perfekxia.collekt_it.viewModel.RetraitViewModel;
import com.perfekxia.collekt_it.viewModel.UtilisateurViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardViewModel extends ViewModel {

    Context context;
    private CycleViewModel cycleViewModel,cycleViewModel2,cycleViewModel3;
    private CollecteViewModel collecteViewModel;
    private MiseViewModel miseViewModel;
    private MutableLiveData<Integer> mNombreClients;
    private MutableLiveData<Integer> mNombreCollectes;
    private MutableLiveData<Integer> mNombreCltVisistes;

    private ClientViewViewModel clientViewViewModel;
    private CycleViewModel cycleViewModel1;
    private ClientViewModel clientViewModel1,clientViewModel;
    private MiseViewModel miseViewModel1,miseViewModel3;
    private CollecteViewModel collecteViewModel1,collecteViewModel2,collecteViewModel3;
    private CompteViewModel compteViewModel1;
    private ProduitViewModel produitViewModel1;
    private RetraitViewModel retraitViewModel1;
    private UtilisateurViewModel utilisateurViewModel;

    int TOTAL_RETRIES = 3;
    int retryCount = 0;

    List<ClientView> clientViewList = new ArrayList<ClientView>();

    PrefManager prefManager;
    private static final String IS_FIRST_RECUP = "IsFirstTimeRecup";
    ProgressDialog pd;

//    dashboardViewModel1.recupererTout(getContext(),getActivity().getApplication());

    public DashboardViewModel() {
    }

    public DashboardViewModel(Context context, Application application, Integer nombreClient, Integer nombreColectes, Integer nombreClientsVisites) {

        this.context = context;

        mNombreClients = new MutableLiveData<>();
        mNombreCollectes = new MutableLiveData<>();
        mNombreCltVisistes = new MutableLiveData<>();
        mNombreClients.setValue(nombreClient);
        mNombreCollectes.setValue(nombreColectes);
        mNombreCltVisistes.setValue(nombreClientsVisites);
    }

    public  void init(Context context, Application application){
        this.context = context;
        prefManager = new PrefManager(context);
        produitViewModel1 = new ProduitViewModel(application);
        retraitViewModel1 = new RetraitViewModel(application);
        clientViewModel1 = new ClientViewModel(application,"A");
        compteViewModel1 = new CompteViewModel(application);
        utilisateurViewModel = new UtilisateurViewModel(application);
        cycleViewModel1 = new CycleViewModel(context,application,"A");
        miseViewModel1 = new MiseViewModel(application,"A");
        collecteViewModel1 = new CollecteViewModel(application,"A");

        pd = new ProgressDialog(context);
        pd.setMessage("Chargement des données en cours...");
        pd.setCanceledOnTouchOutside(false);

        if (!prefManager.checkKey(IS_FIRST_RECUP)){
            produitViewModel1.deleteAll();
            retraitViewModel1.deleteAll();
            clientViewModel1.deleteAll();
            compteViewModel1.deleteAll();
            cycleViewModel1.deleteAll();
            miseViewModel1.deleteAll();
            collecteViewModel1.deleteAll();
            loadProduit(context);
//            Log.i("idzone", prefManager.getLogin().getIdZone());
//            loadClients(context,application,prefManager.getLogin().getIdZone());
//            loadMises(context, 146);
        }
    }

    public LiveData<Integer> getText() {
        return mNombreClients;
    }
    public LiveData<Integer> getNombreCollectes() {
        return mNombreCollectes;
    }
    public LiveData<Integer> getNombreClientsVisites() {
        return mNombreCltVisistes;
    }


    public void synchronisaton(Context context, Application application){

        this.context = context;
        pd = new ProgressDialog(context);
        pd.setMessage("Synchronisation des données en cours...");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        cycleViewModel = new CycleViewModel(context, application,"A");
        cycleViewModel.getAllNewCycle().observe((LifecycleOwner) context, new Observer<List<Cycles>>() {
            @Override
            public void onChanged(List<Cycles> cycles) {
                Log.i("cycles", cycles.toString());
                if (cycles.size()>0){
                    for (Cycles c: cycles
                    ) {
                       saveCycle(c, context, new saveCycleCallbacks() {
                           @Override
                           public void saveCycleCResult(Cycles cycles) {
                               new Handler().postDelayed(new Runnable() {
                                                             @Override
                                                             public void run() {
                                                                 collecteViewModel = new CollecteViewModel(application,cycles.getNumeroCycle());
                                                                 collecteViewModel.getAllNewCollecte().observe((LifecycleOwner) context, new Observer<List<Collectes>>() {
                                                                     @Override
                                                                     public void onChanged(List<Collectes> collectes) {
                                                                         Log.i("liste collectes", collectes.toString());
                                                                         for (Collectes co: collectes
                                                                         ) {
                                                                             saveCollectes(co,context);
                                                                             collecteViewModel.delete(co);
                                                                         }
                                                                     }
                                                                 });

                                                                 miseViewModel = new MiseViewModel(application,cycles.getNumeroCycle());
                                                                 miseViewModel.getAllNewMise().observe((LifecycleOwner) context, new Observer<List<Mises>>() {
                                                                     @Override
                                                                     public void onChanged(List<Mises> mises) {
                                                                         Log.i("liste mises", mises.toString());
                                                                         if(mises.size()>0){
                                                                             for (Mises m: mises
                                                                             ) {
                                                                                 saveMise(m,context);
                                                                                 miseViewModel.delete(m);
                                                                             }
                                                                         }
                                                                     }
                                                                 });
                                                             }
                                                         }, 5000);
                           }

                           @Override
                           public void saveCycleFailed(Throwable error) {
                           }
                       });
                        cycleViewModel.delete(c);

                    }
                }
            }
        });
        cycleViewModel3 = new CycleViewModel(context,application,"A");
        cycleViewModel3.getAllOldCycleEnCours().observe((LifecycleOwner) context, new Observer<List<Cycles>>() {
            @Override
            public void onChanged(List<Cycles> cycles) {
                if (!cycles.isEmpty()){
                    for (Cycles p:cycles
                    ) {
                        collecteViewModel3 = new CollecteViewModel(application,p.getNumeroCycle());
                        collecteViewModel3.getAllNewCollecte().observe((LifecycleOwner) context, new Observer<List<Collectes>>() {
                            @Override
                            public void onChanged(List<Collectes> collectes) {
                                Log.i("liste collectes oldCy", collectes.toString());
                                for (Collectes co: collectes
                                ) {
                                    saveCollectes(co,context);
                                    collecteViewModel3.delete(co);
                                }
                            }
                        });
                    }
                }
            }
        });
        cycleViewModel2 = new CycleViewModel(context, application,"A");
        cycleViewModel2.getAllCycleClot().observe((LifecycleOwner) context, new Observer<List<Cycles>>() {
            @Override
            public void onChanged(List<Cycles> cycles1) {
                if (cycles1.size()>0){
                    for (Cycles c1: cycles1
                    ) {
                        updateCycle(c1,context);
                        cycleViewModel.delete(c1);
                    }
                }

            }
        });

       collecteViewModel2 = new CollecteViewModel(application,"A");
       collecteViewModel2.getmListeDistinctsCollectes().observe((LifecycleOwner) context, new Observer<List<String>>() {
           @Override
           public void onChanged(List<String> strings) {
               for (String s: strings
                    ) {
                   clientViewViewModel = new ClientViewViewModel(application,s,"A");
                   clientViewViewModel.getClientViewById().observe((LifecycleOwner) context, new Observer<ClientView>() {
                       @Override
                       public void onChanged(ClientView clientView) {
                           Log.i("Liste clients", clientViewList.toString());
                           clientViewList.add(clientView);
                       }
                   });
               }
               for (ClientView c: clientViewList
                    ) {
                   clientViewModel = new ClientViewModel(application,c.OldID);
                   clientViewModel.getClient().observe((LifecycleOwner) context, new Observer<Clients>() {
                       @Override
                       public void onChanged(Clients clients) {
                           if(clients!=null){
                               Log.i("Client à update", clients.toString());
                               updateClients(clients,context);
                           }
                       }
                   });
               }
           }
       });
        utilisateurViewModel = new UtilisateurViewModel(application);
        utilisateurViewModel.getAllUpdatedUtilisateur().observe((LifecycleOwner) context, new Observer<List<Utilisateurs>>() {
            @Override
            public void onChanged(List<Utilisateurs> utilisateurs) {
                if (utilisateurs.size()>0){
                    for (Utilisateurs u: utilisateurs
                    ) {
                        updateUtilisateur(u,context);
                    }
                    pd.dismiss();
                }else{
                    pd.dismiss();
                }
            }
        });

    }

    public void updateClients(Clients clients,Context context){
        RetrofitClientInstance instance = new RetrofitClientInstance(context);
        GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
        Call<Clients> call = service.updateClient(clients.getIdClient(),clients);

        call.enqueue(new Callback<Clients>() {
            @Override
            public void onResponse(Call<Clients> call, Response<Clients> response) {
                if (response.isSuccessful()){
                    Log.i("update Clients:", response.message());
                } else {
                    Log.i("error Cycle:", response.message());
                }
            }

            @Override
            public void onFailure(Call<Clients> call, Throwable t) {
//                Log.i("Error :", t.getMessage());
            }
        });
    }

    public void updateCycle(Cycles cycles,Context context){
        RetrofitClientInstance instance = new RetrofitClientInstance(context);
        GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
        Call<Cycles> call = service.updateCycle(cycles.getNumeroCycle(),cycles);

        call.enqueue(new Callback<Cycles>() {
            @Override
            public void onResponse(Call<Cycles> call, Response<Cycles> response) {
                if (response.isSuccessful()){
                    Log.i("update Cycle:", response.message());
                } else {
                    Log.i("error Cycle:", response.message());
                }
            }

            @Override
            public void onFailure(Call<Cycles> call, Throwable t) {
//                Log.i("Error :", t.getMessage());
            }
        });
    }

    public void updateUtilisateur(Utilisateurs utilisateurs,Context context){
        RetrofitClientInstance instance = new RetrofitClientInstance(context);
        GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
        Call<Utilisateurs> call = service.updateUtilisateur(utilisateurs.getNomUtilisateur(),utilisateurs);

        call.enqueue(new Callback<Utilisateurs>() {
            @Override
            public void onResponse(Call<Utilisateurs> call, Response<Utilisateurs> response) {
                if (response.isSuccessful()){
                    Log.i("save Utilisateur:", response.message());
                } else {
                    Log.i("error Utilisateur:", response.message());
                }
            }

            @Override
            public void onFailure(Call<Utilisateurs> call, Throwable t) {
//                Log.i("Error :", t.getMessage());
            }
        });
    }

    public interface saveCycleCallbacks{
        void saveCycleCResult(Cycles cycles);
        void saveCycleFailed(Throwable error);
    }

    public void saveCycle(Cycles cycles,Context context, final saveCycleCallbacks callbacks){
        RetrofitClientInstance instance = new RetrofitClientInstance(context);
        GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
        Call<Cycles> call = service.saveCycle(cycles);

        call.enqueue(new Callback<Cycles>() {
            @Override
            public void onResponse(Call<Cycles> call, Response<Cycles> response) {
                if (response.isSuccessful()){
                    Log.i("save Cycle:", response.message());
                    if (callbacks != null)
                        callbacks.saveCycleCResult(response.body());
                } else {
                    Log.i("error Cycle:", response.message());
                }
            }

            @Override
            public void onFailure(Call<Cycles> call, Throwable t) {
//                Log.i("Error :", t.getMessage());
                if (callbacks != null)
                    callbacks.saveCycleFailed(t);
            }
        });
    }

    public void saveMise(Mises mises,Context context){

        RetrofitClientInstance instance = new RetrofitClientInstance(context);
        GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
        Call<Mises> call = service.saveMise(mises);

        call.enqueue(new Callback<Mises>() {
            @Override
            public void onResponse(Call<Mises> call, Response<Mises> response) {
                if (response.isSuccessful()){
                    Log.i("save Mise:", response.message());
                } else {
                    Log.i("error Mise:", response.message());
                    if (retryCount++ < TOTAL_RETRIES) {
                        retry();
                    }
                }
            }

            @Override
            public void onFailure(Call<Mises> call, Throwable t) {
                Log.i("Error Mise:", t.getMessage());
                if (retryCount++ < TOTAL_RETRIES) {
                    retry();
                }
            }

            private void retry(){
                Log.i("Retry Mise:", "Retry for mise");
                call.clone().enqueue(this);
            }
        });


    }

    public void saveCollectes(Collectes collectes,Context context){

        Log.i("Collecte", collectes.toString());

        RetrofitClientInstance instance = new RetrofitClientInstance(context);
        GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
        Call<Collectes> call = service.saveCollecte(collectes);

        call.enqueue(new Callback<Collectes>() {
            @Override
            public void onResponse(Call<Collectes> call, Response<Collectes> response) {
                if (response.isSuccessful()){
                    Log.i("save Collectes:", response.message());
                } else {
                    Log.i("error Collectes:", response.message());
                    if (retryCount++ < TOTAL_RETRIES) {
                        retry();
                    }
                }
            }

            @Override
            public void onFailure(Call<Collectes> call, Throwable t) {
                Log.i("Error Collectes:", t.getMessage());
                if (retryCount++ < TOTAL_RETRIES) {
                    retry();
                }
            }
            private void retry(){
                Log.i("Retry Collecte:", "Retry for Collecte");
                call.clone().enqueue(this);
            }
        });
    }

    public void recupererTout(Context context,Application application){

        this.context = context;

        pd = new ProgressDialog(context);
        pd.setMessage("Chargement des données en cours...");
        pd.setCanceledOnTouchOutside(false);

        prefManager = new PrefManager(this.context);

        clientViewModel1 = new ClientViewModel(application,"A");
        compteViewModel1 = new CompteViewModel(application);
        cycleViewModel1 = new CycleViewModel(context, application,"A");
        miseViewModel1 = new MiseViewModel(application,"A");
        collecteViewModel1 = new CollecteViewModel(application,"A");
        produitViewModel1 = new ProduitViewModel(application);
        retraitViewModel1 = new RetraitViewModel(application);


        if (!prefManager.checkKey(IS_FIRST_RECUP)){
            produitViewModel1.deleteAll();
            retraitViewModel1.deleteAll();
            clientViewModel1.deleteAll();
            compteViewModel1.deleteAll();
            cycleViewModel1.deleteAll();
            miseViewModel1.deleteAll();
            collecteViewModel1.deleteAll();

            loadProduit(context);
            recupAllClients(context, application, prefManager.getLogin().getIdZone());
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

//                        Log.i("Liste clients :", clients.toString());
                        if (response.body().size() > 0){
                            for (Clients clients1:
                                    response.body()) {
//                                Log.i("Clients", clients1.toString());
                                clientViewModel1.insert(clients1);
                                recupAllComptes(context, application, clients1.getIdClient());
                            }

                        } else {
                            prefManager.setIsFirstRecup(false);
                            pd.dismiss();
                            DashboardFragment.error.setText(R.string.success_sync);
                        }
                        loadRetrait(context);

                    } else {
                        Log.i("error :", response.message());
                        pd.dismiss();
                        DashboardFragment.error.setText(R.string.error_sync_first);
                    }
                }

                @Override
                public void onFailure(Call<List<Clients>> call, Throwable t) {
                    pd.dismiss();
                    DashboardFragment.error.setText(R.string.error_sync_first);
                }
            });
        }catch (Exception ex){
            Log.i("Ecxception error: ",ex.getMessage());
            pd.dismiss();
            DashboardFragment.error.setText(R.string.error_sync_first);
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
                                retraitViewModel1.insert(retraits1);
                            }
                        } else {
                            pd.dismiss();
                            DashboardFragment.error.setText(R.string.success_sync);
                        }

                    } else {
                        Log.i("error :", response.message());
                        pd.dismiss();
                        DashboardFragment.error.setText(R.string.success_sync);
                    }
                }

                @Override
                public void onFailure(Call<List<Retraits>> call, Throwable t) {
                    pd.dismiss();
                    DashboardFragment.error.setText(R.string.success_sync);
                }
            });
        }catch (Exception ex){
            pd.dismiss();
            DashboardFragment.error.setText(R.string.success_sync);
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

//                        List<Produits> produits = response.body();

                        Log.i("Liste produits :", response.body().toString());
                        for (Produits produits1:
                                response.body()) {
                            produitViewModel1.insert(produits1);
                        }

                    } else {
                        Log.i("error :", response.message());
                        DashboardFragment.error.setText(R.string.error_sync_first);
                        pd.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<List<Produits>> call, Throwable t) {
                    pd.dismiss();
                    DashboardFragment.error.setText(R.string.error_sync_first);
                }
            });
        }catch (Exception ex){
            pd.dismiss();
            Log.i("Ecxception error: ",ex.getMessage());
            DashboardFragment.error.setText(R.string.error_sync_first);
        }
    }

    public void recupAllCycle(String idCompte, Context context1, Application application){

        try {

            RetrofitClientInstance instance = new RetrofitClientInstance(context1);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<List<Cycles>> call = service.getActiveCycleRecoveredByCompte(idCompte);


            call.enqueue(new Callback<List<Cycles>>() {
                @Override
                public void onResponse(Call<List<Cycles>> call, Response<List<Cycles>> response) {
                    if (response.isSuccessful()){

                        List<Cycles> cycles = response.body();

                        Log.i("Liste cycles :", cycles.toString());
                        if (response.body().size() > 0){
                            for (Cycles cycles1:
                                    response.body()) {
                                cycleViewModel1.insert(cycles1);
                                recupAllCollectes(cycles1.getNumeroCycle(),context, application);
                            }
                            for (Cycles item :
                                    cycles) {
                                recupAllMise(item.getNumeroCycle(), context);
                            }
                        } else {
                            prefManager.setIsFirstRecup(false);
                            pd.dismiss();
                            DashboardFragment.error.setText(R.string.success_sync);
                        }

                    } else {
                        Log.i("error :", response.message());
                        pd.dismiss();
                        DashboardFragment.error.setText(R.string.error_sync_first);
                    }
                }

                @Override
                public void onFailure(Call<List<Cycles>> call, Throwable t) {
                    pd.dismiss();
                    DashboardFragment.error.setText(R.string.error_sync_first);
                }
            });
        }catch (Exception ex){
            Log.i("Ecxception error: ",ex.getMessage());
            pd.dismiss();
            DashboardFragment.error.setText(R.string.error_sync_first);
        }
    }

    public void recupAllCycleAdmin(String idCompte, String dateStart, String dateEnd, Context context1, Application application){

        try {

            RetrofitClientInstance instance = new RetrofitClientInstance(context1);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<List<Cycles>> call = service.getActiveCycleRecoveredByCompte(idCompte);


            call.enqueue(new Callback<List<Cycles>>() {
                @Override
                public void onResponse(Call<List<Cycles>> call, Response<List<Cycles>> response) {
                    if (response.isSuccessful()){

                        List<Cycles> cycles = response.body();

                        Log.i("Liste cycles :", cycles.toString());
                        if (response.body().size() > 0){
                            for (Cycles cycles1:
                                    response.body()) {
                                cycleViewModel1.insert(cycles1);
                                recupAllCollectes(cycles1.getNumeroCycle(),context, application);
                            }
                            for (Cycles item :
                                    cycles) {
                                recupAllMise(item.getNumeroCycle(), context);
                            }
                        } else {
                            prefManager.setIsFirstRecup(false);
                            pd.dismiss();
                            DashboardFragment.error.setText(R.string.success_sync);
                        }

                    } else {
                        Log.i("error :", response.message());
                        pd.dismiss();
                        DashboardFragment.error.setText(R.string.error_sync_first);
                    }
                }

                @Override
                public void onFailure(Call<List<Cycles>> call, Throwable t) {
                    pd.dismiss();
                    DashboardFragment.error.setText(R.string.error_sync_first);
                }
            });
        }catch (Exception ex){
            Log.i("Ecxception error: ",ex.getMessage());
            pd.dismiss();
            DashboardFragment.error.setText(R.string.error_sync_first);
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
                    if (response.body() != null){
                        Log.i("Liste mises :", response.body().toString());
                        miseViewModel1.insert(response.body());
                        prefManager.setIsFirstRecup(false);
                        DashboardFragment.error.setText(R.string.success_sync);
                        pd.dismiss();
                    } else {
                        prefManager.setIsFirstRecup(false);
                        DashboardFragment.error.setText(R.string.success_sync);
                        pd.dismiss();
                    }
                    Log.i("Success","ok");
                }

                @Override
                public void onFailure(Call<Mises> call, Throwable t) {
                    pd.dismiss();
                    DashboardFragment.error.setText(R.string.error_sync_first);
                }
            });
        }catch (Exception ex){
            pd.dismiss();
            DashboardFragment.error.setText(R.string.error_sync_first);
        }
    }

    public void  recupAllCollectes(String numeroCycle,Context context1, Application application){
        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(context1);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<List<Collectes>> call = service.getCollecteRecoveredByCycle(numeroCycle);


            call.enqueue(new Callback<List<Collectes>>() {
                @Override
                public void onResponse(Call<List<Collectes>> call, Response<List<Collectes>> response) {
                    if (response.isSuccessful()){

                        List<Collectes> collectes = response.body();
                        Log.i("List collectes", response.body().toString());
                        if (response.body().size() > 0){
                            Log.i("Liste collectes :", collectes.toString());
                            for (Collectes collectes1:
                                    response.body()) {
                                collecteViewModel1.insert(collectes1);
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


                            Log.i("Success :", response.message());
                        } else {
                            Log.i("List collectes", response.body().toString());
                            prefManager.setIsFirstRecup(false);
                            DashboardFragment.error.setText(R.string.success_sync);
                            pd.dismiss();
                        }

                    } else {
                        Log.i("error :", response.message());
                        pd.dismiss();
                        DashboardFragment.error.setText(R.string.error_sync_first);
                    }
                }

                @Override
                public void onFailure(Call<List<Collectes>> call, Throwable t) {
                    Log.i("onFailure error", t.getMessage());
                    pd.dismiss();
                    DashboardFragment.error.setText(R.string.error_sync_first);
                }
            });
        }catch (Exception ex){
            Log.i("Exception error", ex.getMessage());
            pd.dismiss();
            DashboardFragment.error.setText(R.string.error_sync_first);
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
                                compteViewModel1.insert(comptes1);

                                if (prefManager.getLogin().getRole().equals("Administrateur")){
                                    if (prefManager.getLogin().getDateDebutCycle() == null || prefManager.getLogin().getDateFinCycle() == null){
                                        recupAllCycle(comptes1.getIdCompte(),context,application);
                                    } else {
                                        recupAllCycleAdmin(comptes1.getIdCompte(), prefManager.getLogin().getDateDebutCycle(), prefManager.getLogin().getDateFinCycle(), context,application);
                                    }
                                } else {
                                    recupAllCycle(comptes1.getIdCompte(),context,application);
                                }

                            }
                        }else {
                            prefManager.setIsFirstRecup(false);
                            pd.dismiss();
                            DashboardFragment.error.setText(R.string.success_sync);
                        }

                    } else {

                        Log.i("error :", response.message());
                        pd.dismiss();
                        DashboardFragment.error.setText(R.string.error_sync_first);
                    }
                }

                @Override
                public void onFailure(Call<List<Comptes>> call, Throwable t) {
                    pd.dismiss();
                    DashboardFragment.error.setText(R.string.error_sync_first);
                }
            });
        }catch (Exception ex){
            Log.i("Ecxception error: ",ex.getMessage());
            pd.dismiss();
            DashboardFragment.error.setText(R.string.error_sync_first);
        }
    }


}