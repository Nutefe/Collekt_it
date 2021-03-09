package com.perfekxia.collekt_it.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.perfekxia.collekt_it.model.Login;
import com.perfekxia.collekt_it.repository.LoginRepository;

import java.util.List;

public class LoginViewModel extends AndroidViewModel {
    private LoginRepository mRepository;

    private LiveData<List<Login>> mAllLogin;

    public LoginViewModel (Application application) {
        super(application);
        mRepository = new LoginRepository(application);
        mAllLogin = mRepository.getAllLogin();
    }

    LiveData<List<Login>> getAllLogin() { return mAllLogin; }

    public void insert(Login login) { mRepository.insert(login); }
}
