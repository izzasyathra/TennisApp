package com.example.tennisapp.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class RankingResponse {
    // Kita tambahkan alias "results", "rankings", "ranking_list" agar pasti tertangkap
    @SerializedName(value = "data", alternate = {"results", "rankings", "ranking", "ranking_list"})
    private List<PlayerRanking> data;

    public List<PlayerRanking> getData() { return data; }
}