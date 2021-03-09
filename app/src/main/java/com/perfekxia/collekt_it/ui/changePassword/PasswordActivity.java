package com.perfekxia.collekt_it.ui.changePassword;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.perfekxia.collekt_it.R;
import com.perfekxia.collekt_it.model.Login;
import com.perfekxia.collekt_it.model.LoginRequest;
import com.perfekxia.collekt_it.preference.PrefManager;
import com.perfekxia.collekt_it.ui.login.LoginViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PasswordActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText nouveau, ancien, pin, passwordConf;
    Button validerChange;
    TextView txtError;
    ScrollView scrollview;

    TextInputLayout inputLayoutPasswordNew, inputLayoutPin, inputLayoutPasswordFormer,
            inputLayoutPasswordConf;

    PrefManager prefManager;

    private PasswordViewModel viewModel;

    private int type_checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        viewModel = new ViewModelProvider(this).get(PasswordViewModel.class);
        viewModel.init(this);

        initView();
        initListner();

        prefManager = new PrefManager(this);

        RadioGroup rg =  findViewById(R.id.radio);

        hidePassworView();

        scrollview.setVerticalScrollBarEnabled(false);

    }

    private  void initView(){
        nouveau = findViewById(R.id.passwordNew);
        ancien = findViewById(R.id.passwordFormer);
        pin = findViewById(R.id.pin);
        passwordConf = findViewById(R.id.passwordConf);
        validerChange = findViewById(R.id.validerChange);
        txtError = findViewById(R.id.txtError);

        inputLayoutPasswordNew = findViewById(R.id.inputLayoutPasswordNew);
        inputLayoutPin = findViewById(R.id.inputLayoutPin);
        inputLayoutPasswordFormer = findViewById(R.id.inputLayoutPasswordFormer);
        inputLayoutPasswordConf = findViewById(R.id.inputLayoutPasswordConf);

        scrollview = findViewById(R.id.scrollview);
    }

    private void initListner(){
        validerChange.setOnClickListener(this);
    }

    private void validPasswordReset(){

        if (type_checked == 1){

            if(TextUtils.isEmpty(nouveau.getText().toString())){
                nouveau.setError("Ce champ ne doit pas etre vide");
                return;
            }
            if(TextUtils.isEmpty(ancien.getText().toString())){
                ancien.setError("Ce champ ne doit pas etre vide");
                return;
            }

            String newpass = nouveau.getText().toString().trim();
            String newpassCon = passwordConf.getText().toString().trim();
            String current = ancien.getText().toString().trim();
            if (newpass.equals(newpassCon)){
                if (prefManager.getLogin().getMotDePasse().equals(ancien.getText().toString())){
                    Log.i("ancien", ancien.getText().toString());
                    viewModel.updateLoginPass(newpass, current);
                    finish();
                }else {
                    txtError.setVisibility(View.VISIBLE);
                    txtError.setText(R.string.error_incorrect_pass);
                }
            }else {
                txtError.setVisibility(View.VISIBLE);
                txtError.setText(R.string.error_incorrect_conf);
            }

        } else  if (type_checked == 2){
            if(TextUtils.isEmpty(pin.getText().toString())){
                pin.setError("Ce champ ne doit pas etre vide");
                return;
            }
            if(TextUtils.isEmpty(ancien.getText().toString())){
                ancien.setError("Ce champ ne doit pas etre vide");
                return;
            }

            String current = ancien.getText().toString().trim();
            String pint = pin.getText().toString().trim();
            if (prefManager.getLogin().getMotDePasse().equals(ancien.getText().toString())){
                Log.i("ancien", ancien.getText().toString());
                viewModel.updateLoginPin(current, pint);
                finish();
            }else {
                txtError.setVisibility(View.VISIBLE);
                txtError.setText(R.string.error_incorrect_pass);
            }
        }


    }

    private void hidePassworView(){
        inputLayoutPasswordNew.setVisibility(View.GONE);
        inputLayoutPin.setVisibility(View.GONE);
        inputLayoutPasswordFormer.setVisibility(View.GONE);
        validerChange.setVisibility(View.GONE);
        inputLayoutPasswordConf.setVisibility(View.GONE);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioPassword:
                if (checked){
                    type_checked = 1;

                    inputLayoutPasswordFormer.setVisibility(View.VISIBLE);
                    inputLayoutPasswordNew.setVisibility(View.VISIBLE);
                    inputLayoutPasswordConf.setVisibility(View.VISIBLE);
                    inputLayoutPin.setVisibility(View.GONE);
                    validerChange.setVisibility(View.VISIBLE);
                    Log.i("passwor", "Ok");
                }
                break;
            case R.id.radioPin:
                if (checked){
                    type_checked = 2;
                    inputLayoutPasswordNew.setVisibility(View.GONE);
                    inputLayoutPasswordFormer.setVisibility(View.VISIBLE);
                    inputLayoutPin.setVisibility(View.VISIBLE);
                    validerChange.setVisibility(View.VISIBLE);
                    inputLayoutPasswordConf.setVisibility(View.GONE);
                    Log.i("pin", "Ok");
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.validerChange:
                validPasswordReset();
                break;
        }
    }
}
