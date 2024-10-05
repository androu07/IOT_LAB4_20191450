package com.example.lab4.retrofit;

import com.example.lab4.recyclerview.Liga;

import java.util.List;

public class LigaResponse {

    private List<Liga> leagues;

    public List<Liga> getLeagues() {
        return leagues;
    }

    public void setLeagues(List<Liga> leagues) {
        this.leagues = leagues;
    }

}
