package com.example.tennisapp.api;

import com.example.tennisapp.model.RankingResponse;
import com.example.tennisapp.model.TournamentResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ApiService {

    String API_KEY = "563b9b3319msh29aa295f9fba35ap141487jsn8af006a6a31a";
    String API_HOST = "tennis-api-atp-wta-itf.p.rapidapi.com";

    @GET("tennis/v2/atp/ranking/singles/")
    Call<RankingResponse> getPlayerRankings(
            @Header("x-rapidapi-key") String apiKey,
            @Header("x-rapidapi-host") String apiHost
    );

    @GET("tennis/v2/atp/tournament/calendar/{year}")
    Call<TournamentResponse> getTournamentCalendar(
            @Header("x-rapidapi-key") String apiKey,
            @Header("x-rapidapi-host") String apiHost,
            @Path("year") int year
    );
}