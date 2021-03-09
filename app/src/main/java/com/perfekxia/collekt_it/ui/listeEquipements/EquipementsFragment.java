package com.perfekxia.collekt_it.ui.listeEquipements;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.perfekxia.collekt_it.R;
import com.perfekxia.collekt_it.adapter.EquipementsAdapter;
import com.perfekxia.collekt_it.api_manager.GetDataService;
import com.perfekxia.collekt_it.api_manager.RetrofitClientInstance;
import com.perfekxia.collekt_it.model.Equipements;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EquipementsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    Button button;
    RecyclerView recyclerView;
    EquipementsAdapter adapter;
    SwipeRefreshLayout containe;

    public static ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_equipements, container, false);

        initView(root);

        containe.setOnRefreshListener(this::onRefresh);
        adapter = new EquipementsAdapter(getContext(), getActivity().getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadEquipements(getContext());
        return root;
    }

    private void initView(View v){
        progressBar = v.findViewById(R.id.progressBar);
        recyclerView = v.findViewById(R.id.listeAutEquipement);
        containe = v.findViewById(R.id.containerListeEquipements);
    }

    public void loadEquipements(Context context){
        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(context);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<List<Equipements>> call = service.unauthorizedDevices();

            progressBar.setVisibility(View.VISIBLE);
            call.enqueue(new Callback<List<Equipements>>() {
                @Override
                public void onResponse(Call<List<Equipements>> call, Response<List<Equipements>> response) {
                    if (response.isSuccessful()){
                        progressBar.setVisibility(View.GONE);
                        List<Equipements> equipements = response.body();
                        Log.i("Liste Zones :", response.body().toString());
                        if (equipements.size()>0){
                            adapter.setEquipements(response.body());

                        }
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Log.i("error :", response.message());
                    }
                }

                @Override
                public void onFailure(Call<List<Equipements>> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }catch (Exception ex){
            progressBar.setVisibility(View.GONE);
            Log.i("Ecxception error: ",ex.getMessage());
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               loadEquipements(getContext());
                containe.setRefreshing(false);
            }
        }, 10);
    }
}
