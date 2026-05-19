package com.example.tennisapp.model;

import com.google.gson.annotations.SerializedName;

public class Tournament {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("date")
    private String date;

    @SerializedName("tier")
    private String tier;

    @SerializedName("court")
    private Court court;

    @SerializedName("coutry")
    private Country country;

    public Tournament() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTier() { return tier; }
    public void setTier(String tier) { this.tier = tier; }

    public Court getCourt() { return court; }
    public void setCourt(Court court) { this.court = court; }

    public Country getCountry() { return country; }
    public void setCountry(Country country) { this.country = country; }

    public static class Court {
        @SerializedName("name")
        private String name;

        public Court() {}

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    public static class Country {
        @SerializedName("name")
        private String name;

        @SerializedName("acronym")
        private String acronym;

        public Country() {}

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getAcronym() { return acronym; }
        public void setAcronym(String acronym) { this.acronym = acronym; }
    }
}