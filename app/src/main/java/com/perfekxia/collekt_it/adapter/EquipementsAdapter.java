package com.perfekxia.collekt_it.adapter;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.perfekxia.collekt_it.R;
import com.perfekxia.collekt_it.api_manager.GetDataService;
import com.perfekxia.collekt_it.api_manager.RetrofitClientInstance;
import com.perfekxia.collekt_it.model.Cycles;
import com.perfekxia.collekt_it.model.Equipements;
import com.perfekxia.collekt_it.model.Retraits;
import com.perfekxia.collekt_it.preference.PrefManager;
import com.perfekxia.collekt_it.ui.listeCollectes.ListeCollecteActivity;
import com.perfekxia.collekt_it.viewModel.CycleViewModel;
import com.perfekxia.collekt_it.viewModel.RetraitViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EquipementsAdapter extends RecyclerView.Adapter<EquipementsAdapter.ViewHolder>{
    private Application application;
    private final LayoutInflater mInflater;
    private List<Equipements> mEquipements;
    private Context context;
    private RetraitViewModel retraitViewModel;
    private CycleViewModel cycleViewModel;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");



    public EquipementsAdapter(Context context, Application application1) {
        mInflater = LayoutInflater.from(context);
        this.application = application1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.equipements_item, viewGroup, false);
        this.context = viewGroup.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mEquipements != null) {
            Equipements current = mEquipements.get(position);
            String strDateDebut = format.format(current.getDateCreation());


            holder.txtDate.setText(strDateDebut);
            holder.txtId.setText(String.valueOf(current.getIdEquipement()));
            holder.txtNumeroEquipement.setText(String.valueOf(current.getNumeroEquipement()));
        } else {
            // Covers the case of data not being ready yet.
//            holder.txtNumberClient.setText("");
            holder.txtDate.setText("");
            holder.txtId.setText("");
            holder.txtNumeroEquipement.setText("");
        }
    }

    public void setEquipements(List<Equipements> equipements){
        mEquipements = equipements;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mEquipements != null)
            return mEquipements.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtDate,txtId,txtNumeroEquipement;
        Equipements equipements;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);


            txtDate = itemView.findViewById(R.id.dateAutorisation);
            txtId = itemView.findViewById(R.id.idEquipements);
            txtNumeroEquipement = itemView.findViewById(R.id.numeroEquipement);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                            Equipements equipementsInit = mEquipements.get(pos);

                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                            builder.setTitle("Ce utitlisateur n'a pas d'autorisation");
                            builder.setMessage("Souhaitez-vous l'autoriser?");
                            builder.setPositiveButton(R.string.text_yes_disconnect, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    equipementsInit.setAutorisation(true);
                                    updateEquipements(equipementsInit,view.getContext());
                                }
                            }).setNegativeButton(R.string.text_no_disconnect, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            builder.create();
                            builder.show();
                    }
                }
            });
        }
}

    public void updateEquipements(Equipements equipements,Context context){
        RetrofitClientInstance instance = new RetrofitClientInstance(context);
        GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
        Call<Equipements> call = service.updateAutEquipements(equipements.getIdEquipement(),equipements);

        call.enqueue(new Callback<Equipements>() {
            @Override
            public void onResponse(Call<Equipements> call, Response<Equipements> response) {
                if (response.isSuccessful()){
                    Log.i("update Equipements:", response.message());
                } else {
                    Log.i("error Equipements:", response.message());
                }
            }

            @Override
            public void onFailure(Call<Equipements> call, Throwable t) {
//                Log.i("Error :", t.getMessage());
            }
        });
    }
}
