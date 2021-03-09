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

public class CycleAdapter extends RecyclerView.Adapter<CycleAdapter.ViewHolder> {
    private Application application;
    private final LayoutInflater mInflater;
    private List<Cycles> mCycles;
    private Context context;
    private RetraitViewModel retraitViewModel;
    private CycleViewModel cycleViewModel;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    PrefManager prefManager;

    TextView montantRetrait,montantCollecte,montantMise;
    Integer mntM =0;
    Integer mntC =0;
    Integer mntR=0;

    public CycleAdapter(Context context,Application application1) {
        mInflater = LayoutInflater.from(context);
        this.application = application1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cycle_item, viewGroup, false);
        this.context = viewGroup.getContext();
        prefManager = new PrefManager(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mCycles != null) {
            Cycles current = mCycles.get(position);
            String strDateDebut = format.format(current.getDateDebut());
            String strDateFin = format.format(current.getDateFin());
//            holder.txtNumberClient.setText(current.getIdClient());
            holder.txtDateDebut.setText(strDateDebut);
            holder.txtDateFin.setText(strDateFin);
            holder.txtNumeroCycle.setText(String.valueOf(current.getNumeroCycle()));
        } else {
            // Covers the case of data not being ready yet.
//            holder.txtNumberClient.setText("");
            holder.txtDateDebut.setText("");
            holder.txtDateFin.setText("");
            holder.txtNumeroCycle.setText("");
        }
    }

    public void setCycles(List<Cycles> cycles){
        mCycles = cycles;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCycles != null)
            return mCycles.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtDateDebut,txtDateFin,txtNumeroCycle;
        Cycles cycles;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

//            txtNumberClient = itemView.findViewById(R.id.cltNumberRetrait);
            txtDateDebut = itemView.findViewById(R.id.dateDebutCycle);
            txtDateFin = itemView.findViewById(R.id.dateFinCycle);
            txtNumeroCycle = itemView.findViewById(R.id.numeroCycle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        cycles = mCycles.get(pos);

                        if (prefManager.getLogin().getRole().equals("Administrateur")){
                            Intent intent = new Intent(context, ListeCollecteActivity.class);
                            intent.putExtra("IDCYCLE", cycles.getNumeroCycle());
                            context.startActivity(intent);
                        } else {
                            LayoutInflater li = LayoutInflater.from(itemView.getContext());
                            View view = li.inflate(R.layout.pop_up_retrait, null);
                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(itemView.getContext());
                            montantRetrait = view.findViewById(R.id.montantRetrait);
                            montantCollecte = view.findViewById(R.id.sommeCRetrait);
                            montantMise = view.findViewById(R.id.sommeMiseRetrait);

                            loadSommeCollecte(context,cycles.getNumeroCycle(), new SommeCollecteCallbacks() {

                                @Override
                                public void sommeCResult(Integer sum) {
                                   montantCollecte.setText(String.valueOf(sum));
                                   loadSommeMise(context, cycles.getNumeroCycle(), new SommeMiseCallbacks() {
                                       @Override
                                       public void sommeMResult(Integer sumM) {
                                           montantMise.setText(String.valueOf(sumM));
                                           mntR = sum - sumM;
                                           montantRetrait.setText(String.valueOf(mntR));
                                       }

                                       @Override
                                       public void sommeMFailed(Throwable error) {
                                        montantRetrait.setText("0");
                                       }
                                   });
                                }

                                @Override
                                public void sommeCFailed(Throwable error) {
                                    montantRetrait.setText("0");
                                }
                            });

                            alertDialogBuilder.setView(view)
                                    .setPositiveButton(R.string.pop_valider, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
                                            Log.d("Montant retrait", String.valueOf(mntR));
                                            prefManager = new PrefManager(v.getContext());
                                            Calendar c = Calendar.getInstance();
                                            Date currentTime = c.getTime();
                                            Retraits retraits = new Retraits();
                                            retraits.setAnnee(currentTime.getYear());
                                            retraits.setCompteEp("");
                                            retraits.setDateRetrait(currentTime);
                                            retraits.setIdClient(cycles.getIdClient());
                                            retraits.setMoisRetrait(currentTime.getMonth());
                                            retraits.setMontantRetrait(mntR);
                                            retraits.setTypeRetrait("");
                                            retraits.setZoneT("");
                                            retraits.setNouveau(true);
                                            retraits.setRecupere(false);
                                            retraits.setIdUtilisateur(prefManager.getLogin().getNomUtilisateur());
                                            retraits.setRemboursement(false);
                                            retraits.setNumero(cycles.getNumeroCycle());
                                            Log.d("Retrait to save", retraits.toString());
                                                saveRetraits(retraits,v.getContext());
                                                cycles.setCloture(false);
                                                updateCycle(cycles,v.getContext());
                                        }
                                    })
                                    .setNegativeButton(R.string.pop_cancel, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                        }
                                    });
                            alertDialogBuilder.create();
                            alertDialogBuilder.show();
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        if (prefManager.getLogin().getRole().equals("Administrateur")){
                            Cycles cyclesInit = mCycles.get(pos);

                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                            builder.setTitle(R.string.text_validation_cycle);
                            builder.setMessage(R.string.text_valide_cycle);
                            builder.setPositiveButton(R.string.text_yes_disconnect, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    cyclesInit.setValide(true);
                                    cyclesInit.setNouveau(false);
                                    cycleViewModel  = new CycleViewModel(context, application, cyclesInit.getIdClient());
                                    Log.i("Cèycle valide", cyclesInit.toString());
                                    cycleViewModel.update(cyclesInit);
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
                    return false;
                }
            });
        }
    }

    public void saveRetraits(Retraits retraits,Context context){
        RetrofitClientInstance instance = new RetrofitClientInstance(context);
        GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
        Call<Retraits> call = service.saveRetraits(retraits);

        call.enqueue(new Callback<Retraits>() {
            @Override
            public void onResponse(Call<Retraits> call, Response<Retraits> response) {
                if (response.isSuccessful()){
                    Log.i("save Retraits:", response.message());
                } else {
                    Log.i("error Retraits:", response.message());
                }
            }

            @Override
            public void onFailure(Call<Retraits> call, Throwable t) {
//                Log.i("Error :", t.getMessage());
            }
        });
    }

    public interface SommeCollecteCallbacks{
        void sommeCResult(Integer sum);
        void sommeCFailed(Throwable error);
    }

    public interface SommeMiseCallbacks{
        void sommeMResult(Integer sumM);
        void sommeMFailed(Throwable error);
    }


    public void loadSommeCollecte(Context context,String idCycle,final SommeCollecteCallbacks callback){
        RetrofitClientInstance instance = new RetrofitClientInstance(context);
        GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
        Call<Integer> call = service.amountByCycleCollectes(idCycle);

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()){
                    Log.i("load Collectes:", response.message());
                    if (response.body()!=null){
                        mntC = response.body();
                        if (callback != null)
                            callback.sommeCResult(mntC);
                    }else {
                        montantCollecte.setText("0");
                    }
                } else {
                    Log.i("error Collectes:", response.message());
                }

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                if (callback != null)
                    callback.sommeCFailed(t);
//                Log.i("Error :", t.getMessage());
            }
        });
    }


    public Integer loadSommeMise(Context context,String idCycle,final SommeMiseCallbacks callbacks){
        RetrofitClientInstance instance = new RetrofitClientInstance(context);
        GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
        Call<Integer> call = service.amountByCycleMises(idCycle);

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()){
                    Log.i("load Mises:", response.message());
                    if (response.body()!=null){
                        mntM = response.body();
                        if (callbacks != null)
                            callbacks.sommeMResult(mntM);
                    }else {
                        montantMise.setText("0");
                    }
                } else {
                    Log.i("error Mises:", response.message());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                if (callbacks != null)
                    callbacks.sommeMFailed(t);
//                Log.i("Error :", t.getMessage());
            }
        });

        return mntM;
    }

    public void updateCycle(Cycles cycles,Context context){
        RetrofitClientInstance instance = new RetrofitClientInstance(context);
        GetDataService service = instance.getRetrofitInstance().create(GetDataService.class);
        Call<Cycles> call = service.updateCycle(cycles.getNumeroCycle(),cycles);

        call.enqueue(new Callback<Cycles>() {
            @Override
            public void onResponse(Call<Cycles> call, Response<Cycles> response) {
                if (response.isSuccessful()){
                    Log.i("update Cycles:", response.message());
                } else {
                    Log.i("error Cycles:", response.message());
                }
            }

            @Override
            public void onFailure(Call<Cycles> call, Throwable t) {
//                Log.i("Error :", t.getMessage());
            }
        });
    }
}
