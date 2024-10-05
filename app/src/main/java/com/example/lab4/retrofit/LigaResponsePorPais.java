package com.example.lab4.retrofit;

import com.example.lab4.recyclerview.Liga;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LigaResponsePorPais {

    @SerializedName("countries")
    private List<Liga> leagues;

    // Getters y Setters
    public List<Liga> getLeagues() {
        return leagues;
    }

    public void setLeagues(List<Liga> leagues) {
        this.leagues = leagues;
    }

}
