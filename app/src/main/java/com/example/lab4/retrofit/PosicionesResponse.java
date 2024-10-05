package com.example.lab4.retrofit;

import com.example.lab4.recyclerview.Equipo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PosicionesResponse {

    @SerializedName("table")
    private List<Equipo> teams;

    public List<Equipo> getTeams() {
        return teams;
    }

}
