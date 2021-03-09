package com.perfekxia.collekt_it.ui.slider.slide3;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.perfekxia.collekt_it.R;
import com.perfekxia.collekt_it.adapter.UserAdapter;
import com.perfekxia.collekt_it.model.TitulaireView;
import com.perfekxia.collekt_it.model.TypeUser;
import com.perfekxia.collekt_it.model.Zones;
import com.perfekxia.collekt_it.preference.PrefManager;

import java.util.ArrayList;
import java.util.List;

public class Slide3Fragment extends Fragment{

    Context context;
    private Slide3ViewModel viewModel;

    RecyclerView recyclerView;
    Spinner spinnerType;
    public static TextView textError;
    TextInputEditText search;
    private UserAdapter userAdapter;

    private List<String> users = new ArrayList<>();
    private List<TypeUser> usersSpinner = new ArrayList<>();

    private String role;

    PrefManager prefManager;


    public Slide3Fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.welcome_slide3, container, false);
        this.context = v.getContext();
        viewModel = new ViewModelProvider(getActivity()).get(Slide3ViewModel.class);
        viewModel.init(this.context);
        prefManager = new PrefManager(context);
        initView(v);



//        search.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.length() != 0)
//                    userAdapter.getFilter().filter(charSequence);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

//        loadRecycleView(v.getContext());
        loadSpinnerType();
        return v;
    }

    private  void  initView(View v){
        recyclerView = v.findViewById(R.id.recycleView);
        spinnerType = v.findViewById(R.id.spinnerType);
        textError = v.findViewById(R.id.txtError);
//        search = v.findViewById(R.id.textInputEditTextSearch);
    }

//    @SuppressLint("FragmentLiveDataObserve")
    private void loadRecycleView(Context context){

        userAdapter = new UserAdapter(context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(userAdapter);

        viewModel.getAllTitulaireView().observe(getActivity(), new Observer<List<TitulaireView>>() {
            @Override
            public void onChanged(@Nullable final List<TitulaireView> titulaireViews) {
                // Update the cached copy of the words in the adapter.
                userAdapter.setClientView(titulaireViews);
            }
        });

        viewModel.getAllZone().observe(getActivity(), new Observer<List<Zones>>() {
            @Override
            public void onChanged(List<Zones> zones) {
                        userAdapter.setZoneView(zones, role);
//                loadRecycleView(context, titulaireViews, zones, role);
            }
        });
    }

    private void loadSpinnerType(){
//        users.add("Sélectionnez");
        users.add("");
        users.add("Administrateur");
        users.add("Agent commercial");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, users);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(arrayAdapter);
        spinnerType.setClickable(true);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                role = users.get(i);
                switch (role){
                    case "Administrateur":
                        prefManager.setRole(users.get(i));
                        textError.setVisibility(View.GONE);

                        load("Administrateur");
                        break;

                    case "Agent commercial":
                        prefManager.setRole(users.get(i));
                        textError.setVisibility(View.GONE);

                        load("Agent commercial");
                        break;
//                    default:
//                        loadRecycleView(getContext());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void load(String rol){
        userAdapter = new UserAdapter(context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(userAdapter);

        viewModel.getAllTitulaireView().observe(getActivity(), new Observer<List<TitulaireView>>() {
            @Override
            public void onChanged(@Nullable final List<TitulaireView> titulaireViews) {
                // Update the cached copy of the words in the adapter.
                List<TitulaireView> result = new ArrayList<>();
                for (TitulaireView tv :
                        titulaireViews) {

                    Log.i("Titulaire role", tv.TitulairesRole);

                    if (tv.TitulairesRole.equals(rol)) {
                        result.add(tv);
                    }
                }
                userAdapter.setClientView(result);
            }
        });

        viewModel.getAllZone().observe(getActivity(), new Observer<List<Zones>>() {
            @Override
            public void onChanged(List<Zones> zones) {
                userAdapter.setZoneView(zones, role);
//                loadRecycleView(context, titulaireViews, zones, role);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
