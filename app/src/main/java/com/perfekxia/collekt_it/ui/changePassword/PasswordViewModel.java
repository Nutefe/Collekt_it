package com.perfekxia.collekt_it.ui.changePassword;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Button;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.perfekxia.collekt_it.model.Affectations;
import com.perfekxia.collekt_it.model.Login;
import com.perfekxia.collekt_it.model.LoginRequest;
import com.perfekxia.collekt_it.model.TitulaireView;
import com.perfekxia.collekt_it.model.UserRequest;
import com.perfekxia.collekt_it.model.Utilisateurs;
import com.perfekxia.collekt_it.model.Zones;
import com.perfekxia.collekt_it.preference.PrefManager;
import com.perfekxia.collekt_it.repository.AffectationRepository;
import com.perfekxia.collekt_it.repository.LoginRepository;
import com.perfekxia.collekt_it.repository.TitulaireViewRepository;
import com.perfekxia.collekt_it.repository.UtilisateurRepository;
import com.perfekxia.collekt_it.repository.ZoneRepository;

import java.util.List;

public class PasswordViewModel extends ViewModel {

    Context context;
    String deviceID = Build.ID;
    private UtilisateurRepository utilisateurRepository;
    private AffectationRepository affectationRepository;
    private TitulaireViewRepository titulaireViewRepository;
    private ZoneRepository zoneRepository;
    private LoginRepository loginRepository;

    PrefManager prefManager;

    public void init(Context context) {
        this.context = context;
        loginRepository = new LoginRepository(context);
        utilisateurRepository = new UtilisateurRepository(context);
        prefManager = new PrefManager(context);
    }

    public Login login(LoginRequest request){
        return  loginRepository.selectLogin(request);
    }

    public void updateLoginPass(String passnew, String passanc){
        Login login = prefManager.getLogin();
        if (login.getMotDePasse().equals(passanc)){
            if (!passnew.isEmpty()){
                login.setMotDePasse(passnew);
                UserRequest request = new UserRequest();
                request.setMotDePasse(passnew);
                request.setNomUtilisateur(login.getNomUtilisateur());
                utilisateurRepository.updatePassword(request);
            }

            Log.i("Login", login.toString());
            loginRepository.updateLogin(login);

            prefManager.setLogin(login);
        }
    }

    public void updateLoginPin(String passanc, String pin){
        Login login = prefManager.getLogin();
        if (login.getMotDePasse().equals(passanc)){

            if (!pin.isEmpty()){
                login.setPin(Integer.parseInt(pin));
                UserRequest request = new UserRequest();
                request.setPin(Integer.parseInt(pin));
                request.setNomUtilisateur(login.getNomUtilisateur());
                utilisateurRepository.updatePin(request);
            }

            Log.i("Login", login.toString());
            loginRepository.updateLogin(login);

            prefManager.setLogin(login);
        }
    }

    public void updateUtilisateur(String passnew, String passanc, String pin){
        Login login = prefManager.getLogin();
        if (login.getMotDePasse().equals(passanc)){
            if (!passnew.isEmpty())
                login.setMotDePasse(passnew);

            if (!pin.isEmpty())
                login.setPin(Integer.parseInt(pin));

            Log.i("Login", login.toString());
            loginRepository.updateLogin(login);

            prefManager.setLogin(login);
        }
    }


}
