package com.perfekxia.collekt_it.ui.listescomptespourretraits;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.perfekxia.collekt_it.R;
import com.perfekxia.collekt_it.adapter.CompteAdapter;
import com.perfekxia.collekt_it.model.Comptes;
import com.perfekxia.collekt_it.model.Produits;
import com.perfekxia.collekt_it.model.Zones;
import com.perfekxia.collekt_it.ui.effectuerRetrait.EffectuerRetraitActivity;


import java.util.List;


public class ListeComptesRetraitsActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener,SwipeRefreshLayout.OnRefreshListener{
    RecyclerView recyclerView;
    TextView textError;
    TextView textView;
    static Button button;
    static List<Comptes> comptesList;
    static CompteAdapter adapter;
    ListeComptesRetraitsViewModel listeComptesRetraitsViewModel;
    static Spinner spinnerProduit;
    Produits pdt;
    SwipeRefreshLayout container;
    Zones zne;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comptes_retrait);
        spinnerProduit = (Spinner) findViewById(R.id.mySpinnerProduit);
        spinnerProduit.setOnItemSelectedListener(this);
        recyclerView = findViewById(R.id.listeComptesRetrait);
        container = findViewById(R.id.containerRetrait);
        container.setOnRefreshListener(this::onRefresh);
        textView = findViewById(R.id.numeroDuCompteRetrait);
        button = findViewById(R.id.validerRechercheDuCompteRetrait);
        textError = findViewById(R.id.errorTextCompteRetrait);
        adapter = new CompteAdapter(ListeComptesRetraitsActivity.this,getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListeComptesRetraitsActivity.this));
        listeComptesRetraitsViewModel = new ListeComptesRetraitsViewModel(getApplication());
        listeComptesRetraitsViewModel.init(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(textView.getText())){
                    for (int i=0;i<comptesList.size();i++){
                        Log.i("Textview", textView.getText().toString());
                        if (textView.getText().toString().equals(comptesList.get(i).getIdCompte())){
                            Log.i("Dans", "le if");
                            Intent activity2Intent = new Intent(view.getContext(), EffectuerRetraitActivity.class);
                            activity2Intent.putExtra("EXTRA_COMPTES",comptesList.get(i).getIdCompte());
                            startActivity(activity2Intent);
                        }
                    }

                }else{
                    textError.setText("Le champ est vide veuillez le remplir");
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        pdt = (Produits) spinnerProduit.getSelectedItem();
        listeComptesRetraitsViewModel.loadComptes(this,pdt.getIdProduit());


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listeComptesRetraitsViewModel.init(ListeComptesRetraitsActivity.this);
                container.setRefreshing(false);
            }
        }, 10);
    }
}
