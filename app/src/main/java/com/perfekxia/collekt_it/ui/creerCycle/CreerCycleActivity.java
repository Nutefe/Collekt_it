package com.perfekxia.collekt_it.ui.creerCycle;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.perfekxia.collekt_it.ui.confirmerCollecte.ConfirmerCollecteActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CreerCycleActivity extends AppCompatActivity {
    private CreerCycleViewModel creerCycleViewModel;
    ClientView clientView;
    Calendar c = Calendar.getInstance();
    Calendar c1 = Calendar.getInstance();
    Date currentTime = c.getTime();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String strDateDebut = dateFormat.format(currentTime);
    private EditText montantCollecte,montantMise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_cycle);
        final TextView textView = findViewById(R.id.cltAccountCreerCycle);
        final TextView textView1 = findViewById(R.id.montantCollecteCreerCycle);
        final TextView textView2 = findViewById(R.id.montantMiseCreerCycle);
        final TextView textViewDateDebut = findViewById(R.id.dateDebut);
        final TextView textViewDateFin = findViewById(R.id.dateFin);
        final TextView textError = findViewById(R.id.errorText);
        Button validation = findViewById(R.id.validerCycle);
        montantCollecte = findViewById(R.id.montantCollecteCycle);
        montantMise = findViewById(R.id.montantMiseCycle);
        clientView = (ClientView) getIntent().getSerializableExtra("EXTRA_CLIENTVIEW");
        String infoAccount = clientView.IdClient.concat("°").concat(clientView.Nom).concat(" ").concat(clientView.Prenom);
        c1.add(Calendar.DAY_OF_MONTH, 36);
        String strDateFin = dateFormat.format(c1.getTime());

        creerCycleViewModel = new CreerCycleViewModel(infoAccount, strDateDebut,strDateFin);

        creerCycleViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        creerCycleViewModel.getTextDateDebut().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textViewDateDebut.setText(s);
            }
        });

        creerCycleViewModel.getTextDateFin().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textViewDateFin.setText(s);
            }
        });

        validation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                        textView1.setText(montantCollecte.getText().toString());
                        textView2.setText(montantMise.getText().toString());
                if (TextUtils.isEmpty(montantCollecte.getText()) || TextUtils.isEmpty(montantMise.getText())) {
                  textError.setText("L'un des champs est vide.Veuillez-remplir tous les champs");
                } else if (!TextUtils.isEmpty(montantCollecte.getText()) && !TextUtils.isEmpty(montantMise.getText())){
                    textError.setText("");
                    if (Integer.parseInt(montantCollecte.getText().toString()) % Integer.parseInt(montantMise.getText().toString()) == 0){
                        if(Integer.parseInt(montantCollecte.getText().toString()) > (Integer.parseInt(montantMise.getText().toString())*31)){
                            textError.setText("Le montant de collecte est supérieur à la somme des collectes à percevoir");
                             }
                        else {
                            if (Integer.parseInt(montantCollecte.getText().toString()) == (Integer.parseInt(montantMise.getText().toString())*31)){

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

//                                LayoutInflater li = LayoutInflater.from(v.getContext());
//                                View view = li.inflate(R.layout.pop_up_fin_cycle, null);
//                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
//                                alertDialogBuilder.setView(view)
//                                        .setPositiveButton(R.string.pop_valider, new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int id) {
//                                                Intent activity2Intent = new Intent(getApplicationContext(), ConfirmerCollecteActivity.class);
//                                                activity2Intent.putExtra("EXTRA_CLIENTVIEW1", clientView);
//                                                activity2Intent.putExtra("EXTRA_MONTANT", montantCollecte.getText().toString());
//                                                startActivity(activity2Intent);
//                                            }
//                                        })
//                                        .setNegativeButton(R.string.pop_cancel, new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int id) {
//                                            }
//                                        });
//                                alertDialogBuilder.create();
//                                alertDialogBuilder.show();
                            }else {
                                Intent activity2Intent = new Intent(getApplicationContext(), ConfirmerCollecteActivity.class);
                                activity2Intent.putExtra("EXTRA_DATE_DEBUT", currentTime);
                                activity2Intent.putExtra("EXTRA_DATE_FIN", c1.getTime());
                                activity2Intent.putExtra("EXTRA_CLIENTVIEW1", clientView);
                                activity2Intent.putExtra("EXTRA_MONTANT", montantCollecte.getText().toString());
                                activity2Intent.putExtra("EXTRA_MONTANT_MISE", montantMise.getText().toString());
                                startActivity(activity2Intent);
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
