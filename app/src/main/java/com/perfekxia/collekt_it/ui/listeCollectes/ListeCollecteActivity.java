package com.perfekxia.collekt_it.ui.listeCollectes;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.perfekxia.collekt_it.R;
import com.perfekxia.collekt_it.adapter.CollecteAdapter;
import com.perfekxia.collekt_it.model.Collectes;
import com.perfekxia.collekt_it.preference.PrefManager;
import com.perfekxia.collekt_it.viewModel.CollecteViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListeCollecteActivity extends AppCompatActivity {
    private CollecteViewModel collecteViewModel;
    private CollecteAdapter cltAdapter;
    RecyclerView recyclerView;
    private List<String> users = new ArrayList<>();

    PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_collecte);

        recyclerView = findViewById(R.id.listeCollecte);

        prefManager = new PrefManager(this);

        String idcycle = getIntent().getStringExtra("IDCYCLE");
        Log.i("idcycle", String.valueOf(idcycle));

        collecteViewModel = new CollecteViewModel(getApplication(),idcycle);
        final CollecteAdapter adapter = new CollecteAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (prefManager.getLogin().getRole().equals("Administrateur")){
            collecteViewModel.getmAllCollecteByCycle().observe(this, new Observer<List<Collectes>>() {
                @Override
                public void onChanged(@Nullable final List<Collectes> collectes) {
                    adapter.setCollecte(collectes);
                }
            });
        }else {
            collecteViewModel.getAllNewCollecte().observe(this, new Observer<List<Collectes>>() {
                @Override
                public void onChanged(@Nullable final List<Collectes> collectes) {
                    List<Collectes> col =new ArrayList<Collectes>();
                    for(int i=0;i<collectes.size();i++){
                        if(collectes.get(i).getDateCollecte().getDay()==Calendar.getInstance().getTime().getDay()){
                            col.add(collectes.get(i));
                        }
                    }

                    adapter.setCollecte(col);
                }
            });
        }

    }

}
