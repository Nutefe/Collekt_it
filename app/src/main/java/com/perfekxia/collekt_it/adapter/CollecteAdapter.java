package com.perfekxia.collekt_it.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.perfekxia.collekt_it.R;
import com.perfekxia.collekt_it.model.Collectes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class CollecteAdapter extends  RecyclerView.Adapter<CollecteAdapter.ViewHolder> {
    private final LayoutInflater mInflater;
    private List<Collectes> mCollecte; // Cached copy of words
    private static Context context;


    public CollecteAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.collecte_item, viewGroup, false);
        context = viewGroup.getContext();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        if (mCollecte != null) {
            Collectes current = mCollecte.get(i);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = dateFormat.format(current.getDateCollecte());
            holder.txtNumber.setText(current.getIdClient());
            holder.txtDate.setText(strDate);
            holder.txtMontant.setText(String.valueOf(current.getMontantCollecte()));
        } else {
            // Covers the case of data not being ready yet.
            holder.txtNumber.setText("");
            holder.txtDate.setText("");
            holder.txtMontant.setText("");
        }
    }

    public void setCollecte(List<Collectes> collecte){
        mCollecte = collecte;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCollecte != null)
            return mCollecte.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNumber, txtDate,txtMontant;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            txtNumber = itemView.findViewById(R.id.cltNumber);
            txtDate = itemView.findViewById(R.id.dateCollecte);
            txtMontant = itemView.findViewById(R.id.montantCollecte);

        }
    }
}