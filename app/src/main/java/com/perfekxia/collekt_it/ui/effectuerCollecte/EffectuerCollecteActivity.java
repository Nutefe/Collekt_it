package com.perfekxia.collekt_it.ui.effectuerCollecte;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.perfekxia.collekt_it.R;
import com.perfekxia.collekt_it.model.ClientView;
import com.perfekxia.collekt_it.model.Mises;
import com.perfekxia.collekt_it.ui.confirmerCollecte.ConfirmerCollecteActivity;
import com.perfekxia.collekt_it.ui.creerCycle.CreerCycleActivity;
import com.perfekxia.collekt_it.viewModel.CollecteViewModel;
import com.perfekxia.collekt_it.viewModel.CycleViewModel;
import com.perfekxia.collekt_it.viewModel.MiseViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EffectuerCollecteActivity extends AppCompatActivity {
    private EffectuerCollecteViewModel effectuerCollecteViewModel;
    private CycleViewModel cycleViewModel;
    private MiseViewModel miseViewModel;
    private CollecteViewModel collecteViewModel;
    private EditText montantCollecte;
    private String numeroCycle;
    Integer montantMise =0;
    Integer montantSommeCollecte =0;
    Integer montantRestant = 0;
    ClientView clientView;
    String dateDebut,dateFin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
//        getSupportActionBar().hide(); // hide the title// bar
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_effectuer_collecte);
        final TextView textView = findViewById(R.id.cltAccount);
        final TextView textViewMise = findViewById(R.id.sommeMise);
        final TextView textViewCollecte = findViewById(R.id.sommeCollecte);
        final TextView textViewRestante = findViewById(R.id.sommeRestante);
        final TextView textError = findViewById(R.id.errorText);
        final TextView dateD = findViewById(R.id.dateDCycle);
        final TextView dateF = findViewById(R.id.dateFCycle);
        final Button button = findViewById(R.id.validerCollecte);
        montantCollecte = findViewById(R.id.montantEffectuerCollecte);
        clientView = (ClientView) getIntent().getSerializableExtra("EXTRA_CLIENTVIEW");
         numeroCycle = getIntent().getStringExtra("EXTRA_NUMERO_CYCLE");
         dateDebut = getIntent().getStringExtra("EXTRA_DATE_DEBUT");
         dateFin = getIntent().getStringExtra("EXTRA_DATE_FIN");

         Log.i("num cycle", String.valueOf(numeroCycle));

        miseViewModel = new MiseViewModel(getApplication(),numeroCycle);
        collecteViewModel = new CollecteViewModel(getApplication(),numeroCycle);
        String infoAccount = clientView.IdClient.concat("°").concat(clientView.Nom).concat(" ").concat(clientView.Prenom);

            miseViewModel.getMiseByCycle().observe(EffectuerCollecteActivity.this,new Observer<Mises>() {
                @Override
                public void onChanged(@Nullable Mises m) {
                    Log.d("La valeur de la mise: ", m.getMontantMise().toString());
                    montantMise = m.MontantMise;
                    collecteViewModel.getSumCollecte().observe(EffectuerCollecteActivity.this,new Observer<Integer>() {
                        @Override
                        public void onChanged(@Nullable Integer i) {
                           if(i!=null){
                               if (montantSommeCollecte != null && montantRestant != null){
                                   montantSommeCollecte= i;
                                   montantRestant = (montantMise*31) - montantSommeCollecte;
                                   Log.d("montant somme: ", montantSommeCollecte.toString());
                                   Log.d("somme restante: ", montantRestant.toString());
                               }
                           }
                            effectuerCollecteViewModel = new EffectuerCollecteViewModel(infoAccount,montantMise,montantSommeCollecte,montantRestant,dateDebut,dateFin);
                           effectuerCollecteViewModel.getTextDateF().observe(EffectuerCollecteActivity.this, new Observer<String>() {
                               @Override
                               public void onChanged(String s) {
                                   dateF.setText(s);
                               }
                           });
                            effectuerCollecteViewModel.getTextDateD().observe(EffectuerCollecteActivity.this, new Observer<String>() {
                                @Override
                                public void onChanged(String s) {
                                    dateD.setText(s);
                                }
                            });
                            effectuerCollecteViewModel.getText().observe(EffectuerCollecteActivity.this, new Observer<String>() {
                                @Override
                                public void onChanged(@Nullable String s) {
                                    textView.setText(s);
                                    Log.i("Le numero du cycle",String.valueOf(numeroCycle));
                                }
                            });

                            effectuerCollecteViewModel.getTextMise().observe(EffectuerCollecteActivity.this, new Observer<Integer>() {
                                @Override
                                public void onChanged(@Nullable Integer s) {
                                    textViewMise.setText(String.valueOf(s));

                                }
                            });

                            effectuerCollecteViewModel.getTextSommeCollecte().observe(EffectuerCollecteActivity.this, new Observer<Integer>() {
                                @Override
                                public void onChanged(@Nullable Integer s) {
                                    textViewCollecte.setText(String.valueOf(s));

                                }
                            });

                            effectuerCollecteViewModel.getTextSommeRestante().observe(EffectuerCollecteActivity.this, new Observer<Integer>() {
                                @Override
                                public void onChanged(@Nullable Integer s) {
                                    textViewRestante.setText(String.valueOf(s));

                                }
                            });
                        }
                    });
                }
            });



        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                    if (TextUtils.isEmpty(montantCollecte.getText())) {
                        textError.setText("Le champ de collecte est vide.Veuillez le remplir.");
                    } else {
                        textError.setText("");
                        if (Integer.parseInt(montantCollecte.getText().toString()) % montantMise == 0){
                            if(Integer.parseInt(montantCollecte.getText().toString()) > (montantMise*31)){
                                textError.setText("Le montant de collecte est supérieur à la somme des collectes à percevoir");
                            }
                            else {
                                if (Integer.parseInt(montantCollecte.getText().toString()) > montantRestant){
                                    textError.setText("Le montant de collecte est supérieur au montant restant à percevoir");
                                }else{
                                    if (Integer.parseInt(montantCollecte.getText().toString()) == (montantMise*31) || Integer.parseInt(montantCollecte.getText().toString())+montantSommeCollecte == (montantMise*31)){

                                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getContext());
                                        builder.setTitle("Le montant saisi va cloturer le cycle");
                                        builder.setMessage("Souhaitez-vous continuer?");
                                        builder.setPositiveButton(R.string.pop_valider, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent activity2Intent = new Intent(v.getContext(), ConfirmerCollecteActivity.class);
                                                activity2Intent.putExtra("EXTRA_CLIENTVIEW1", clientView);
                                                activity2Intent.putExtra("EXTRA_MONTANT", montantCollecte.getText().toString());
                                                startActivity(activity2Intent);
                                            }
                                        }).setNegativeButton(R.string.pop_cancel, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        });
                                        builder.create();
                                        builder.show();

//                                        LayoutInflater li = LayoutInflater.from(v.getContext());
//                                        View view = li.inflate(R.layout.pop_up_fin_cycle, null);
//                                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
//                                        alertDialogBuilder.setView(view)
//                                                .setPositiveButton(R.string.pop_valider, new DialogInterface.OnClickListener() {
//                                                    @Override
//                                                    public void onClick(DialogInterface dialog, int id) {
//                                                        Intent activity2Intent = new Intent(getApplicationContext(), ConfirmerCollecteActivity.class);
//                                                        activity2Intent.putExtra("EXTRA_CLIENTVIEW1", clientView);
//                                                        activity2Intent.putExtra("EXTRA_MONTANT", montantCollecte.getText().toString());
//                                                        startActivity(activity2Intent);
//                                                    }
//                                                })
//                                                .setNegativeButton(R.string.pop_cancel, new DialogInterface.OnClickListener() {
//                                                    public void onClick(DialogInterface dialog, int id) {
//                                                    }
//                                                });
//                                        alertDialogBuilder.create();
//                                        alertDialogBuilder.show();
                                    }else {
                                        Intent activity2Intent = new Intent(getApplicationContext(), ConfirmerCollecteActivity.class);
                                        activity2Intent.putExtra("EXTRA_CLIENTVIEW1", clientView);
                                        activity2Intent.putExtra("EXTRA_MONTANT", montantCollecte.getText().toString());
                                        startActivity(activity2Intent);
                                    }
                                }

                            }
                        }else{
                            textError.setText("Le montant de collecte ne correspond pas à la mise");
                        }

                    }
            }
        });
    }

}
