package com.perfekxia.collekt_it.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.perfekxia.collekt_it.MainActivity;
import com.perfekxia.collekt_it.R;
import com.perfekxia.collekt_it.model.Login;
import com.perfekxia.collekt_it.model.LoginRequest;
import com.perfekxia.collekt_it.preference.PrefManager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private LoginViewModel loginViewModel;

    TextInputEditText edtPassword;
    TextInputEditText edtUsername;
    TextView txtError;
    AppCompatButton btnLogin;
    boolean value = true;

    PrefManager prefManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.init(this);

        prefManager = new PrefManager(this);

        initView();

        intitListner();

    }

    private void initView(){
        edtPassword = findViewById(R.id.passwordField);
        edtUsername = findViewById(R.id.usernameField);
        btnLogin = findViewById(R.id.btnLogin);
        txtError = findViewById(R.id.txtError);
    }

    private void intitListner(){
        edtPassword.setOnTouchListener(this);
        edtUsername.setOnTouchListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                Login login =loginViewModel.login(new LoginRequest(edtUsername.getText().toString().trim(), edtPassword.getText().toString().trim()));
                if (login != null){

                    Date now = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String snow = format.format(now);
                    prefManager.setLoginDate(snow);
                    prefManager.setLogin(login);
                    prefManager.setConnexion(true);
                    launchHomePage();
                    Log.i("login", login.toString());
                }else {
                    txtError.setVisibility(View.VISIBLE);
                    txtError.setText(R.string.login_error);
                }

                break;
        }
    }

    private void launchHomePage() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (view.getId()){
            case R.id.passwordField:
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(edtPassword.getCompoundDrawables()[2]!=null){
                        if(event.getX() >= (edtPassword.getRight()- edtPassword.getLeft() - edtPassword.getCompoundDrawables()[2].getBounds().width())) {
                            if (value)
                            {
                                // Show Password
                                edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                value = false;
                            }
                            else
                            {
                                // Hide Password
                                value = true;
                                edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            }
                        }
                    }
                    edtPassword.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.blue)));
                }
                break;
            case R.id.usernameField:
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    edtPassword.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.black_overlay)));
                }
                break;
        }
        return false;
    }
}
