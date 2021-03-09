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
import com.perfekxia.collekt_it.model.Comptes;
import com.perfekxia.collekt_it.model.Cycles;
import com.perfekxia.collekt_it.ui.creerCycle.CreerCycleActivity;
import com.perfekxia.collekt_it.ui.effectuerCollecte.EffectuerCollecteActivity;
import com.perfekxia.collekt_it.ui.effectuerRetrait.EffectuerRetraitActivity;
import com.perfekxia.collekt_it.viewModel.CycleViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class CompteAdapter extends RecyclerView.Adapter<CompteAdapter.ViewHolder>{
    private Application application;
    private final LayoutInflater mInflater;
    private List<Comptes> mComptes; // Cached copy of words
    private static Context context;




    public CompteAdapter(Context context,Application application1) {
        mInflater = LayoutInflater.from(context);
        this.application = application1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.compte_item, viewGroup, false);
        context = viewGroup.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mComptes != null) {
            Comptes current = mComptes.get(position);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = dateFormat.format(current.getDateOuverture());
            holder.txtNumber.setText(current.getIdCompte());
            holder.txtDate.setText(strDate);
        } else {
            // Covers the case of data not being ready yet.
            holder.txtNumber.setText("");
            holder.txtDate.setText("");
        }
    }

    public void setCompteView(List<Comptes> comptes){
        mComptes = comptes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mComptes != null)
            return mComptes.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNumber, txtDate;
        Comptes comptes;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            txtNumber = itemView.findViewById(R.id.accountNumber);
            txtDate = itemView.findViewById(R.id.dateOuverture);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        comptes = mComptes.get(pos);
                        Intent activity2Intent = new Intent(context, EffectuerRetraitActivity.class);
                        activity2Intent.putExtra("EXTRA_COMPTES",comptes.getIdCompte());
                        context.startActivity(activity2Intent);
                    }
                }
            });
        }
    }
}
