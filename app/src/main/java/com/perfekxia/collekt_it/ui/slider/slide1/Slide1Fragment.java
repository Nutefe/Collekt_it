package com.perfekxia.collekt_it.ui.slider.slide1;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.perfekxia.collekt_it.R;
import com.perfekxia.collekt_it.preference.PrefManager;
import com.perfekxia.collekt_it.ui.configuration.WelcomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Slide1Fragment extends Fragment {

//    @BindView(R.id.textInputEditTextAdresse)
    public TextInputEditText editText;
    public TextView txtError;
    private PrefManager prefManager;
    private static final String SERVER_ADDRESS = "ServerAddress";

    public Slide1Fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.welcome_slide1, container, false);

        initView(v);

        prefManager = new PrefManager(getContext());
        editText.setText("http://perfekxia-001-site7.itempurl.com");
        if (prefManager.checkKey(SERVER_ADDRESS)){
            editText.setText(prefManager.getServerAddress());
        }

        if (!TextUtils.isEmpty(editText.getText())){
            WelcomeActivity.btnNext.setVisibility(View.VISIBLE);
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0)
                    WelcomeActivity.btnNext.setVisibility(View.VISIBLE);
                else
                    WelcomeActivity.btnNext.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return v;
    }

    private  void initView(View v){
        txtError = v.findViewById(R.id.txtError);
        editText = v.findViewById(R.id.textInputEditTextAdresse);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void prid(){

    }

}
