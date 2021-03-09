package com.perfekxia.collekt_it.ui.effectuerRetrait;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.perfekxia.collekt_it.R;
import com.perfekxia.collekt_it.adapter.CycleAdapter;

import com.perfekxia.collekt_it.preference.PrefManager;
import com.perfekxia.collekt_it.viewModel.CycleViewModel;



public class EffectuerRetraitActivity extends AppCompatActivity{
    RecyclerView recyclerView;
    private CycleViewModel cycleViewModel,cycleViewModel1;
    TextView textError;
    TextView textView;
    Button button;
    static CycleAdapter adapter;
    PrefManager prefManager;
    EffectuerRetraitViewModel effectuerRetraitViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effectuer_retrait);
        recyclerView = findViewById(R.id.listeCRetrait);
        textView = findViewById(R.id.numeroDuCompteRetrait);
        button = findViewById(R.id.validerRechercheDuCompteRetrait);
//        textError = findViewById(R.id.errorTextRetrait);
        adapter = new CycleAdapter(EffectuerRetraitActivity.this,getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(EffectuerRetraitActivity.this));
        String numeroCompte = getIntent().getStringExtra("EXTRA_COMPTES");
        effectuerRetraitViewModel = new EffectuerRetraitViewModel(getApplication());
        effectuerRetraitViewModel.init(this,getApplication(),numeroCompte);
//        cycleViewModel = new CycleViewModel(getApplication(),"A");
//        cycleViewModel.getmAllCycleEnCours().observe(this, new Observer<List<Cycles>>() {
//            @Override
//            public void onChanged(List<Cycles> cycles) {
//                adapter.setCycles(cycles);
//            }
//        });
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            if(!TextUtils.isEmpty(textView.getText())){
//                cycleViewModel1 = new CycleViewModel(getApplication(),textView.getText().toString());
//                cycleViewModel1.getCycleByAccount().observe(EffectuerRetraitActivity.this, new Observer<Cycles>() {
//                    @Override
//                    public void onChanged(Cycles cycles) {
//                        if (cycles!=null){
//                            prefManager = new PrefManager(view.getContext());
//                            LayoutInflater li = LayoutInflater.from(view.getContext());
//                            View view = li.inflate(R.layout.pop_up_retrait, null);
//                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
//                            TextView montantRetrait = view.findViewById(R.id.montantRetrait);
//                            alertDialogBuilder.setView(view)
//                                    .setPositiveButton(R.string.pop_valider, new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int id) {
//
//                                            if (!TextUtils.isEmpty(montantRetrait.getText())){
//                                                prefManager = new PrefManager(getApplicationContext());
//                                                Calendar c = Calendar.getInstance();
//                                                Date currentTime = c.getTime();
//                                                Log.i("Montant retrait", montantRetrait.getText().toString());
//                                                Retraits retraits = new Retraits();
//                                                retraits.setAnnee(currentTime.getYear());
//                                                retraits.setCompteEp("");
//                                                retraits.setDateRetrait(currentTime);
//                                                retraits.setIdClient(cycles.getIdClient());
//                                                retraits.setMoisRetrait(currentTime.getMonth());
//                                                retraits.setMontantRetrait(Integer.valueOf(montantRetrait.getText().toString()));
//                                                retraits.setTypeRetrait("");
//                                                retraits.setZoneT("");
//                                                retraits.setNouveau(true);
//                                                retraits.setRecupere(false);
//                                                retraits.setIdUtilisateur(prefManager.getLogin().getNomUtilisateur());
//                                                retraits.setRemboursement(false);
//                                                retraits.setNumero(cycles.getNumeroCycle());
//                                                saveRetraits(retraits,getApplicationContext());
//                                                cycles.setCloture(false);
//                                                updateCycle(cycles,getApplicationContext());
//                                            }
//                                        }
//                                    })
//                                    .setNegativeButton(R.string.pop_cancel, new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int id) {
//                                        }
//                                    });
//                            alertDialogBuilder.create();
//                            alertDialogBuilder.show();
//                        }else {
//                            textError.setText("Ce compte n'a pas de cycle en cours");
//                        }
//                    }
//                });
//            }else{
//                textError.setText("Veuillez remplir le champ");
//            }
//            }
//        });
//
//    }
//    public void saveRetraits(Retraits retraits, Context context){
//        RetrofitClientInstance instance = new RetrofitClientInstance(context);
//        GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
//        Call<Retraits> call = service.saveRetraits(retraits);
//
//        call.enqueue(new Callback<Retraits>() {
//            @Override
//            public void onResponse(Call<Retraits> call, Response<Retraits> response) {
//                if (response.isSuccessful()){
//                    Log.i("save Retraits:", response.message());
//                } else {
//                    Log.i("error Retraits:", response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Retraits> call, Throwable t) {
////                Log.i("Error :", t.getMessage());
//            }
//        });
//    }
//
//    public void updateCycle(Cycles cycles,Context context){
//        RetrofitClientInstance instance = new RetrofitClientInstance(context);
//        GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
//        Call<Cycles> call = service.updateCycle(cycles.getNumeroCycle(),cycles);
//
//        call.enqueue(new Callback<Cycles>() {
//            @Override
//            public void onResponse(Call<Cycles> call, Response<Cycles> response) {
//                if (response.isSuccessful()){
//                    Log.i("update Cycles:", response.message());
//                } else {
//                    Log.i("error Cycles:", response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Cycles> call, Throwable t) {
////                Log.i("Error :", t.getMessage());
//            }
//        });
    }
}
