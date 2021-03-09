package com.perfekxia.collekt_it.ui.listeClients;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.perfekxia.collekt_it.R;
import com.perfekxia.collekt_it.adapter.ClientAdapter;
import com.perfekxia.collekt_it.model.ClientView;
import com.perfekxia.collekt_it.model.Cycles;
import com.perfekxia.collekt_it.model.Produits;
import com.perfekxia.collekt_it.preference.PrefManager;
import com.perfekxia.collekt_it.ui.creerCycle.CreerCycleActivity;
import com.perfekxia.collekt_it.ui.effectuerCollecte.EffectuerCollecteActivity;
import com.perfekxia.collekt_it.viewModel.ClientViewModel;
import com.perfekxia.collekt_it.viewModel.ClientViewViewModel;
import com.perfekxia.collekt_it.viewModel.CycleViewModel;
import com.perfekxia.collekt_it.viewModel.ProduitViewModel;

import java.util.ArrayList;
import java.util.List;


public class ListeClientsActivity extends AppCompatActivity  implements
        AdapterView.OnItemSelectedListener{
    private ClientViewViewModel clientViewViewModel;
    RecyclerView recyclerView;
    private CycleViewModel cycleViewModel;
    private ClientViewViewModel clientViewViewModel1;
    private ProduitViewModel produitViewModel;
    TextView textError;
    ClientView clientView;
    TextView textView;
    Button button;
    Spinner spinner;
    CardView cltVisites;
    ClientAdapter adapter;

    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_clients);
        recyclerView = findViewById(R.id.listeClients);
        textView = findViewById(R.id.numeroDuCompte);
        button = findViewById(R.id.validerRechercheDuCompte);
        spinner = (Spinner) findViewById(R.id.spinnerProduit);

        textError = findViewById(R.id.errorText);

        prefManager = new PrefManager(this);

        initView();

        spinner.setOnItemSelectedListener(this);

        adapter = new ClientAdapter(this,getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        produitViewModel = new ProduitViewModel(getApplication());
        produitViewModel.getAllProduit().observe(this, new Observer<List<Produits>>() {
            @Override
            public void onChanged(List<Produits> produits) {
                if(produits.size()!=0){
                    ArrayAdapter aa = new ArrayAdapter(ListeClientsActivity.this,android.R.layout.simple_spinner_item,produits);
                    spinner.setAdapter(aa);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }
            }
        });

        if (prefManager.getLogin().getRole().equals("Administrateur")){
            cltVisites.setVisibility(View.GONE);
        }

    }

    private void initView(){
        cltVisites = findViewById(R.id.cltVisites);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Produits st = (Produits) spinner.getSelectedItem();
        clientViewViewModel = new ClientViewViewModel(getApplication(),"A",st.getIdProduit());
        clientViewViewModel.getAllClientViewProuit().observe(this, new Observer<List<ClientView>>() {
            @Override
            public void onChanged(@Nullable final List<ClientView> clientViews) {
                // Update the cached copy of the words in the adapter.
                adapter.setClientView(clientViews);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(textView.getText())){
                    textError.setText("Le champs est vide.Veuillez le remplir.");
                }
                else if(!TextUtils.isEmpty(textView.getText())){
                    clientViewViewModel1 = new ClientViewViewModel(getApplication(),textView.getText().toString(),st.getIdProduit());
                    clientViewViewModel1.getClientViewById().observe(ListeClientsActivity.this, new Observer<ClientView>() {
                        @Override
                        public void onChanged(ClientView clientView) {
                            if (clientView!=null){
                                cycleViewModel = new CycleViewModel(ListeClientsActivity.this,getApplication(),clientView.IdClient);
                                cycleViewModel.getCycleByAccount().observe(ListeClientsActivity.this,new Observer<Cycles>() {
                                    @Override
                                    public void onChanged(@Nullable Cycles c) {
                                        if (c != null){
                                            Log.d("Le cycle correspondant",c.toString());
                                            Intent activity2Intent = new Intent(getApplicationContext(), EffectuerCollecteActivity.class);
                                            activity2Intent.putExtra("EXTRA_CLIENTVIEW",clientView);
                                            activity2Intent.putExtra("EXTRA_NUMERO_CYCLE",c.getNumeroCycle());
                                            startActivity(activity2Intent);
                                        }else {
                                            LayoutInflater li = LayoutInflater.from(v.getContext());
//                                            View view = li.inflate(R.layout.pop_up_creer_cycle, null);
                                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                                            alertDialogBuilder.setTitle("Ce client ne possède pas de cycle");
                                            alertDialogBuilder.setMessage("Souhaitez-vous lui en créer un?");
                                            alertDialogBuilder
                                                    .setPositiveButton(R.string.pop_valider, new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            Intent activity2Intent = new Intent(getApplicationContext(), CreerCycleActivity.class);
                                                            activity2Intent.putExtra("EXTRA_CLIENTVIEW",clientView);
                                                            startActivity(activity2Intent);
                                                        }
                                                    })
                                                    .setNegativeButton(R.string.pop_cancel, new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                        }
                                                    });
                                            alertDialogBuilder.create();
                                            alertDialogBuilder.show();
                                        }
                                    }
                                });
                            }else{
                                textError.setText("Ce numéro ne correspond à aucun compte.");
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
