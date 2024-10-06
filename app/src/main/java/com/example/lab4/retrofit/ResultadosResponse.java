package com.example.lab4.retrofit;

import com.example.lab4.recyclerview.Equipo;
import com.example.lab4.recyclerview.Resultado;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultadosResponse {

    @SerializedName("events")
    private List<Resultado> resultados;

    public List<Resultado> getResultados() {
        return resultados;
    }

}
