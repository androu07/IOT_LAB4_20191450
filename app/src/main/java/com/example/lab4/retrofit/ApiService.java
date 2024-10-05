package com.example.lab4.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("all_leagues.php")
    Call<LigaResponse> getAllLeagues();

    @GET("search_all_leagues.php")
    Call<LigaResponsePorPais> getLeaguesByCountry(@Query("c") String country);

    @GET("lookuptable.php")
    Call<PosicionesResponse> getPosiciones(@Query("l") String idLiga, @Query("s") String temporada);

}
