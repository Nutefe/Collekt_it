package com.perfekxia.collekt_it.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.perfekxia.collekt_it.model.Equipements;
import com.perfekxia.collekt_it.model.Login;
import com.perfekxia.collekt_it.model.Zones;

import java.util.Date;

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "welcome";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_FIRST_RECUP = "IsFirstTimeRecup";
    private static final String PAGE_INDEX = "PageIndex";
    private static final String SERVER_ADDRESS = "ServerAddress";
    private static final String ROLE = "role";
    private static final String EQUIPEMENT = "equipement";
    private static final String LOGIN = "login";
    private static final String LOGIN_DATE = "loginDate";
    private static final String CONNEXION = "connexion";
    private static final String ZONE_AC = "zone";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }
    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setIsFirstRecup(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_RECUP, isFirstTime);
        editor.commit();
    }
    public boolean isFirstRecup() {
        return pref.getBoolean(IS_FIRST_RECUP, true);
    }

    public void setConnexion(boolean isFirstTime) {
        editor.putBoolean(CONNEXION, isFirstTime);
        editor.commit();
    }
    public boolean isConnexion() {
        return pref.getBoolean(CONNEXION, false);
    }

    public void setPageIndex(int index) {
        editor.putInt(PAGE_INDEX, index);
        editor.commit();
    }

    public int pageIndex() {
        return pref.getInt(PAGE_INDEX, 0);
    }

    public void setServerAddress(String address) {
        editor.putString(SERVER_ADDRESS, address);
        editor.commit();
    }

    public String getServerAddress() {
        return pref.getString(SERVER_ADDRESS, "");
    }

    public boolean checkKey(String key) {
        return pref.contains(key);
    }

    public void setRole(String role) {
        editor.putString(ROLE, role);
        editor.commit();
    }

    public String getRole() {
        return pref.getString(ROLE, "");
    }

    public void deleteRole() {
        editor.remove(ROLE);
        editor.commit();
    }

    public void setEquipement(Equipements equipement) {
        Gson gson = new Gson();
        String json = gson.toJson(equipement);
        editor.putString(EQUIPEMENT, json);
        editor.commit();
    }

    public Equipements getEquipement() {
        Gson gson = new Gson();
        String json = pref.getString(EQUIPEMENT, "");
        Equipements equipement = gson.fromJson(json, Equipements.class);
        return equipement;
    }

    public void deleteEquipement() {
        editor.remove(EQUIPEMENT);
        editor.commit();
    }

    public void setZoneAc(Zones zoneAc) {
        Gson gson = new Gson();
        String json = gson.toJson(zoneAc);
        editor.putString(ZONE_AC, json);
        editor.commit();
    }

    public Zones getZoneAv() {
        Gson gson = new Gson();
        String json = pref.getString(ZONE_AC, "");
        Zones zones = gson.fromJson(json, Zones.class);
        return zones;
    }

    public void deleteZoneAc() {
        editor.remove(ZONE_AC);
        editor.commit();
    }

    public void setLogin(Login login) {
        Gson gson = new Gson();
        String json = gson.toJson(login);
        editor.putString(LOGIN, json);
        editor.commit();
    }

    public Login getLogin() {
        Gson gson = new Gson();
        String json = pref.getString(LOGIN, "");
        Login login = gson.fromJson(json, Login.class);
        return login;
    }

    public void deleteLogin() {
        editor.remove(LOGIN);
        editor.commit();
    }

    public void setLoginDate(String date) {
        editor.putString(LOGIN_DATE, date);
        editor.commit();
    }

    public String getLoginDate() {
        return pref.getString(LOGIN_DATE, "");
    }

    public void deleteLoginDate() {
        editor.remove(LOGIN_DATE);
        editor.commit();
    }
}
