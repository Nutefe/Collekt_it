package com.perfekxia.collekt_it.ui.confirmerCollecte;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import com.perfekxia.collekt_it.R;
import com.perfekxia.collekt_it.helper.GpsCoordinates;
import com.perfekxia.collekt_it.model.ClientView;
import com.perfekxia.collekt_it.model.Clients;
import com.perfekxia.collekt_it.model.Collectes;
import com.perfekxia.collekt_it.model.Cycles;
import com.perfekxia.collekt_it.model.Localisations;
import com.perfekxia.collekt_it.model.Mises;
import com.perfekxia.collekt_it.preference.PrefManager;
import com.perfekxia.collekt_it.ui.imprimer.ImprimerActivity;
import com.perfekxia.collekt_it.viewModel.ClientViewModel;
import com.perfekxia.collekt_it.viewModel.CollecteViewModel;
import com.perfekxia.collekt_it.viewModel.CycleViewModel;
import com.perfekxia.collekt_it.viewModel.LocalisationsViewModel;
import com.perfekxia.collekt_it.viewModel.MiseViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class ConfirmerCollecteActivity extends AppCompatActivity implements LocationListener {
    int min = 1;
    int max = 10000;
    private ConfirmerCollecteViewModel confirmerCollecteViewModel;
    private CycleViewModel cycleViewModel;
    private MiseViewModel miseViewModel;
    private MiseViewModel miseViewModel1;
    private CollecteViewModel collecteViewModel;
    private CollecteViewModel collecteViewModel1;
    private LocalisationsViewModel localisationsViewModel;
    private ClientViewModel clientViewModel;
    private String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    Mises mises = new Mises();
    Cycles cy = new Cycles();
    Calendar c = Calendar.getInstance();
    Date d = c.getTime();
    Date dateDebut, dateFin;
    Location location;
    Double longitude,latitude;
    GpsCoordinates gpsCoordinates;
    ClientView clientView;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
//        getSupportActionBar().hide(); // hide the title bar
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_confirmer_collecte);
        Button validation = findViewById(R.id.validerConfirmation);
        final TextView textView = findViewById(R.id.montantConfirmationCollecte);
        final TextView textView1 = findViewById(R.id.numeroDuCompte);
        final TextView textView2 = findViewById(R.id.nomDuClient);


        String montant = getIntent().getStringExtra("EXTRA_MONTANT");
        String montant_Mise = getIntent().getStringExtra("EXTRA_MONTANT_MISE");
        clientView = (ClientView) getIntent().getSerializableExtra("EXTRA_CLIENTVIEW1");
       dateDebut = (Date) this.getIntent().getExtras().get("EXTRA_DATE_DEBUT");
       dateFin = (Date) this.getIntent().getExtras().get("EXTRA_DATE_FIN");
       gpsCoordinates = new GpsCoordinates(this);
        prefManager = new PrefManager(this);

       cycleViewModel = new CycleViewModel(this, getApplication(),clientView.IdClient);
       confirmerCollecteViewModel = new ConfirmerCollecteViewModel(montant,clientView);
        confirmerCollecteViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        confirmerCollecteViewModel.getClientView().observe(this, new Observer<ClientView>() {
            @Override
            public void onChanged(@Nullable ClientView c) {
                String nomPrenomClt = c.Nom.concat(" ").concat(c.Prenom);
                textView1.setText(c.IdClient);
                textView2.setText(nomPrenomClt);
            }
        });

        validation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                location = gpsCoordinates.getCoordinates();
                LayoutInflater li = LayoutInflater.from(v.getContext());
                View view = li.inflate(R.layout.pop_up_confirmer_pin, null);
                TextView textView3 = view.findViewById(R.id.saisirPinCollecte);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                alertDialogBuilder.setView(view)
                        .setPositiveButton(R.string.pop_valider, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                if (!TextUtils.isEmpty(textView3.getText()) && Integer.parseInt(textView3.getText().toString()) == prefManager.getLogin().getPin() ){
                                    String pwd = UUID.randomUUID().toString().substring(0,5);
                                    int random_int = (int)(Math.random() * (max - min + 1) + min);
                                    String gId = prefManager.getLogin().getIdTitulaire()+pwd;
                                    if(dateDebut!=null && dateFin !=null && montant_Mise!=null){

                                        if (location!=null){
                                           clientViewModel = new ClientViewModel(getApplication(),clientView.OldID);
                                           clientViewModel.getClient().observe(ConfirmerCollecteActivity.this, new Observer<Clients>() {
                                               @Override
                                               public void onChanged(Clients clients) {
                                                   clients.setLongitude(location.getLongitude());
                                                   clients.setLatitude(location.getLatitude());
                                                  clientViewModel.update(clients);
                                               }
                                           });
                                        }

                                        Cycles cycles = new Cycles();
                                        cycles.setNumeroCycle(gId);
                                        cycles.setCloture(true);
                                        cycles.setDateDebut(dateDebut);
                                        cycles.setDateFin(dateFin);
                                        cycles.setIdClient(clientView.IdClient);
                                        cycles.setDuree(31);
                                        cycles.setMois(dateDebut.getMonth());
                                        cycles.setTypeCycle("");
                                        cycles.setNouveau(true);
                                        cycles.setRecupere(false);

                                        Long idCycle =  cycleViewModel.insert(cycles);
                                        Log.d("La valeur de l'id: ", idCycle.toString());
//                                        mises.setNumeroMise(random_int);
                                        mises.setAnnee(dateDebut.getYear());
                                        mises.setClient(clientView.IdClient);
                                        mises.setMois(dateDebut.getMonth());
                                        mises.setDateMise(dateDebut);
                                        mises.setMontantMise(Integer.parseInt(montant_Mise));
                                        mises.setNouveau(true);
                                        mises.setTitulaire(prefManager.getLogin().getIdTitulaire());
                                        mises.setZoneT(prefManager.getLogin().getIdZone());
                                        mises.setUtilisateur(prefManager.getLogin().getNomUtilisateur());
                                        mises.setRecupere(false);
                                        mises.setCycle(gId);
                                        miseViewModel = new MiseViewModel(getApplication(),gId);
                                        miseViewModel.insert(mises);

                                        Collectes collectes = new Collectes();
//                                        collectes.setNumeroCollecte(random_int);
                                        collectes.setCarnet("");
                                        collectes.setDateCollecte(d);
                                        collectes.setIdClient(clientView.IdClient);
                                        collectes.setIdCycle(gId);
                                        collectes.setIdTitulaire(prefManager.getLogin().getIdTitulaire());
                                        collectes.setMoisCollecte(d.getMonth());
                                        collectes.setUtilisateur(prefManager.getLogin().getNomUtilisateur());
                                        collectes.setZoneT(prefManager.getLogin().getIdZone());
                                        collectes.setManquant(false);
                                        collectes.setNouveau(true);
                                        collectes.setRecupere(false);
                                        collectes.setMontantCollecte(Integer.parseInt(montant));
                                        Log.i("La collecte à créer",collectes.toString());
                                        collecteViewModel = new CollecteViewModel(getApplication(),gId);
                                        collecteViewModel.insert(collectes);
                                        collecteViewModel.getSumCollecte().observe(ConfirmerCollecteActivity.this,new Observer<Integer>() {
                                            @Override
                                            public void onChanged(@Nullable Integer sommeCollecte) {
                                                if(cycles.getDateFin().after(d) || sommeCollecte == (Integer.parseInt(montant_Mise)*31) ){
                                                    cy.setCloture(false);
                                                    cycleViewModel.update(cycles);
                                                }
                                            }
                                        });
                                        Intent activity2Intent = new Intent(getApplicationContext(), ImprimerActivity.class);
                                        startActivity(activity2Intent);
                                        finishAffinity();
                                    }else if(dateDebut==null && dateFin == null && montant_Mise ==null) {
                                        CycleViewModel cycleViewModel1 = new CycleViewModel(ConfirmerCollecteActivity.this, getApplication(),clientView.IdClient);
                                        cycleViewModel1.getCycleByAccount().observe(ConfirmerCollecteActivity.this,new Observer<Cycles>() {
                                            @Override
                                            public void onChanged(@Nullable Cycles c) {
                                                if(c!=null){
                                                    Log.d("La valeur du cycle: ", c.toString());
                                                    Log.i("La  longitude: ", String.valueOf(location.getLongitude()));
                                                    Log.i("La  latitude: ", String.valueOf(location.getLatitude()));

                                                    Localisations localisations = new Localisations();

                                                    if (location!=null){
                                                        clientViewModel = new ClientViewModel(getApplication(),clientView.OldID);
                                                        clientViewModel.getClient().observe(ConfirmerCollecteActivity.this, new Observer<Clients>() {
                                                            @Override
                                                            public void onChanged(Clients clients) {
                                                                clients.setLongitude(location.getLongitude());
                                                                clients.setLatitude(location.getLatitude());
                                                                clientViewModel.update(clients);
                                                            }
                                                        });
                                                    }

                                                    Collectes collectes = new Collectes();
                                                    collectes.setCarnet("");
                                                    collectes.setDateCollecte(d);
                                                    collectes.setIdClient(clientView.IdClient);
                                                    collectes.setIdCycle(c.getNumeroCycle());
                                                    collectes.setIdTitulaire(prefManager.getLogin().getIdTitulaire());
                                                    collectes.setMoisCollecte(d.getMonth());
                                                    collectes.setUtilisateur(prefManager.getLogin().getNomUtilisateur());
                                                    collectes.setZoneT(prefManager.getLogin().getIdZone());
                                                    collectes.setManquant(false);
                                                    collectes.setNouveau(true);
                                                    collectes.setRecupere(false);
                                                    collectes.setMontantCollecte(Integer.parseInt(montant));
                                                    collecteViewModel = new CollecteViewModel(getApplication(),c.getNumeroCycle());
                                                    collecteViewModel.insert(collectes);
                                                    miseViewModel1 =  new MiseViewModel(getApplication(),c.getNumeroCycle());
                                                    miseViewModel1.getMiseByCycle().observe(ConfirmerCollecteActivity.this, new Observer<Mises>() {
                                                        @Override
                                                        public void onChanged(Mises mises) {
                                                            collecteViewModel1 = new CollecteViewModel(getApplication(),c.getNumeroCycle());
                                                            collecteViewModel1.getSumCollecte().observe(ConfirmerCollecteActivity.this, new Observer<Integer>() {
                                                                @Override
                                                                public void onChanged(Integer integer) {
                                                                    if(d.after(c.getDateFin()) || integer == (mises.getMontantMise()*31) ){
                                                                        c.setCloture(false);
                                                                        cycleViewModel1.update(c);
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    });
                                                    Intent activity2Intent = new Intent(getApplicationContext(), ImprimerActivity.class);
                                                    startActivity(activity2Intent);
                                                    finishAffinity();
                                                }

                                            }
                                        });
                                    }
                                }
                            }
                        })
                        .setNegativeButton(R.string.pop_cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                alertDialogBuilder.create();
                alertDialogBuilder.show();
            }
        });

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//       if (requestCode==1){
//           if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//               if (location != null){
//                   Localisations localisations = new Localisations();
//
//                   localisations.setIdCompte(clientView.IdClient);
//                   localisations.setLatitude(location.getLatitude());
//                   localisations.setLongitude(location.getLongitude());
//                   localisationsViewModel = new LocalisationsViewModel(getApplication());
//                   localisationsViewModel.insert(localisations);
//               }
//
//           }
//       }
//    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();

    }
}
