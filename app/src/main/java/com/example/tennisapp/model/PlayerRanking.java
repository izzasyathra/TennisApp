package com.example.tennisapp.model;

import com.google.gson.annotations.SerializedName;

public class PlayerRanking {

    @SerializedName("id")
    private long id;

    @SerializedName("point")
    private int point;

    @SerializedName("position")
    private int position;

    @SerializedName("player")
    private PlayerDetail player;

    public PlayerRanking() {}

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public int getPoint() { return point; }
    public void setPoint(int point) { this.point = point; }

    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }

    public PlayerDetail getPlayer() { return player; }
    public void setPlayer(PlayerDetail player) { this.player = player; }

    public static class PlayerDetail {
        @SerializedName("id")
        private int id;

        @SerializedName("name")
        private String name;

        @SerializedName("birthday")
        private String birthday;

        @SerializedName("countryAcr")
        private String countryAcr;

        @SerializedName("currentRank")
        private int currentRank;

        @SerializedName("points")
        private int points;

        @SerializedName("country")
        private Country country;

        public PlayerDetail() {}

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getBirthday() { return birthday; }
        public void setBirthday(String birthday) { this.birthday = birthday; }

        public String getCountryAcr() { return countryAcr; }
        public void setCountryAcr(String countryAcr) { this.countryAcr = countryAcr; }

        public int getCurrentRank() { return currentRank; }
        public void setCurrentRank(int currentRank) { this.currentRank = currentRank; }

        public int getPoints() { return points; }
        public void setPoints(int points) { this.points = points; }

        public Country getCountry() { return country; }
        public void setCountry(Country country) { this.country = country; }

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
}