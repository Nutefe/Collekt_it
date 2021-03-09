package com.perfekxia.collekt_it.sql;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.perfekxia.collekt_it.dao.AffectationDao;
import com.perfekxia.collekt_it.dao.ClientDao;
import com.perfekxia.collekt_it.dao.ClientViewDao;
import com.perfekxia.collekt_it.dao.CollecteDao;
import com.perfekxia.collekt_it.dao.CompteDao;
import com.perfekxia.collekt_it.dao.CycleDao;
import com.perfekxia.collekt_it.dao.EquipementDao;
import com.perfekxia.collekt_it.dao.LocalisationsDao;
import com.perfekxia.collekt_it.dao.LoginDao;
import com.perfekxia.collekt_it.dao.MiseDao;
import com.perfekxia.collekt_it.dao.ProduitDao;
import com.perfekxia.collekt_it.dao.RetraitDao;
import com.perfekxia.collekt_it.dao.TitulaireDao;
import com.perfekxia.collekt_it.dao.TitulaireViewDao;
import com.perfekxia.collekt_it.dao.UtilisateurDao;
import com.perfekxia.collekt_it.dao.ZoneDao;
import com.perfekxia.collekt_it.helper.Converters;
import com.perfekxia.collekt_it.model.Affectations;
import com.perfekxia.collekt_it.model.Clients;
import com.perfekxia.collekt_it.model.ClientView;
import com.perfekxia.collekt_it.model.Collectes;
import com.perfekxia.collekt_it.model.Comptes;
import com.perfekxia.collekt_it.model.Cycles;
import com.perfekxia.collekt_it.model.Equipements;
import com.perfekxia.collekt_it.model.Localisations;
import com.perfekxia.collekt_it.model.Login;
import com.perfekxia.collekt_it.model.Mises;
import com.perfekxia.collekt_it.model.Produits;
import com.perfekxia.collekt_it.model.Retraits;
import com.perfekxia.collekt_it.model.Titulaires;
import com.perfekxia.collekt_it.model.TitulaireView;
import com.perfekxia.collekt_it.model.Utilisateurs;
import com.perfekxia.collekt_it.model.Zones;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Database(
        entities = {Affectations.class,
                Clients.class,
                Collectes.class,
                Comptes.class,
                Cycles.class,
                Equipements.class,
                Login.class, Mises.class,
                Produits.class,
                Retraits.class,
                Titulaires.class,
                Utilisateurs.class,
                Zones.class,
                Localisations.class},
        views = {ClientView.class, TitulaireView.class}, version = 1,exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract AffectationDao affectationDao();
    public abstract ClientDao clientDao();
    public abstract CollecteDao collecteDao();
    public abstract CompteDao compteDao();
    public abstract CycleDao cycleDao();
    public abstract EquipementDao equipementDao();
    public abstract LoginDao loginDao();
    public abstract MiseDao miseDao();
    public abstract ProduitDao produitDao();
    public abstract RetraitDao retraitDao();
    public abstract TitulaireDao titulaireDao();
    public abstract UtilisateurDao utilisateurDao();
    public abstract ZoneDao zoneDao();
    public abstract ClientViewDao clientViewDao();
    public abstract TitulaireViewDao titulaireViewDao();
    public abstract LocalisationsDao localisationsDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .addCallback(sRoomDatabaseCallback)
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static Callback sRoomDatabaseCallback =
            new Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };
    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ClientDao mcltDao;
        private final CompteDao mcmpDao;
        private final ZoneDao zoneDao;
        private final ProduitDao produitDao;


        PopulateDbAsync(AppDatabase db) {
            mcltDao = db.clientDao();
            mcmpDao = db.compteDao();
            zoneDao = db.zoneDao();
            produitDao= db.produitDao();

        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
            Date date = new Date();
            String dtStart = "2020-07-16T09:27:37Z";
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            try {
                 date = format.parse(dtStart);
            } catch (ParseException e) {
                e.printStackTrace();
            }

//            mcltDao.deleteAllClient();
//            mcmpDao.deleteAllCompte();
//            zoneDao.deleteAllZones();

//            Clients clients1 = new Clients();
//            clients1.setIdClient("Clt001");
//            clients1.setNom("Agamah");
//            clients1.setPrenom("Zith");
//            clients1.setSexe("M");
//            clients1.setProfession("Commercial");
//            clients1.setZoneClient("Zn001");
//            clients1.setAdresse("Adidogome");
//            clients1.setMessagerie("");
//            clients1.setTelephone("95246587");
//            clients1.setDateAdhesion(date);
//            clients1.setPAP("");
//            clients1.setPhoto("");
//            clients1.setTelephonePAP("95246587");
//            clients1.setCompteEp("");
//            clients1.setRecupere(false);
//            clients1.setNouveau(true);
//
//            mcltDao.insertClient(clients1);
//
//            Comptes comptes1 = new Comptes();
//            comptes1.setIdCompte("Cmp001");
//            comptes1.setIdClient("Clt001");
//            comptes1.setDateOuverture(date);
//            comptes1.setIdProduit("Prod001");
//            comptes1.setCarnetC("");
//            comptes1.setRecupere(false);
//            comptes1.setNouveau(true);
//
//            Comptes comptes2 = new Comptes();
//            comptes2.setIdCompte("Cmp002");
//            comptes2.setIdClient("Clt001");
//            comptes2.setDateOuverture(date);
//            comptes2.setIdProduit("Prod002");
//            comptes2.setCarnetC("");
//            comptes2.setRecupere(false);
//            comptes2.setNouveau(true);
//
//            mcmpDao.insertCompte(comptes1);
//            mcmpDao.insertCompte(comptes2);
//
//
//            Produits produits = new Produits();
//            produits.setIdProduit("Prod001");
//            produits.setDateDebut(date);
//            produits.setLibelle("Compte épargne");
//            produits.setDescription("Compte épargne");
//            produits.setNouveau(true);
//            produits.setRecupere(false);
//
//            Produits produits1 = new Produits();
//            produits1.setIdProduit("Prod002");
//            produits1.setDateDebut(date);
//            produits1.setLibelle("Compte courant");
//            produits1.setDescription("Compte courant");
//            produits1.setNouveau(true);
//            produits1.setRecupere(false);

//            produitDao.insertProduit(produits);
//            produitDao.insertProduit(produits1);


            Zones zones = new Zones();
            zones.setDateJ(date);
            zones.setIdZone("Zn001");
            zones.setNomZone("Zone Adidogome");
            zones.setNouveau(true);
            zones.setQuartier("Adidogome");
            zones.setRecupere(false);

            Zones zones1 = new Zones();
            zones1.setDateJ(date);
            zones1.setIdZone("Zn002");
            zones1.setNomZone("Zone Agoe");
            zones1.setNouveau(true);
            zones1.setQuartier("Agoe");
            zones1.setRecupere(false);
//            zoneDao.insertZone(zones);
//            zoneDao.insertZone(zones1);
            return null;
        }
    }

}

