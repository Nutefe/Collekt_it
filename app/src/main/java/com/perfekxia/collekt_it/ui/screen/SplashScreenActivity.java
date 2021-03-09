package com.perfekxia.collekt_it.ui.screen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.perfekxia.collekt_it.MainActivity;
import com.perfekxia.collekt_it.R;
import com.perfekxia.collekt_it.preference.PrefManager;
import com.perfekxia.collekt_it.ui.configuration.WelcomeActivity;
import com.perfekxia.collekt_it.ui.login.LoginActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity {

    private int SPLASH_TIME = 5000;

    @BindView(R.id.imageViewScreen)
    ImageView imageViewScreen;

    private PrefManager prefManager;

    private static final String LOGIN_DATE = "loginDate";
    private static final String CONNEXION = "connexion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);

        ButterKnife.bind(this);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.screen_trasanction);
        imageViewScreen.setAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                prefManager = new PrefManager(SplashScreenActivity.this);
                if (!prefManager.isFirstTimeLaunch()) {
                    Date now = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String snow = format.format(now);
                    Date d1, d2;
                    try {
                        d1 = format.parse(snow);
                        if (prefManager.checkKey(CONNEXION)){
                            if (prefManager.isConnexion()){
                                if (prefManager.checkKey(LOGIN_DATE)){
                                    d2 = format.parse(prefManager.getLoginDate());
                                    if (d1.compareTo(d2) > 0){
                                        launchLoginScreen();
                                        finish();
                                    } else {
                                        Log.i("home", "okkk");
                                        launchHomeScreen();
//                                        prefManager.setLoginDate("2020-09-19");
                                        //pass4
                                        finish();
                                    }
                                }else {
                                    launchLoginScreen();
                                    finish();
                                }
                            }else {
                                launchLoginScreen();
                                finish();
                            }

                        } else {
                            launchLoginScreen();
                            finish();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    launchWelcomeScreen();
                    finish();
                }
            }
        }, SPLASH_TIME);

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

//    private void launchHomeScreen() {
//        startActivity(new Intent(this, MainActivity.class));
//        finish();
//    }
//
//    private void launchWelcomeScreen() {
//        startActivity(new Intent(this, WelcomeActivity.class));
//        finish();
//    }
//
//    private void launchLoginScreen() {
//        startActivity(new Intent(this, LoginActivity.class));
//        finish();
//    }
}
