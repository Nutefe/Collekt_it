package com.perfekxia.collekt_it.ui.slider.slide4;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.messaging.FirebaseMessaging;
import com.perfekxia.collekt_it.R;
import com.perfekxia.collekt_it.api_manager.GetDataService;
import com.perfekxia.collekt_it.api_manager.RetrofitClientInstance;
import com.perfekxia.collekt_it.api_manager.VolleySingleton;
import com.perfekxia.collekt_it.model.Equipements;
import com.perfekxia.collekt_it.model.Login;
import com.perfekxia.collekt_it.model.TitulaireView;
import com.perfekxia.collekt_it.model.Zones;
import com.perfekxia.collekt_it.preference.PrefManager;
import com.perfekxia.collekt_it.ui.configuration.WelcomeActivity;
import com.perfekxia.collekt_it.ui.login.LoginActivity;
import com.perfekxia.collekt_it.ui.slider.slide3.Slide3ViewModel;
import com.perfekxia.collekt_it.viewModel.MiseViewModel;
import com.perfekxia.collekt_it.viewModel.TitulaireViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Slide4Fragment extends Fragment implements View.OnClickListener{
    private Slide4ViewModel viewModel;
    Context context;
    Button btnAuth, btnValideAdmin;
    public static TextView txtError, txtError1, prenomAc, nomComplet, identifiantAc;
    private LinearLayout adminLayout, simpleLayout;

    private EditText dateTextDebut, dateTextFin;
    private TextInputEditText pinField;
    private Spinner spinner;

    private PrefManager prefManager;
    private static final String ROLE = "role";
    private Zones zone;

    DatePickerDialog datePickerDialog;

    TitulaireViewModel titulaireViewModel;

    public Slide4Fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.welcome_slide4, container, false);
        this.context = v.getContext();
        ButterKnife.bind(this, v);
        initView(v);
        initListner();
        viewModel = new ViewModelProvider(getActivity()).get(Slide4ViewModel.class);
        viewModel.init(this.context);

        prefManager = new PrefManager(getContext());

        if (prefManager.checkKey(ROLE)){
            if (prefManager.getRole().equals("Administrateur")){
                btnAuth.setVisibility(View.GONE);
                adminLayout.setVisibility(View.VISIBLE);
                simpleLayout.setVisibility(View.GONE);
                Login  login = prefManager.getLogin();
                if (login != null){
                    prenomAc.setText(login.getNomUtilisateur());
                    identifiantAc.setText(login.getIdTitulaire());
                    nomComplet.setText(login.getNomComplet());
                    viewModel.getAllZone().observe(getActivity(), new Observer<List<Zones>>() {
                        @Override
                        public void onChanged(List<Zones> zones) {
                            if (zones !=null){
                                loadSpinnerType(zones);
                            }
                        }
                    });
                }
            } else if (prefManager.getRole().equals("Agents commerciaux")){
                btnAuth.setVisibility(View.VISIBLE);
                adminLayout.setVisibility(View.GONE);
                simpleLayout.setVisibility(View.VISIBLE);
            } else {

            }
        }

        return v;
    }

    private void initView(View v){
        adminLayout = v.findViewById(R.id.adminLayout);
        simpleLayout = v.findViewById(R.id.simpleLayout);
        btnAuth = v.findViewById(R.id.okBtn);
        txtError = v.findViewById(R.id.txtError);
        txtError1 = v.findViewById(R.id.txtError1);
        prenomAc = v.findViewById(R.id.prenomAc);
        nomComplet = v.findViewById(R.id.nomComplet);
        identifiantAc = v.findViewById(R.id.identifiantAc);
        dateTextDebut = v.findViewById(R.id.dateTextDebut);
        dateTextFin = v.findViewById(R.id.dateTextFin);
        spinner = v.findViewById(R.id.spinner);
        btnValideAdmin = v.findViewById(R.id.btnValideAdmin);
        pinField = v.findViewById(R.id.pinField);
    }

    private  void initListner(){
        dateTextDebut.setOnClickListener(this);
        dateTextFin.setOnClickListener(this);
        btnValideAdmin.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick({ R.id.okBtn})
    public void OnClick(View view){

        switch (view.getId()){
            case R.id.okBtn:
                checkAuthorization(prefManager.getEquipement().getNumeroEquipement(), prefManager.getEquipement().getIdTitulaire());
                break;
        }
    }

    private void loadSpinnerType(List<Zones> zoneList){

        List<String> zones = new ArrayList<>();
        List<Zones> zonesSpinner = new ArrayList<>();
        zones.clear();
        zonesSpinner.clear();
        for (Zones zone:
                zoneList) {
            zones.add(zone.getNomZone());
            zonesSpinner.add(zone);
        }

        if (prefManager.pageIndex() == 4){
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, zones);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter);
            spinner.setClickable(true);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    zone = zoneList.get(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

    }

    private void getAuthorization(){
        prefManager.setFirstTimeLaunch(false);
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        startActivity(new Intent(getContext(), LoginActivity.class));
        getActivity().finishAffinity();
    }

    private void checkAuthorization(String idDevice, String idTitulaire) {
        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(context);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<Boolean> call = service.checkConfirmTitulaire(idDevice, idTitulaire);

            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()) {
                        Log.i("Success :", response.message());
                        if (response.body()){
//                            sendNot();
                            addAdminLogin();
                            Equipements equipements = prefManager.getEquipement();
                            equipements.setEtat(true);
                            updateEquipement(prefManager.getEquipement().getNumeroEquipement(), equipements.getIdTitulaire());
                        } else {
                            Slide4Fragment.txtError.setVisibility(View.VISIBLE);
                            Slide4Fragment.txtError.setText(R.string.error_auth);
                        }
                    } else {
                        Log.i("error :", response.message());
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
//                    Log.i("Error :", t.getMessage());
                }
            });
        } catch (Exception ex) {

        }
    }

    private void addAdminLogin(){
        viewModel.getAllTitulaireView().observe(getActivity(), new Observer<List<TitulaireView>>() {
            @Override
            public void onChanged(@Nullable final List<TitulaireView> titulaireViews) {
                // Update the cached copy of the words in the adapter.
                for (TitulaireView user :
                        titulaireViews) {
                    if (user.TitulairesRole.equals("Administrateur")){
                        Login login = new Login();
                        login.setIdTitulaire(String.valueOf(user.OldID));
                        login.setActif(false);
                        login.setIdZone(prefManager.getLogin().getIdZone());
                        login.setNomZone(user.TitulairesNomZ);
                        login.setMotDePasse(user.PassWordsTitulaire);
                        login.setNomComplet(user.FullNameTitulaires);
                        login.setNomUtilisateur(user.LoginTitulaire);
                        login.setNumeroEquipement(Build.ID);
                        login.setPin(user.TitulairesPin);
                        login.setRole(user.TitulairesRole);
                        login.setActif(user.Statut);
                        viewModel.insertLogin(login);
                    }
                }
            }
        });
    }



    private void updateEquipement(String idDevice, String idTitulaire){
        RetrofitClientInstance instance = new RetrofitClientInstance(context);
        GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
        Call<Equipements> call = service.updateEquipement(idDevice, idTitulaire);

        call.enqueue(new Callback<Equipements>() {
            @Override
            public void onResponse(Call<Equipements> call, Response<Equipements> response) {
                if (response.isSuccessful()){
                    Log.i("save :", response.message());
                    Equipements equipements = response.body();
                    if (equipements != null) {
//                        Log.i("Get equipement", response.body().toString());
                        if (equipements.getAutorisation() && equipements.getEtat()) {
                            prefManager.setEquipement(equipements);
                            viewModel.saveEquipement(equipements);
                            getAuthorization();
                        }
                    } else {
//                            checkEquipementAssignTitulaire(deviceID, prefManager.getEquipement().getIdTitulaire());
                        Slide4Fragment.txtError.setVisibility(View.VISIBLE);
                        Slide4Fragment.txtError.setText(R.string.error_auth);
                    }
                    Log.i("Get equipement", response.message());
                } else {
                    Log.i("error :", response.message());
                }
            }

            @Override
            public void onFailure(Call<Equipements> call, Throwable t) {
//                Log.i("Error :", t.getMessage());
            }
        });
    }

    private void datPick(EditText editText){
        // calender class's instance and get current date , month and year from calender
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day


        // date picker dialog
        datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
//                                dateText.setText(dayOfMonth + "-"
//                                        + (monthOfYear + 1) + "-" + year);
                        String dt = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
                        Date d1;
                        try {
                            d1 = dateFormat.parse(dt);
                            String strDateDebut = dateFormat.format(d1);
                            editText.setText(strDateDebut);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.show();
    }

    private void valideAdmin(){
        Log.i("zone", zone.toString());
        Log.i("pin", pinField.getText().toString());
        if (TextUtils.isEmpty(pinField.getText().toString())){
            pinField.setError("Ce champ ne peut pas être vide");
            return;
        }
        if (TextUtils.isEmpty(dateTextDebut.getText().toString())){
            dateTextDebut.setError("Ce champ ne peut pas être vide");
            return;
        }
        if (TextUtils.isEmpty(dateTextFin.getText().toString())){
            dateTextFin.setError("Ce champ ne peut pas être vide");
            return;
        }


        if (prefManager.getLogin().getPin() == Integer.parseInt(pinField.getText().toString())){
            Login login = prefManager.getLogin();
            login.setDateDebutCycle(dateTextDebut.getText().toString());
            login.setDateFinCycle(dateTextFin.getText().toString());
            login.setIdZone(zone.getIdZone());
            login.setNomZone(zone.getNomZone());
            Log.i("Login admin", login.toString());
            Long id_login = viewModel.insertLogin(login);
            if (id_login != null){
//                prefManager.setLogin(login);
                getAuthorization();
            } else {
                Slide4Fragment.txtError1.setVisibility(View.VISIBLE);
                Slide4Fragment.txtError1.setText(R.string.error_conf);
            }
            Log.i("id login", String.valueOf(id_login));
        } else {
            Slide4Fragment.txtError1.setVisibility(View.VISIBLE);
            Slide4Fragment.txtError1.setText(R.string.error_incorrect_pass);
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dateTextDebut:
                datPick(dateTextDebut);
                break;
            case R.id.dateTextFin:
                datPick(dateTextFin);
                break;
            case R.id.btnValideAdmin:
                valideAdmin();
                break;
        }
    }
}
