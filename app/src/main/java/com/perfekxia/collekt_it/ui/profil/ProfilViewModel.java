package com.perfekxia.collekt_it.ui.profil;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.widget.Button;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.perfekxia.collekt_it.model.Affectations;
import com.perfekxia.collekt_it.model.Clients;
import com.perfekxia.collekt_it.model.Login;
import com.perfekxia.collekt_it.model.TitulaireView;
import com.perfekxia.collekt_it.model.Utilisateurs;
import com.perfekxia.collekt_it.model.Zones;
import com.perfekxia.collekt_it.preference.PrefManager;
import com.perfekxia.collekt_it.repository.ClientRepository;
import com.perfekxia.collekt_it.repository.LoginRepository;
import com.perfekxia.collekt_it.repository.UtilisateurRepository;
import com.perfekxia.collekt_it.repository.ZoneRepository;

import java.util.List;

public class ProfilViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<Button> settings;

    Context context;
    String deviceID = Build.ID;
    private ZoneRepository zoneRepository;
    private LoginRepository loginRepository;
    private ClientRepository clientRepository;

    private LiveData<List<Utilisateurs>> mAllUtilisateur;
    private LiveData<List<Affectations>> mAllAffectation;
    private LiveData<List<TitulaireView>> titulaires;
    private Zones zones;
    private UtilisateurRepository utilisateurRepository;
    private LiveData<List<Utilisateurs>> mAllUpdatedUtilisateur;


    private LiveData<List<Clients>> clients;

    PrefManager prefManager;

    public void init(Context context, Application application, String idzone) {
        this.context = context;
        loginRepository = new LoginRepository(context);
        clientRepository = new ClientRepository(application,"A");
        zoneRepository = new ZoneRepository(context);
        zones = zoneRepository.getZone(idzone);
        prefManager = new PrefManager(context);
        utilisateurRepository = new UtilisateurRepository(application);
        mAllUpdatedUtilisateur = utilisateurRepository.getAllUpdatedUtilisateur();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<Clients>> getClient(String zone){
        return clientRepository.getAllClientZone(zone);
    }

    public LiveData<List<Utilisateurs>> getUtilisateur(){
        return mAllUpdatedUtilisateur;
    }

    public Zones getZone(){
        return zones;
    }

    public Login getLogins(){
        return prefManager.getLogin();
    }
}