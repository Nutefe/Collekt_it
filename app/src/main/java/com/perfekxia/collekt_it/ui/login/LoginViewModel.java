package com.perfekxia.collekt_it.ui.login;

import android.content.Context;
import android.os.Build;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.perfekxia.collekt_it.model.Affectations;
import com.perfekxia.collekt_it.model.Login;
import com.perfekxia.collekt_it.model.LoginRequest;
import com.perfekxia.collekt_it.model.TitulaireView;
import com.perfekxia.collekt_it.model.Utilisateurs;
import com.perfekxia.collekt_it.model.Zones;
import com.perfekxia.collekt_it.preference.PrefManager;
import com.perfekxia.collekt_it.repository.AffectationRepository;
import com.perfekxia.collekt_it.repository.LoginRepository;
import com.perfekxia.collekt_it.repository.TitulaireViewRepository;
import com.perfekxia.collekt_it.repository.UtilisateurRepository;
import com.perfekxia.collekt_it.repository.ZoneRepository;

import java.util.List;

public class LoginViewModel extends ViewModel {

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
        loginRepository = new LoginRepository(context);
        prefManager = new PrefManager(context);
    }

    public Login login(LoginRequest request){
        return  loginRepository.selectLogin(request);
    }

}
