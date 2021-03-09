package com.perfekxia.collekt_it.ui.profil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;


import com.perfekxia.collekt_it.R;
import com.perfekxia.collekt_it.model.Clients;
import com.perfekxia.collekt_it.model.Collectes;
import com.perfekxia.collekt_it.model.Comptes;
import com.perfekxia.collekt_it.model.Login;
import com.perfekxia.collekt_it.model.LoginRequest;
import com.perfekxia.collekt_it.model.Utilisateurs;
import com.perfekxia.collekt_it.model.Zones;
import com.perfekxia.collekt_it.preference.PrefManager;
import com.perfekxia.collekt_it.repository.ZoneRepository;
import com.perfekxia.collekt_it.ui.changePassword.PasswordActivity;
import com.perfekxia.collekt_it.ui.dashboard.DashboardViewModel;
import com.perfekxia.collekt_it.ui.login.LoginActivity;
import com.perfekxia.collekt_it.viewModel.CollecteViewModel;
import com.perfekxia.collekt_it.viewModel.CompteViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ProfilFragment extends Fragment implements View.OnClickListener{

    private ProfilViewModel viewModel;
    private CompteViewModel compteViewModel;
    ImageView settings;
    TextView textViewName, textViewNbrClient, textViewIdentifiant, textViewZone, textViewUsername;
    LinearLayout deconnexion;

    PrefManager prefManager;
    private ZoneRepository zoneRepository;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profil, container, false);

        prefManager = new PrefManager(getContext());

        viewModel =new ViewModelProvider(this).get(ProfilViewModel.class);
        compteViewModel = new CompteViewModel(getActivity().getApplication());
        viewModel.init(v.getContext(), getActivity().getApplication(),prefManager.getLogin().getIdZone());

        initView(v);
        initListner();

//        zoneRepository = new ZoneRepository(v.getContext());
//        Zones zones = zoneRepository.getZone(prefManager.getLogin().getIdZone());

//        Log.i("Zone", zones.toString());

//        viewModel.getClient(prefManager.getLogin().getIdZone()).observe(getViewLifecycleOwner(), new Observer<List<Clients>>() {
//            @Override
//            public void onChanged(List<Clients> clients) {
//                textViewNbrClient.setText(String.valueOf(clients.size()));
//            }
//        });

        compteViewModel.getAllCompte().observe(getViewLifecycleOwner(), new Observer<List<Comptes>>() {
            @Override
            public void onChanged(List<Comptes> comptes) {
                textViewNbrClient.setText(String.valueOf(comptes.size()));
            }
        });

        viewModel.getUtilisateur().observe(getViewLifecycleOwner(), new Observer<List<Utilisateurs>>() {
            @Override
            public void onChanged(List<Utilisateurs> utilisateurs) {
                Log.i("update user", utilisateurs.toString());
            }
        });

        if (viewModel.getLogins()!= null){
            Login login = prefManager.getLogin();
            Zones zones = prefManager.getZoneAv();
            textViewName.setText(login.getNomComplet());
            textViewIdentifiant.setText(login.getIdTitulaire());
            textViewUsername.setText(login.getNomUtilisateur());
//            Log.i("zone", zones.toString());
//            Zones zones = viewModel.getZone();
            textViewZone.setText(login.getNomZone());
        }

        return v;
    }

    private  void initView(View v){
        settings = v.findViewById(R.id.settings);
        textViewName = v.findViewById(R.id.tv_name);
        textViewNbrClient = v.findViewById(R.id.nbrclient);
        textViewIdentifiant = v.findViewById(R.id.txtIdentifiant);
        textViewZone = v.findViewById(R.id.txtZone);
        textViewUsername = v.findViewById(R.id.txtUsername);
        deconnexion = v.findViewById(R.id.deconnexion);
    }

    private  void initListner(){
        settings.setOnClickListener(this);
        deconnexion.setOnClickListener(this);

    }

    private void launchPasswordPage(){
        Intent activity2Intent = new Intent(getActivity().getApplicationContext(), PasswordActivity.class);
        startActivity(activity2Intent);
    }

    private void disconect(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.text_title_disconnect);
        builder.setPositiveButton(R.string.text_yes_disconnect, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                launchLoginScreen();
            }
        }).setNegativeButton(R.string.text_no_disconnect, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        builder.create();
        builder.show();
    }

    private void launchLoginScreen() {
        startActivity(new Intent(getContext(), LoginActivity.class));
        getActivity().finish();
        prefManager.setConnexion(false);
        prefManager.deleteLogin();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.settings:
                launchPasswordPage();
                break;
            case R.id.deconnexion:
                disconect();
                break;
        }
    }
}