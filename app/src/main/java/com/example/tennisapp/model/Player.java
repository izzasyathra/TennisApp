package com.example.tennisapp.model;

public class Player {
    private int id;
    private String name;
    private String country;
    private int ranking;
    private String imageUrl;
    private int age;
    private String hand;

    public Player() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public int getRanking() { return ranking; }
    public void setRanking(int ranking) { this.ranking = ranking; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getHand() { return hand; }
    public void setHand(String hand) { this.hand = hand; }
}