package com.perfekxia.collekt_it.ui.slider.slide2;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.perfekxia.collekt_it.R;
import com.perfekxia.collekt_it.api_manager.GetDataService;
import com.perfekxia.collekt_it.api_manager.RetrofitClientInstance;
import com.perfekxia.collekt_it.model.Affectations;
import com.perfekxia.collekt_it.model.Utilisateurs;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Slide2Fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    Context context;
    private Slide2ViewModel viewModel;

    public static ProgressBar progressBar;
    public static TextView textError;
    SwipeRefreshLayout container;

    public Slide2Fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.welcome_slide2, container, false);
        this.context = v.getContext();
        initView(v);
        initListner();
        viewModel = new ViewModelProvider(getActivity()).get(Slide2ViewModel.class);
        viewModel.init(this.context);
//        loadUser();

        return v;
    }

    private void initView(View v){
        progressBar = v.findViewById(R.id.progressBar);
        textError = v.findViewById(R.id.txtError);
        container = v.findViewById(R.id.container);
    }

    private void initListner(){
        container.setOnRefreshListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewModel.init(context);
                container.setRefreshing(false);
            }
        }, 10);
    }
}
