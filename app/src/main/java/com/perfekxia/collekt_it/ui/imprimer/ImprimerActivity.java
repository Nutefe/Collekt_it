package com.perfekxia.collekt_it.ui.imprimer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.perfekxia.collekt_it.MainActivity;
import com.perfekxia.collekt_it.R;
import com.perfekxia.collekt_it.model.Clients;
import com.perfekxia.collekt_it.model.Localisations;
import com.perfekxia.collekt_it.viewModel.ClientViewModel;
import com.perfekxia.collekt_it.viewModel.LocalisationsViewModel;

import java.util.List;


public class ImprimerActivity extends AppCompatActivity {
    private LocalisationsViewModel localisationsViewModel;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imprimer);
        progressBar = findViewById(R.id.progressBarImprimer);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(activity2Intent);
                finish();
            }
        }, 5000);

    }

}
