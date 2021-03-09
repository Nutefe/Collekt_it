package com.perfekxia.collekt_it.ui.listeCycles;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.perfekxia.collekt_it.R;
import com.perfekxia.collekt_it.adapter.CycleAdapter;
import com.perfekxia.collekt_it.api_manager.GetDataService;
import com.perfekxia.collekt_it.api_manager.RetrofitClientInstance;
import com.perfekxia.collekt_it.model.Collectes;
import com.perfekxia.collekt_it.model.Cycles;
import com.perfekxia.collekt_it.model.Mises;
import com.perfekxia.collekt_it.model.Retraits;
import com.perfekxia.collekt_it.preference.PrefManager;
import com.perfekxia.collekt_it.viewModel.CollecteViewModel;
import com.perfekxia.collekt_it.viewModel.CycleViewModel;
import com.perfekxia.collekt_it.viewModel.MiseViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListeCyclesActivity extends AppCompatActivity implements View.OnClickListener{

    RecyclerView recyclerView;
    private CycleViewModel cycleViewModel;
    private CollecteViewModel collecteViewModel;
    private MiseViewModel miseViewModel;
    TextView textError;
    TextView textView;
    Button button;
    CycleAdapter adapter;
    PrefManager prefManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_cycle);

        prefManager = new PrefManager(this);

        initView();
        initListner();

        String idCompte = getIntent().getStringExtra("IDCOMPTE");

        Log.i("idcompte", idCompte);

        adapter = new CycleAdapter(ListeCyclesActivity.this,getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListeCyclesActivity.this));
        cycleViewModel = new CycleViewModel(this, getApplication(),idCompte);

        loadView();

        cycleViewModel.getmAllCycleCompte().observe(this, new Observer<List<Cycles>>() {
            @Override
            public void onChanged(List<Cycles> cycles) {
                adapter.setCycles(cycles);
            }
        });

    }

    private void initView(){
        recyclerView = findViewById(R.id.listeCRetrait);
        textView = findViewById(R.id.numeroDuCompteRetrait);
        button = findViewById(R.id.btnValideCycle);
    }

    private void initListner(){
        button.setOnClickListener(this);
    }

    private void loadView(){
        if (prefManager.getLogin().getRole().equals("Administrateur")){
            cycleViewModel.getmAllCycleCompte().observe(this, new Observer<List<Cycles>>() {
                @Override
                public void onChanged(List<Cycles> cycles) {
                    Log.i("cycles valide", cycles.toString());

                    if (cycles.size() > 0){
                        button.setVisibility(View.GONE);
                        button.setEnabled(true);
                        button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue)));
                    } else {
                        button.setVisibility(View.GONE);
                        button.setEnabled(false);
                        button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorBorder)));
                    }
                }
            });

        } else {
            button.setVisibility(View.VISIBLE);
            button.setVisibility(View.GONE);
        }

    }

    private void  updateCycles(){
        cycleViewModel.getmAllCycleValide().observe(ListeCyclesActivity.this, new Observer<List<Cycles>>() {
            @Override
            public void onChanged(List<Cycles> cycles) {
                if (cycles.size() > 0){
                    Log.i("update cycle nouveau", cycles.toString());
                    for (Cycles cycles1 :
                            cycles) {
                        cycles1.setNouveau(false);
                        cycleViewModel.update(cycles1);
                        collecteViewModel = new CollecteViewModel(getApplication(), cycles1.getNumeroCycle());
                        miseViewModel = new MiseViewModel(getApplication(), cycles1.getNumeroCycle());

                        collecteViewModel.getmAllCollecteByCycle().observe(ListeCyclesActivity.this, new Observer<List<Collectes>>() {
                            @Override
                            public void onChanged(List<Collectes> collectes) {
                                if (collectes.size() > 0){
                                    for (Collectes item :
                                            collectes) {
                                        item.setNouveau(false);
                                        collecteViewModel.update(item);
                                    }
                                }
                            }
                        });

                        miseViewModel.getAllNewMiseByCyle().observe(ListeCyclesActivity.this, new Observer<List<Mises>>() {
                            @Override
                            public void onChanged(List<Mises> mises) {
                                if (mises.size() > 0){
                                    for (Mises item :
                                            mises) {
                                        item.setNouveau(false);
                                        miseViewModel.update(item);
                                    }
                                }
                            }
                        });

                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnValideCycle:
//                updateCycles();
                break;
        }
    }
}
