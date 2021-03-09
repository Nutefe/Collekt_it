package com.perfekxia.collekt_it.ui.recuperation;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.perfekxia.collekt_it.R;
import com.perfekxia.collekt_it.adapter.ClientAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecuperationFragment extends Fragment {
    private ClientAdapter cltAdapter;
    RecyclerView recyclerView;
    private List<String> users = new ArrayList<>();
    private RecuperationViewModel recuperationViewModel;
    public static TextView error,errorAdmin;
    public static ProgressBar progressBar;
    private EditText adminPin;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recuperation, container, false);
        error = root.findViewById(R.id.errorTextRecuperation);
        progressBar = root.findViewById(R.id.myprogressBarRecuperation);
        Button recupere = root.findViewById(R.id.recupererDonnees);
        Button recupereTout = root.findViewById(R.id.recupererToutesDonnees);


        recuperationViewModel = new ViewModelProvider(getActivity()).get(RecuperationViewModel.class);

        recupere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recuperationViewModel.init(getContext(),getActivity().getApplication());
            }
        });

        recupereTout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater li = LayoutInflater.from(getContext());
                View view1 = li.inflate(R.layout.pop_up_saisir_adminpin, null);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
               EditText myadminPin = view1.findViewById(R.id.saisirAdminPin);
                errorAdmin = view1.findViewById(R.id.errorTextAdminPin);
                alertDialogBuilder.setView(view1).setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(TextUtils.isEmpty(myadminPin.getText())){
                            errorAdmin.setText("Veuillez remplir le champ");
                        }else {
                            Log.i("the admin", myadminPin.getText().toString());
                            recuperationViewModel.checkAdminPin(Integer.parseInt(myadminPin.getText().toString()),getContext(),getActivity().getApplication());
                        }
                    }
                }).setNegativeButton("Annuler",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                alertDialogBuilder.create();
                alertDialogBuilder.show();
//                ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//                boolean isWifiConn = false;
//                boolean isMobileConn = false;
//                for (Network network : connMgr.getAllNetworks()) {
//                    NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
//                    if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
//                        isWifiConn |= networkInfo.isConnected();
//                        if(isWifiConn==true){
//
//                        }
//                    }
//                    if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
//                        isMobileConn |= networkInfo.isConnected();
//                        error.setText(R.string.error_no_data);
//                    }
//                }

            }
        });
        return root;
    }
}