package com.example.tennisapp.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TournamentResponse {
    @SerializedName("data")
    private List<Tournament> data;

    public List<Tournament> getData() { return data; }
}