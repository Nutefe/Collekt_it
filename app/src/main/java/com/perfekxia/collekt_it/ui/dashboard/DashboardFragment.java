package com.perfekxia.collekt_it.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.perfekxia.collekt_it.R;
import com.perfekxia.collekt_it.model.ClientView;
import com.perfekxia.collekt_it.model.Collectes;
import com.perfekxia.collekt_it.model.Comptes;
import com.perfekxia.collekt_it.model.Cycles;
import com.perfekxia.collekt_it.preference.PrefManager;
import com.perfekxia.collekt_it.ui.clientsVisites.ClientsVisitesActivity;
import com.perfekxia.collekt_it.ui.listeClients.ListeClientsActivity;
import com.perfekxia.collekt_it.ui.listeCollectes.ListeCollecteActivity;
import com.perfekxia.collekt_it.ui.listeEquipements.EquipementsFragment;
import com.perfekxia.collekt_it.ui.listescomptespourretraits.ListeComptesRetraitsActivity;
import com.perfekxia.collekt_it.viewModel.ClientViewViewModel;
import com.perfekxia.collekt_it.viewModel.CollecteViewModel;
import com.perfekxia.collekt_it.viewModel.CompteViewModel;
import com.perfekxia.collekt_it.viewModel.CycleViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DashboardFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{

    private DashboardViewModel dashboardViewModel;
    private DashboardViewModel dashboardViewModel1;
    private CompteViewModel compteViewModel;
    private CollecteViewModel collecteViewModel;
    private CycleViewModel cycleViewModel;
    private ClientViewViewModel clientViewViewModel;
    private List<ClientView> mCltView;

    public static TextView error;
    CardView colAjd, cltVisites, cltListe;
    Button buttonSync,buttonRetr, syncDonneesDonne;

    PrefManager prefManager;

    SwipeRefreshLayout container;

    View viewRoot;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        prefManager = new PrefManager(root.getContext());
        initView(root);
        initListner();

        this.viewRoot = root;

        mCltView = new ArrayList<ClientView>();
        dashboardViewModel1 = new DashboardViewModel();
        compteViewModel = new CompteViewModel(getActivity().getApplication());
        cycleViewModel = new CycleViewModel(getContext(), getActivity().getApplication(), "A");
        dashboardViewModel1.recupererTout(getContext(),getActivity().getApplication());

        loadView();

        compteViewModel.getAllCompte().observe(getViewLifecycleOwner(), new Observer<List<Comptes>>() {
            @Override
            public void onChanged(List<Comptes> comptes) {
                collecteViewModel = new CollecteViewModel(getActivity().getApplication(),"A");
                collecteViewModel.getAllNewCollecte().observe(getViewLifecycleOwner(), new Observer<List<Collectes>>() {
                    @Override
                    public void onChanged(List<Collectes> collectes) {
                        List<Collectes> col =new ArrayList<Collectes>();
                        for(int i=0;i<collectes.size();i++){
                            if(collectes.get(i).getDateCollecte().getDay()== Calendar.getInstance().getTime().getDay()){
                                col.add(collectes.get(i));
                            }
                        }

                        collecteViewModel.getmListeDistinctsCollectes().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
                            @Override
                            public void onChanged(List<String> strings) {
                                dashboardViewModel = new DashboardViewModel(getContext(),getActivity().getApplication(),comptes.size() ,col.size(),strings.size());
                                final TextView textViewCltVisites = root.findViewById(R.id.nombreCltVisites);
                                dashboardViewModel.getNombreClientsVisites().observe(getViewLifecycleOwner(), new Observer<Integer>() {
                                    @Override
                                    public void onChanged(Integer integer) {
                                        textViewCltVisites.setText(String.valueOf(integer));
                                    }
                                });
                                final TextView textView = root.findViewById(R.id.nombreClient);
                                dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<Integer>() {
                                    @Override
                                    public void onChanged(@Nullable Integer s) {
                                        textView.setText(String.valueOf(comptes.size()));
                                    }
                                });
                                final TextView textView1 = root.findViewById(R.id.nombreDeCollectes);
                                dashboardViewModel.getNombreCollectes().observe(getViewLifecycleOwner(), new Observer<Integer>() {
                                    @Override
                                    public void onChanged(Integer integer) {
                                        textView1.setText(String.valueOf(col.size()));
                                    }
                                });
                                if(col.size()!=0){
                                    buttonSync.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue)));
                                    buttonSync.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                                            boolean isWifiConn = false;
                                            boolean isMobileConn = false;

                                            DashboardViewModel dashboardViewModel = new DashboardViewModel(getContext(), getActivity().getApplication(),0,0,0);
                                            dashboardViewModel.synchronisaton(getContext(),getActivity().getApplication());

//                                            for (Network network : connMgr.getAllNetworks()) {
//                                                NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
//                                                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
//                                                    isWifiConn |= networkInfo.isConnected();
//                                                    if(isWifiConn==true){
//                                                        DashboardViewModel dashboardViewModel = new DashboardViewModel(getContext(), getActivity().getApplication(),0,0,0);
//                                                        dashboardViewModel.synchronisaton(getContext(),getActivity().getApplication());
//                                                    }else {
//                                                        error.setText(R.string.error_no_data);
//                                                    }
//                                                }
//                                                if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
//                                                    isMobileConn |= networkInfo.isConnected();
//                                                    error.setText(R.string.error_no_data);
//                                                }
//                                            }
                                        }
                                    });
                                }else if(strings.size()==0){
                                    buttonSync.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            error.setText(R.string.error_no_data_for_sync);
                                        }
                                    });
                                }
                            }
                        });
                    }
                });
            }
        });

//        onResume();
        return root;
    }

    private void initView(View root){
        error = root.findViewById(R.id.errorTextSynchronisation);
        colAjd = root.findViewById(R.id.cdCollectes);
        cltVisites = root.findViewById(R.id.cltVisites);
        cltListe = root.findViewById(R.id.cltListe);
        buttonSync = root.findViewById(R.id.syncDonnees);
        container = root.findViewById(R.id.contener);
        buttonRetr = root.findViewById(R.id.effectuerRetrait);
        syncDonneesDonne = root.findViewById(R.id.syncDonneesDonne);
//        listeEquipements = root.findViewById(R.id.listeEquipements);
    }

    private void loadView(){
        if (prefManager.getLogin().getRole().equals("Administrateur")){
            buttonRetr.setVisibility(View.VISIBLE);
            syncDonneesDonne.setVisibility(View.VISIBLE);
//            listeEquipements.setVisibility(View.VISIBLE);
            buttonSync.setVisibility(View.GONE);
            cycleViewModel.getmAllCycleValideNew().observe(getViewLifecycleOwner(), new Observer<List<Cycles>>() {
                @Override
                public void onChanged(List<Cycles> cycles) {
                    Log.i("cycles valide", cycles.toString());

                    if (cycles.size() > 0){
                        syncDonneesDonne.setEnabled(true);
                        syncDonneesDonne.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue)));
                    } else {
                        syncDonneesDonne.setEnabled(false);
                        syncDonneesDonne.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorBorder)));
                    }
                }
            });

        } else {
            buttonSync.setVisibility(View.VISIBLE);
            syncDonneesDonne.setVisibility(View.GONE);
        }

    }

    private void initListner(){
        container.setOnRefreshListener(this);
        colAjd.setOnClickListener(this);
        cltVisites.setOnClickListener(this);
        cltListe.setOnClickListener(this);
        buttonRetr.setOnClickListener(this);
        syncDonneesDonne.setOnClickListener(this);
//        listeEquipements.setOnClickListener(this);
    }

    private void  updateCycles(){
        cycleViewModel.getmAllCycleValideNew().observe(getViewLifecycleOwner(), new Observer<List<Cycles>>() {
            @Override
            public void onChanged(List<Cycles> cycles) {
                if (cycles.size() > 0){
                    for (Cycles cycles1 :
                            cycles) {
                        Log.i("save cycle", cycles1.toString());
                        cycleViewModel.updateCycle(cycles1);
                    }
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                dashboardViewModel1.init(getContext(),getActivity().getApplication());
                container.setRefreshing(false);
            }
        }, 10);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cdCollectes:
                Intent activity2Intent = new Intent(getActivity().getApplicationContext(), ListeCollecteActivity.class);
                startActivity(activity2Intent);
                break;
            case R.id.cltVisites:
                Intent activityclient = new Intent(getActivity().getApplicationContext(), ClientsVisitesActivity.class);
                startActivity(activityclient);
                break;
            case R.id.cltListe:
                Intent activityListe = new Intent(getActivity().getApplicationContext(), ListeClientsActivity.class);
                startActivity(activityListe);
                break;
            case R.id.effectuerRetrait:
                Intent activityL = new Intent(getActivity().getApplicationContext(), ListeComptesRetraitsActivity.class);
                startActivity(activityL);
                break;
            case R.id.syncDonneesDonne:
                    updateCycles();
                break;
//            case R.id.listeEquipements:
//                Intent activityEqui = new Intent(getActivity().getApplicationContext(), EquipementsFragment.class);
//                startActivity(activityEqui);
//                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        compteViewModel.getAllCompte().observe(getViewLifecycleOwner(), new Observer<List<Comptes>>() {
            @Override
            public void onChanged(List<Comptes> comptes) {
                collecteViewModel = new CollecteViewModel(getActivity().getApplication(),"A");
                collecteViewModel.getAllNewCollecte().observe(getViewLifecycleOwner(), new Observer<List<Collectes>>() {
                    @Override
                    public void onChanged(List<Collectes> collectes) {
                        List<Collectes> col =new ArrayList<Collectes>();
                        for(int i=0;i<collectes.size();i++){
                            if(collectes.get(i).getDateCollecte().getDay()== Calendar.getInstance().getTime().getDay()){
                                col.add(collectes.get(i));
                            }
                        }

                        collecteViewModel.getmListeDistinctsCollectes().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
                            @Override
                            public void onChanged(List<String> strings) {
                                dashboardViewModel = new DashboardViewModel(getContext(),getActivity().getApplication(),comptes.size() ,col.size(),strings.size());
                                final TextView textViewCltVisites = viewRoot.findViewById(R.id.nombreCltVisites);
                                dashboardViewModel.getNombreClientsVisites().observe(getViewLifecycleOwner(), new Observer<Integer>() {
                                    @Override
                                    public void onChanged(Integer integer) {
                                        textViewCltVisites.setText(String.valueOf(integer));
                                    }
                                });
                                final TextView textView = viewRoot.findViewById(R.id.nombreClient);
                                dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<Integer>() {
                                    @Override
                                    public void onChanged(@Nullable Integer s) {
                                        textView.setText(String.valueOf(s));
                                    }
                                });
                                final TextView textView1 = viewRoot.findViewById(R.id.nombreDeCollectes);
                                dashboardViewModel.getNombreCollectes().observe(getViewLifecycleOwner(), new Observer<Integer>() {
                                    @Override
                                    public void onChanged(Integer integer) {
                                        textView1.setText(String.valueOf(col.size()));
                                    }
                                });
                                if(col.size()!=0){
                                    buttonSync.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue)));
                                    buttonSync.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                                            boolean isWifiConn = false;
                                            boolean isMobileConn = false;

                                            DashboardViewModel dashboardViewModel = new DashboardViewModel(getContext(), getActivity().getApplication(),0,0,0);
                                            dashboardViewModel.synchronisaton(getContext(),getActivity().getApplication());

                                        }
                                    });
                                }else if(strings.size()==0){
                                    buttonSync.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            error.setText(R.string.error_no_data_for_sync);
                                        }
                                    });
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    private void refreshFragment(){
        Fragment selectedFragment = new DashboardFragment();
//        Bundle bundleAcceuil = new Bundle();
//        selectedFragment.setArguments(bundleAcceuil);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.detach(selectedFragment);
        transaction.attach(selectedFragment);
//        transaction.replace(R.id.frame, selectedFragment);
        transaction.commit();
    }
}