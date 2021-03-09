package com.perfekxia.collekt_it.ui.clientsVisites;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.perfekxia.collekt_it.R;
import com.perfekxia.collekt_it.adapter.ClientAdapter;
import com.perfekxia.collekt_it.model.ClientView;
import com.perfekxia.collekt_it.viewModel.ClientViewViewModel;
import com.perfekxia.collekt_it.viewModel.CollecteViewModel;

import java.util.ArrayList;
import java.util.List;

public class ClientsVisitesActivity extends AppCompatActivity {
    private ClientViewViewModel clientViewViewModel;
    private CollecteViewModel collecteViewModel;
    private ClientAdapter cltAdapter;
    private List<ClientView> mCltView;
    private ClientsVisitesViewModel clientsVisitesViewModel;
    RecyclerView recyclerView;
    private List<String> users = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients_visites);
        recyclerView = findViewById(R.id.listeClientsVisites);
        mCltView = new ArrayList<ClientView>();
        final ClientAdapter adapter = new ClientAdapter(this,getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        collecteViewModel = new CollecteViewModel(getApplication(),"A");
        clientsVisitesViewModel = new ClientsVisitesViewModel(mCltView);
        clientsVisitesViewModel.getClt().observe(ClientsVisitesActivity.this, new Observer<List<ClientView>>() {
            @Override
            public void onChanged(List<ClientView> clientViews) {
                collecteViewModel.getmListeDistinctsCollectes().observe(ClientsVisitesActivity.this, new Observer<List<String>>() {
                    @Override
                    public void onChanged(List<String> strings) {
                        if (strings.size()!=0){
                            adapter.setClientView(mCltView);
                            for(int i=0;i<strings.size();i++){
                                clientViewViewModel = new ClientViewViewModel(getApplication(),strings.get(i),"A");
                                clientViewViewModel.getClientViewById().observe(ClientsVisitesActivity.this, new Observer<ClientView>() {
                                    @Override
                                    public void onChanged(ClientView clientView) {
                                        mCltView.add(clientView);
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }

                        }
                    }
                });
            }
        });

    }

}
