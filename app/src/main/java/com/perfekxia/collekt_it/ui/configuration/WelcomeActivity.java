package com.perfekxia.collekt_it.ui.configuration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.perfekxia.collekt_it.MainActivity;
import com.perfekxia.collekt_it.R;
import com.perfekxia.collekt_it.api_manager.GetDataService;
import com.perfekxia.collekt_it.api_manager.RetrofitClientInstance;
import com.perfekxia.collekt_it.api_manager.VolleySingleton;
import com.perfekxia.collekt_it.model.Equipements;
import com.perfekxia.collekt_it.model.Login;
import com.perfekxia.collekt_it.model.TitulaireView;
import com.perfekxia.collekt_it.preference.PrefManager;
import com.perfekxia.collekt_it.ui.login.LoginActivity;
import com.perfekxia.collekt_it.ui.slider.slide1.Slide1Fragment;
import com.perfekxia.collekt_it.ui.slider.slide2.Slide2Fragment;
import com.perfekxia.collekt_it.ui.slider.slide2.Slide2ViewModel;
import com.perfekxia.collekt_it.ui.slider.slide3.Slide3Fragment;
import com.perfekxia.collekt_it.ui.slider.slide4.Slide4Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager viewPager;
//    private MyViewPagerAdapter myViewPagerAdapter;

    private TextView[] dots;
    private int[] layouts = new int[]{
            R.layout.welcome_slide1,
            R.layout.welcome_slide2,
            R.layout.welcome_slide3,
            R.layout.welcome_slide4
    };

    private PrefManager prefManager;
    Fragment fragment = null;

    private String address;

    int current;

    final Slide1Fragment slide1Fragment = new Slide1Fragment();

//    @BindView(R.id.btn_skip)
    public static Button btnSkip;
//    @BindView(R.id.btn_next)
    public static Button btnNext;
    @BindView(R.id.layoutDots)
    LinearLayout dotsLayout;

    private WelecomeViewModel viewModel;

    String deviceID = Build.ID;
    private static final String ROLE = "role";
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

        viewModel = new ViewModelProvider(this).get(WelecomeViewModel.class);
        viewModel.init(this);

        initView();
        initListner();

        prefManager = new PrefManager(this);
        checkPage();

        pd = new ProgressDialog(this);
        pd.setMessage("Chargement des données en cours...");
        pd.setCanceledOnTouchOutside(false);

    }

    private  void initView(){

        btnSkip = findViewById(R.id.btn_skip);
        btnNext = findViewById(R.id.btn_next);
        dotsLayout = findViewById(R.id.layoutDots);
    }

    private  void initListner(){
        btnSkip.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        finish();
    }

    private void checkPage() {

        if (prefManager.pageIndex() == 0){
                btnSkip.setText(getString(R.string.quitter));
                FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.view_pager, slide1Fragment);
                transaction1.commit();
                addBottomDots(0);
                current = 1;
            } else if (prefManager.pageIndex() == 1){
                btnSkip.setText(getString(R.string.quitter));
                FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.view_pager, slide1Fragment);
                transaction1.commit();
                addBottomDots(0);
                current = prefManager.pageIndex();
            } else if (prefManager.pageIndex() == 2){
                btnSkip.setText(getString(R.string.retour));
                fragment = new Slide2Fragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.view_pager, fragment);
                transaction.commit();
                addBottomDots(1);
                btnNext.setVisibility(View.VISIBLE);
                current = prefManager.pageIndex();
            } else if (prefManager.pageIndex() == 3){
                btnSkip.setText(getString(R.string.retour));
                fragment = new Slide2Fragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.view_pager, fragment);
                transaction.commit();
                addBottomDots(1);
                btnNext.setVisibility(View.VISIBLE);
                current = 2;
            } else if (prefManager.pageIndex() == 4){
                btnSkip.setText(getString(R.string.retour));
                fragment = new Slide4Fragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.view_pager, fragment);
                transaction.commit();
                current = prefManager.pageIndex();
                addBottomDots(3);
                btnNext.setVisibility(View.GONE);
            }
            prefManager.setPageIndex(current);
    }

    private  void setBtnSkip(){
        if (current < 5 && current > 1){
            Log.i("Current", String.valueOf(current));
            current =current - 1;
            prefManager.setPageIndex(current);
            if (current == 1){
                btnSkip.setText(getString(R.string.quitter));
                fragment = slide1Fragment;
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.view_pager, fragment);
                transaction.commit();
                btnNext.setVisibility(View.VISIBLE);
                addBottomDots(0);
            } else if (current == 2){
                btnSkip.setText(getString(R.string.retour));
                fragment = new Slide2Fragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.view_pager, fragment);
                transaction.commit();
                addBottomDots(1);
                btnNext.setVisibility(View.VISIBLE);
                prefManager.deleteRole();
            }else if (current == 3){
                btnSkip.setText(getString(R.string.retour));
                fragment = new Slide3Fragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.view_pager, fragment);
                transaction.commit();
                addBottomDots(2);
                btnNext.setVisibility(View.VISIBLE);
                prefManager.deleteRole();
            }else if (current == 4){
                btnSkip.setText(getString(R.string.retour));
                fragment = new Slide4Fragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.view_pager, fragment);
                transaction.commit();
                addBottomDots(3);
                btnNext.setVisibility(View.GONE);
                prefManager.deleteRole();

            }
        }
    }

    private  void setBtnNext(){
        Fragment fragment;
        if (current < 4){
            current =current +1;

            if (current == 1){
                btnSkip.setText(getString(R.string.quitter));
//                        fragment = new Slide1Fragment();
                fragment = slide1Fragment;
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.view_pager, fragment);
                transaction.commit();
                addBottomDots(0);
                prefManager.deleteRole();
                btnNext.setVisibility(View.VISIBLE);
                prefManager.setPageIndex(current);
            } else if (current == 2){
                btnSkip.setText(getString(R.string.retour));
                fragment = new Slide2Fragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.view_pager, fragment);
                transaction.commit();
                addBottomDots(1);
                btnNext.setVisibility(View.VISIBLE);
                prefManager.setServerAddress(slide1Fragment.editText.getText().toString().trim());
                prefManager.deleteRole();
                prefManager.setPageIndex(current);
            }else if (current == 3){
                btnSkip.setText(getString(R.string.retour));
                fragment = new Slide3Fragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.view_pager, fragment);
                transaction.commit();
                addBottomDots(2);
                prefManager.deleteRole();
                btnNext.setVisibility(View.VISIBLE);
                prefManager.setPageIndex(current);
            }else if (current == 4){
                btnSkip.setText(getString(R.string.retour));

                if (prefManager.checkKey(ROLE)){
                    if (prefManager.getRole().equals("Agent commercial")){
                        current = current - 1;
                        Log.i("Simple click", "ok");
                        Slide3Fragment.textError.setVisibility(View.GONE);
                        checkEquipementExit();
                    } else if (prefManager.getRole().equals("Administrateur")){
                        if (prefManager.getLogin() != null){
//                            current = current - 1;
                            Slide3Fragment.textError.setVisibility(View.GONE);
                            fragment = new Slide4Fragment();
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.view_pager, fragment);
                            transaction.commit();
                            addBottomDots(3);
                            btnNext.setVisibility(View.GONE);
                            prefManager.setPageIndex(current);
                        } else {
                            current = current - 1;
                            Slide3Fragment.textError.setVisibility(View.VISIBLE);
                            Slide3Fragment.textError.setText("Sélectionnez un utilisateur");
                        }
                    } else {
                        current = current - 1;
                        Slide3Fragment.textError.setVisibility(View.VISIBLE);
                        Slide3Fragment.textError.setText("Sélectionnez un type utilisateur");
                    }
                } else {
                    current = current - 1;
                    Slide3Fragment.textError.setVisibility(View.VISIBLE);
                    Slide3Fragment.textError.setText("Sélectionnez un type utilisateur");
                }

            }
        }
    }

    private void launchLoginScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void sendNot(String titu){


        Map<String, String> data = new HashMap<>();
        data.put("title", getResources().getString(R.string.notification_title));
        data.put("content",  getResources().getString(R.string.notification_content)+" "+titu);
        data.put("imageUrl", "");
        data.put("gameUrl", "");

        Map<String, String> not = new HashMap<>();

        JSONObject object = new JSONObject(data);

        JSONObject params = new JSONObject();

        try {
            params.put("data", object);
            params.put("to", "/topics/all");

        } catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "https://fcm.googleapis.com/fcm/send",
                params,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("respons", response.toString());

                        try {
                            if (response != null){

                                String message_id = response.getString("message_id");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = "";
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                } else {
                    message = "Username ou mot de passe incorrecte";
                }
                Log.i("login_erreur", message);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization","key=AAAAO9FlzJo:APA91bEk8-3165bMsP6SipWrVeTMwKyYULAoY3BVcljJpdCtRsPGAoUqXTmsPD84faaS3iK5KqWFjH2MXjZPiYXq8O_uck9aFE9VRhEyJlxi35pzTv9jscjTSuWF7nZMZ_5NH2kORWll");
                return headers;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

    private void saveEquipement(Equipements equipement){
        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(this);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<Equipements> call = service.saveEquipement(equipement);

            call.enqueue(new Callback<Equipements>() {
                @Override
                public void onResponse(Call<Equipements> call, Response<Equipements> response) {
                    if (response.isSuccessful()){
                        sendNot(equipement.getIdTitulaire());
                        current = current + 1;
                        fragment = new Slide4Fragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.view_pager, fragment);
                        transaction.commit();
                        addBottomDots(3);
                        prefManager.deleteRole();
                        prefManager.setEquipement(response.body());
                        Long id_login = viewModel.insertLogin(prefManager.getLogin());
                        Log.i("id_login :", String.valueOf(id_login));
                        btnNext.setVisibility(View.GONE);
                        prefManager.setPageIndex(current);
                        pd.dismiss();
                        Log.i("save :", response.message());
                    } else {
                        Slide3Fragment.textError.setVisibility(View.VISIBLE);
                        Slide3Fragment.textError.setText("Erreur de configuration de l'utilisateur");
                        Log.i("error :", response.message());
                        pd.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<Equipements> call, Throwable t) {
//                    Log.i("Error :", t.getMessage());
                    Slide3Fragment.textError.setVisibility(View.VISIBLE);
                    Slide3Fragment.textError.setText("Erreur de configuration de l'utilisateur");
                    pd.dismiss();
                }
            });
        }catch (Exception ex){
            Slide3Fragment.textError.setVisibility(View.VISIBLE);
            Slide3Fragment.textError.setText("Erreur de configuration de l'utilisateur");
            pd.dismiss();
        }
    }

    private void updateEquipement(Equipements equipement, int id){
        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(this);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<Equipements> call = service.updateEquipement(id, equipement);

            call.enqueue(new Callback<Equipements>() {
                @Override
                public void onResponse(Call<Equipements> call, Response<Equipements> response) {
                    if (response.isSuccessful()){
                        Log.i("save :", response.message());
                    } else {
                        Log.i("error :", response.message());
                    }
                }

                @Override
                public void onFailure(Call<Equipements> call, Throwable t) {
//                    Log.i("Error :", t.getMessage());
                }
            });
        }catch (Exception ex){

        }
    }

    public void checkEquipementExit(){

        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(this);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<Boolean> call = service.checkEquipementExist(deviceID);
            pd.show();
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()){
                        if (response.body()){
                            if (prefManager.getEquipement() != null){
//                                checkEquipement(deviceID, prefManager.getEquipement().getIdTitulaire());
                                checkEquipementUse();
                            } else {
                                Slide3Fragment.textError.setVisibility(View.VISIBLE);
                                Slide3Fragment.textError.setText("Sélectionnez un utilisateur à configuré");
                                pd.dismiss();
                            }
                        }else {
                            checkEquipementUse();
                        }
                        Log.i("Success device exist", response.message());
                    } else {
                        Log.i("error :", response.message());
                        pd.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
//                    Log.i("Error", t.getCause().getMessage());
                    pd.dismiss();
                }
            });
        }catch (Exception ex){
            pd.dismiss();
        }
    }

    private void checkEquipementUse(){

        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(this);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<Boolean> call = service.checkEquipementUse(deviceID);

            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()){
                        if (response.body()){
//                            Slide3Fragment.textError.setVisibility(View.VISIBLE);
//                            Slide3Fragment.textError.setText("Cet équipement est en cours d'utililisation");
                            checkEquipement(deviceID, prefManager.getEquipement().getIdTitulaire());
                        }else {
                            if (prefManager.getEquipement() != null){
                                checkEquipement(deviceID, prefManager.getEquipement().getIdTitulaire());
                            } else {
                                Slide3Fragment.textError.setVisibility(View.VISIBLE);
                                Slide3Fragment.textError.setText("Sélectionnez un utilisateur à configuré");
                                pd.dismiss();
                            }
                        }
                        Log.i("Success device using:", response.message());
                    } else {
                        Log.i("error :", response.message());
                        pd.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
//                    Log.i("Error :", t.getMessage());
                    pd.dismiss();
                }
            });
        } catch (Exception ex){
            pd.dismiss();
        }

    }

    private void checkEquipement(String idDevice, String idTitulaire){
        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(this);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<Equipements> call = service.getEquipement(idDevice, idTitulaire);

            call.enqueue(new Callback<Equipements>() {
                @Override
                public void onResponse(Call<Equipements> call, Response<Equipements> response) {
                    if (response.isSuccessful()){
                        Log.i("Success :", response.message());
                        Equipements equipements = response.body();
                        if (equipements != null){
                            if (equipements.getAutorisation() && equipements.getEtat()){
                                pd.dismiss();
                                prefManager.setEquipement(equipements);
                                viewModel.insertLogin(prefManager.getLogin());
                                viewModel.saveEquipement(equipements);
                                viewModel.getAllTitulaireView().observe(WelcomeActivity.this, new Observer<List<TitulaireView>>() {
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
                                launchLoginScreen();
                            }
                        } else {
                            if (prefManager.getEquipement() != null){
                                checkEquipementAssignTitulaire(deviceID, prefManager.getEquipement().getIdTitulaire());
                            } else {
                                pd.dismiss();
                                Slide3Fragment.textError.setVisibility(View.VISIBLE);
                                Slide3Fragment.textError.setText("Sélectionnez un utilisateur à configuré");
                            }
                        }
                        Log.i("Get equipement", response.message());
                    } else {
                        pd.dismiss();
                        Log.i("error :", response.message());
                    }
                }

                @Override
                public void onFailure(Call<Equipements> call, Throwable t) {
                    pd.dismiss();
//                    Log.i("Error :", t.getMessage());
                }
            });
        }catch (Exception ex){
            pd.dismiss();
        }
    }

    private void checkAuthorization(String idDevice, String idTitulaire){
        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(this);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<Equipements> call = service.getEquipement(idDevice, idTitulaire);

            call.enqueue(new Callback<Equipements>() {
                @Override
                public void onResponse(Call<Equipements> call, Response<Equipements> response) {
                    if (response.isSuccessful()){
                        Log.i("Success :", response.message());
                        Equipements equipements = response.body();
                        if (equipements != null){
//                        Log.i("Get equipement", response.body().toString());
                            if (equipements.getAutorisation() && equipements.getEtat()){
                                prefManager.setEquipement(equipements);
                                launchLoginScreen();
                            }
                        } else {

                            if (prefManager.getEquipement() != null){
                                checkEquipementAssignTitulaire(deviceID, prefManager.getEquipement().getIdTitulaire());
                            } else {
                                Slide3Fragment.textError.setVisibility(View.VISIBLE);
                                Slide3Fragment.textError.setText("Sélectionnez un utilisateur à configuré");
                            }
                        }
                        Log.i("Get equipement", response.message());
                    } else {
                        Log.i("error :", response.message());
                    }
                }

                @Override
                public void onFailure(Call<Equipements> call, Throwable t) {
//                    Log.i("Error :", t.getMessage());
                }
            });
        }catch (Exception ex){

        }
    }

    private void checkEquipementAssignTitulaire(String idDevice,String idTitulaire){

        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(this);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<Boolean> call = service.checkConfirmTitulaire(idDevice, idTitulaire);

            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()){
                        if (response.body()){
                            pd.dismiss();
//                            launchLoginScreen();
                            current = current + 1;
                            fragment = new Slide4Fragment();
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.view_pager, fragment);
                            transaction.commit();
                            addBottomDots(3);
                            prefManager.deleteRole();
//                            prefManager.setEquipement(response.body());
                            Long id_login = viewModel.insertLogin(prefManager.getLogin());
                            Log.i("id_login :", String.valueOf(id_login));
                            btnNext.setVisibility(View.GONE);
                            prefManager.setPageIndex(current);
                            Log.i("save :", response.message());
                        }else {

                            if (prefManager.getEquipement() != null){
                                checkTitulaireHaveEquipement(prefManager.getEquipement().getIdTitulaire());
                            } else {
                                pd.dismiss();
                                Slide3Fragment.textError.setVisibility(View.VISIBLE);
                                Slide3Fragment.textError.setText("Sélectionnez un utilisateur à configuré");
                            }
                        }
                        Log.i("Success device assign", response.message());
                    } else {
                        pd.dismiss();
                        Log.i("error :", response.message());
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    pd.dismiss();
//                    Log.i("Error :", t.getMessage());
                }
            });
        }catch (Exception ex){
            pd.dismiss();
        }
    }

    private void checkTitulaireHaveEquipement(String idTitulaire){

        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(this);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<Boolean> call = service.checkEquipementAssigned( idTitulaire);

            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()){
                        if (response.body()){
                            pd.dismiss();
                            Slide3Fragment.textError.setVisibility(View.VISIBLE);
                            Slide3Fragment.textError.setText("Cet d'utilisateur possède déjà un équipement");
                        }else {
                            if (prefManager.getEquipement() != null){
                                checkMultipleUsage(deviceID, prefManager.getEquipement().getIdTitulaire());
                            } else {
                                pd.dismiss();
                                Slide3Fragment.textError.setVisibility(View.VISIBLE);
                                Slide3Fragment.textError.setText("Sélectionnez un utilisateur à configuré");
                            }
                        }
                        Log.i("Success titu have devic", response.message());
                    } else {
                        pd.dismiss();
                        Log.i("error :", response.message());
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    pd.dismiss();
//                    Log.i("Error :", t.getMessage());
                }
            });
        } catch (Exception ex){
            pd.dismiss();
        }
    }

    private void checkMultipleUsage(String idDevice, String idTitulaire){

        try {
            RetrofitClientInstance instance = new RetrofitClientInstance(this);
            GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
            Call<Boolean> call = service.checkMultipleUsage(idDevice, idTitulaire);

            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()){
                        if (response.body()){
                            pd.dismiss();
                            // Redirect to slide4fragment
                            current = current + 1;
                            fragment = new Slide4Fragment();
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.view_pager, fragment);
                            transaction.commit();
                            addBottomDots(3);
                            prefManager.deleteRole();
//                            prefManager.setEquipement(response.body());
                            btnNext.setVisibility(View.GONE);
                            prefManager.setPageIndex(current);
                            Log.i("save :", response.message());
                        }else {
                            if (prefManager.getEquipement() != null){
                                saveEquipement(prefManager.getEquipement());
                            } else {
                                pd.dismiss();
                                Slide3Fragment.textError.setVisibility(View.VISIBLE);
                                Slide3Fragment.textError.setText("Sélectionnez un utilisateur à configuré");
                            }

                        }
                        Log.i("Success device+titu use", response.message());
                    } else {
                        pd.dismiss();
                        Log.i("error :", response.message());
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    pd.dismiss();
//                    Log.i("Error :", t.getMessage());
                }
            });
        }catch (Exception ex){
            pd.dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_skip:
                if (btnSkip.getText().toString().trim().equals("Quitter")){
                    this.finish();
                } else {
                    setBtnSkip();
                }
                break;
            case R.id.btn_next:
                setBtnNext();
                break;
        }
    }
}
