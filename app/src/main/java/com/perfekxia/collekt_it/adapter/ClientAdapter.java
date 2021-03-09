package com.perfekxia.collekt_it.adapter;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.perfekxia.collekt_it.R;
import com.perfekxia.collekt_it.helper.GpsCoordinates;
import com.perfekxia.collekt_it.model.ClientView;
import com.perfekxia.collekt_it.model.Cycles;
import com.perfekxia.collekt_it.preference.PrefManager;
import com.perfekxia.collekt_it.ui.creerCycle.CreerCycleActivity;
import com.perfekxia.collekt_it.ui.effectuerCollecte.EffectuerCollecteActivity;
import com.perfekxia.collekt_it.ui.listeCycles.ListeCyclesActivity;
import com.perfekxia.collekt_it.viewModel.CycleViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ViewHolder> {
    private Application application;
    private final LayoutInflater mInflater;
    private List<ClientView> mClientView; // Cached copy of words
    private static Context context;
    private CycleViewModel cycleViewModel;
    GpsCoordinates gpsCoordinates;
    Location location;

    PrefManager prefManager;



   public ClientAdapter(Context context,Application application1) {
       mInflater = LayoutInflater.from(context);
       this.application = application1;
   }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.client_item, viewGroup, false);
        context = viewGroup.getContext();
        prefManager = new PrefManager(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mClientView != null) {
            ClientView current = mClientView.get(position);
            holder.txtNumber.setText(current.getIdClient());
            holder.txtNom.setText(current.getNom());
            holder.txtPrenom.setText(current.getPrenom());
        } else {
            // Covers the case of data not being ready yet.
            holder.txtNumber.setText("");
            holder.txtNom.setText("");
            holder.txtPrenom.setText("");
        }
    }

    public void setClientView(List<ClientView> clientView){
        mClientView = clientView;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mClientView != null)
            return mClientView.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNumber, txtNom,txtPrenom;
        ClientView clientView;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            txtNumber = itemView.findViewById(R.id.cltNumber);
            txtNom = itemView.findViewById(R.id.cltNom);
            txtPrenom = itemView.findViewById(R.id.cltPrenom);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                         clientView = mClientView.get(pos);
                        if (prefManager.getLogin().getRole().equals("Administrateur")){
                            Intent intent = new Intent(context, ListeCyclesActivity.class);
                            intent.putExtra("IDCOMPTE", clientView.IdClient);
                            context.startActivity(intent);
                        } else {
                            gpsCoordinates = new GpsCoordinates(context);
                            location = gpsCoordinates.getCoordinates();
                            cycleViewModel = new CycleViewModel(context, application,clientView.IdClient);
                            cycleViewModel.getCycleByAccount().observe((LifecycleOwner) context,new Observer<Cycles>() {
                                @Override
                                public void onChanged(@Nullable Cycles c) {
                                    if (c != null){
                                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                        String strDateDebut = dateFormat.format(c.getDateDebut());
                                        String strDateFin = dateFormat.format(c.getDateFin());
                                        Log.d("Le cycle correspondant",c.toString());
                                        Intent activity2Intent = new Intent(context, EffectuerCollecteActivity.class);
                                        activity2Intent.putExtra("EXTRA_CLIENTVIEW",clientView);
                                        activity2Intent.putExtra("EXTRA_NUMERO_CYCLE",c.getNumeroCycle());
                                        activity2Intent.putExtra("EXTRA_DATE_DEBUT",strDateDebut);
                                        activity2Intent.putExtra("EXTRA_DATE_FIN",strDateFin);
                                        context.startActivity(activity2Intent);
                                    }else {
                                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                                        builder.setTitle("Ce client ne dispose pas de cycle");
                                        builder.setMessage("Souhaitez-vous lui en créer?");
                                        builder.setPositiveButton(R.string.pop_valider, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent activity2Intent = new Intent(context, CreerCycleActivity.class);
                                                activity2Intent.putExtra("EXTRA_CLIENTVIEW",clientView);
                                                context.startActivity(activity2Intent);
                                            }
                                        }).setNegativeButton(R.string.pop_cancel, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        });
                                        builder.create();
                                        builder.show();
//                                     LayoutInflater li = LayoutInflater.from(context);
//                                     View view = li.inflate(R.layout.pop_up_creer_cycle, null);
//                                     final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
//                                     alertDialogBuilder.setView(view)
//                                             .setPositiveButton(R.string.pop_valider, new DialogInterface.OnClickListener() {
//                                                 @Override
//                                                 public void onClick(DialogInterface dialog, int id) {
//
//                                                 }
//                                             })
//                                             .setNegativeButton(R.string.pop_cancel, new DialogInterface.OnClickListener() {
//                                                 public void onClick(DialogInterface dialog, int id) {
//                                                 }
//                                             });
//                                     alertDialogBuilder.create();
//                                     alertDialogBuilder.show();
                                    }
                                }
                            });
                        }

                    }
                }
            });
        }
    }
}
