package com.perfekxia.collekt_it.model;

import com.google.gson.annotations.SerializedName;

public class AdminPins {
    @SerializedName("idAdminPin")
    private Integer idAdminPin;

    @SerializedName("pin")
    private Integer pin;

    @Override
    public String toString() {
        return "AdminPins{" +
                "idAdminPin=" + idAdminPin +
                ", pin=" + pin +
                '}';
    }
}
