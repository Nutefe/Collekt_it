package com.perfekxia.collekt_it.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.perfekxia.collekt_it.R;
import com.perfekxia.collekt_it.model.Equipements;
import com.perfekxia.collekt_it.model.Login;
import com.perfekxia.collekt_it.model.TitulaireView;
import com.perfekxia.collekt_it.model.Zones;
import com.perfekxia.collekt_it.preference.PrefManager;
import com.perfekxia.collekt_it.ui.slider.slide3.Slide3Fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserAdapter extends  RecyclerView.Adapter<UserAdapter.ViewHolder> implements Filterable {

    private List<TitulaireView> userList;
    private List<Zones> zonesList;
    private String roles;
    private Context context;

    private List<String> zones = new ArrayList<>();
    private List<Zones> zonesSpinner = new ArrayList<>();

    private String zone_id;
    private final LayoutInflater mInflater;

    private PrefManager prefManager;

    public UserAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
//        this.userList = userList;
//        this.zonesList = zonesLis;
//        this.roles = roles;
//        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_user, viewGroup, false);
//        context = viewGroup.getContext();
        prefManager = new PrefManager(context);
        return new ViewHolder(view);
    }

    public void setClientView(List<TitulaireView> titulaireViews){
        userList = titulaireViews;
        Log.i("user", userList.toString());
        notifyDataSetChanged();
    }

    public void setZoneView( List<Zones> zones, String roles){
        this.zonesList = zones;
        this.roles = roles;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        TitulaireView  user = userList.get(i);
        viewHolder.txtUsername.setText(user.LoginTitulaire);
        viewHolder.txtTitulaire.setText(user.FullNameTitulaires);
    }

    @Override
    public int getItemCount() {
        if (userList != null)
            return userList.size();
        else return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    List<TitulaireView> titulaireViews = userList;
                    userList = titulaireViews;
                } else {
                    List<TitulaireView> filteredList = new ArrayList<>();
                    for (TitulaireView row : userList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.FullNameTitulaires.toLowerCase().contains(charString.toLowerCase()) ||
                                row.LoginTitulaire.contains(charSequence) || row.OldID.contains(charSequence)){
                            filteredList.add(row);
                        }
                    }

                    userList = filteredList;
                }
                Log.i("userlist", userList.toString());
                FilterResults filterResults = new FilterResults();
                filterResults.values = userList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                userList = (ArrayList<TitulaireView>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtUsername, txtTitulaire;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtTitulaire = itemView.findViewById(R.id.txtTitulaire);


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (prefManager.getRole() !=null && !prefManager.getRole().equals("")) {
                            Slide3Fragment.textError.setVisibility(View.GONE);
                            int pos = getAdapterPosition();
                            if (pos != RecyclerView.NO_POSITION){
                                TitulaireView user = userList.get(pos);

                                LayoutInflater li = LayoutInflater.from(context);


                                if (prefManager.getRole().equals("Administrateur")){
                                    View view = li.inflate(R.layout.costum_pin_popup, null);

                                    TextView identifiantAc = view.findViewById(R.id.identifiantAc);
                                    TextView nomComplet = view.findViewById(R.id.nomComplet);
                                    TextView prenomAc = view.findViewById(R.id.prenomAc);

                                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                                    alertDialogBuilder.setView(view)
                                            .setPositiveButton(R.string.pop_valider, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int id) {

                                                    Login login = new Login();
                                                    login.setIdTitulaire(String.valueOf(user.OldID));
                                                    login.setActif(false);
                                                    login.setNomZone(user.TitulairesNomZ);
                                                    login.setMotDePasse(user.PassWordsTitulaire);
                                                    login.setNomComplet(user.FullNameTitulaires);
                                                    login.setNomUtilisateur(user.LoginTitulaire);
                                                    login.setNumeroEquipement(Build.ID);
                                                    login.setPin(user.TitulairesPin);
                                                    login.setRole(user.TitulairesRole);
                                                    login.setActif(user.Statut);
                                                    prefManager.setLogin(login);

                                                    Log.i("Login", prefManager.getLogin().toString());
                                                }
                                            })
                                            .setNegativeButton(R.string.pop_cancel, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
//                                                      LoginDialogFragment.this.getDialog().cancel();
                                                }
                                            });

                                    identifiantAc.setText(user.OldID);
                                    nomComplet.setText(user.FullNameTitulaires);
                                    prenomAc.setText(user.LoginTitulaire);

                                    alertDialogBuilder.create();
                                    alertDialogBuilder.show();
                                }else {
                                    View view = li.inflate(R.layout.costum_popup, null);

                                    TextView zone = view.findViewById(R.id.txtZone);
                                    TextView identifiantAc = view.findViewById(R.id.identifiantAc);
                                    TextView nomComplet = view.findViewById(R.id.nomComplet);
                                    TextView prenomAc = view.findViewById(R.id.prenomAc);
                                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                                    alertDialogBuilder.setView(view)
                                            .setPositiveButton(R.string.pop_valider, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int id) {

                                                    Date now = new Date();

                                                    Equipements equipements = new Equipements();
                                                    equipements.setAutorisation(false);
                                                    equipements.setEtat(false);
                                                    equipements.setNumeroEquipement(Build.ID);
                                                    equipements.setIdTitulaire(user.OldID);
                                                    equipements.setIdZone(zone_id);
                                                    equipements.setDateCreation(now);
                                                    prefManager.setEquipement(equipements);

                                                    Login login = new Login();
                                                    login.setIdTitulaire(String.valueOf(user.OldID));
                                                    login.setActif(false);
                                                    login.setIdZone(zone_id);
                                                    login.setNomZone(user.TitulairesNomZ);
                                                    login.setMotDePasse(user.PassWordsTitulaire);
                                                    login.setNomComplet(user.FullNameTitulaires);
                                                    login.setNomUtilisateur(user.LoginTitulaire);
                                                    login.setNumeroEquipement(Build.ID);
                                                    login.setPin(user.TitulairesPin);
                                                    login.setRole(user.TitulairesRole);
                                                    login.setActif(user.Statut);
                                                    prefManager.setLogin(login);
//                                                prefManager.deleteRole();
                                                    Log.i("Equipement", prefManager.getEquipement().toString());
                                                    Log.i("Login", prefManager.getLogin().toString());
                                                }
                                            })
                                            .setNegativeButton(R.string.pop_cancel, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
//                                              LoginDialogFragment.this.getDialog().cancel();
                                                }
                                            });

                                    identifiantAc.setText(user.OldID);
                                    nomComplet.setText(user.FullNameTitulaires);
                                    prenomAc.setText(user.LoginTitulaire);
                                    zone.setText(user.TitulairesNomZ);
                                    zone_id = user.ZoneTitulaires;
                                    alertDialogBuilder.create();
                                    alertDialogBuilder.show();
                                }

                            }

                        }else {
                            Slide3Fragment.textError.setVisibility(View.VISIBLE);
                            Slide3Fragment.textError.setText(R.string.error_select_type_user);
                        }

                    }
                });
            }

    }
}
