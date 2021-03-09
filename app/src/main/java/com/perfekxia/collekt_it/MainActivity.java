package com.perfekxia.collekt_it;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.perfekxia.collekt_it.preference.PrefManager;
import com.perfekxia.collekt_it.ui.configuration.WelcomeActivity;
import com.perfekxia.collekt_it.ui.dashboard.DashboardFragment;
import com.perfekxia.collekt_it.ui.listeEquipements.EquipementsFragment;
import com.perfekxia.collekt_it.ui.login.LoginActivity;
import com.perfekxia.collekt_it.ui.recuperation.RecuperationFragment;
import com.perfekxia.collekt_it.ui.profil.ProfilFragment;
import com.perfekxia.collekt_it.ui.screen.SplashScreenActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private LinearLayout linearLayout;

    PrefManager prefManager;

    private static final String LOGIN_DATE = "loginDate";
    private static final String CONNEXION = "connexion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar =  findViewById(R.id.idToolbarDashboardAgent);
//        toolbar.setTitle(R.string.app_name_m);
//        toolbar.setTitleMarginStart(50);
//        setSupportActionBar(toolbar);

        initView();
        prefManager = new PrefManager(this);

        prefManager = new PrefManager(this);
        if (prefManager.checkKey(CONNEXION)){
            if (prefManager.isConnexion()){
                //
            }else {
                launchLoginScreen();
                finish();
            }

        } else {
            launchLoginScreen();
            finish();
        }


        if (prefManager.getLogin().getRole().equals("Administrateur")){
            bottomNavigationView.inflateMenu(R.menu.bottom_nav_menu_admin);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.navigation_dashboard:
                            selectedFragment = new DashboardFragment();
                            Bundle bundleAcceuil = new Bundle();
                            selectedFragment.setArguments(bundleAcceuil);
                            break;
                        case R.id.navigation_profil:
                            selectedFragment =new ProfilFragment();
                            Bundle bundleRapport = new Bundle();
                            selectedFragment.setArguments(bundleRapport);
                            break;
                        case R.id.navigation_recuperation:
                            selectedFragment = new RecuperationFragment();
                            Bundle bundleDocument = new Bundle();
                            selectedFragment.setArguments(bundleDocument);
                            break;
                        case R.id.navigation_auth:
                            selectedFragment = new EquipementsFragment();
                            Bundle bundleEquipement = new Bundle();
                            selectedFragment.setArguments(bundleEquipement);
                            break;
                    }
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame, selectedFragment);
                    transaction.commit();
                    return true;
                }
            });

            DashboardFragment dashboardFragment = new DashboardFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame, dashboardFragment);
            transaction.commit();
        } else {
            bottomNavigationView.inflateMenu(R.menu.bottom_nav_menu);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.navigation_dashboard:
                            selectedFragment = new DashboardFragment();
                            Bundle bundleAcceuil = new Bundle();
                            selectedFragment.setArguments(bundleAcceuil);
                            break;
                        case R.id.navigation_profil:
                            selectedFragment =new ProfilFragment();
                            Bundle bundleRapport = new Bundle();
                            selectedFragment.setArguments(bundleRapport);
                            break;
                        case R.id.navigation_recuperation:
                            selectedFragment = new RecuperationFragment();
                            Bundle bundleDocument = new Bundle();
                            selectedFragment.setArguments(bundleDocument);
                            break;
                    }
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame, selectedFragment);
                    transaction.commit();
                    return true;
                }
            });

            DashboardFragment dashboardFragment = new DashboardFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame, dashboardFragment);
            transaction.commit();
        }


    }

    private void initView(){
        bottomNavigationView = findViewById(R.id.idBottomNavigationAgent);
        linearLayout = findViewById(R.id.ltest);
    }

    private void launchHomeScreen() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void launchWelcomeScreen() {
        startActivity(new Intent(this, WelcomeActivity.class));
        finish();
    }

    private void launchLoginScreen() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {

        Fragment f = getSupportFragmentManager().findFragmentById(R.id.frame);

        if (prefManager.getLogin().getRole().equals("Administrateur")){
            if (f instanceof ProfilFragment){

                DashboardFragment dashboardFragment = new DashboardFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, dashboardFragment);
                transaction.commit();
                bottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);
            }
            else if (f instanceof RecuperationFragment){

                DashboardFragment dashboardFragment = new DashboardFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, dashboardFragment);
                transaction.commit();
                bottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);
            } else if (f instanceof EquipementsFragment){
                DashboardFragment dashboardFragment = new DashboardFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, dashboardFragment);
                transaction.commit();
                bottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);
            }
            else {
                super.onBackPressed();

            }
        } else {
            if (f instanceof ProfilFragment){

                DashboardFragment dashboardFragment = new DashboardFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, dashboardFragment);
                transaction.commit();
                bottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);
            }
            else if (f instanceof RecuperationFragment){

                DashboardFragment dashboardFragment = new DashboardFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, dashboardFragment);
                transaction.commit();
                bottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);
            }
            else {
                super.onBackPressed();

            }
        }



    }

}