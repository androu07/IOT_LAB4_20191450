package com.example.lab4.recyclerview;

import com.google.gson.annotations.SerializedName;

public class Liga {

    @SerializedName("idLeague")
    private String id;

    @SerializedName("strLeague")
    private String name;

    @SerializedName("strLeagueAlternate")
    private String altName1;

    @SerializedName("strLeagueAlternate2")
    private String altName2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAltName1() {
        return altName1;
    }

    public void setAltName1(String altName1) {
        this.altName1 = altName1;
    }

    public String getAltName2() {
        return altName2;
    }

    public void setAltName2(String altName2) {
        this.altName2 = altName2;
    }

}
