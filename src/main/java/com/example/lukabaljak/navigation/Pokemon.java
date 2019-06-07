package com.example.lukabaljak.navigation;

public class Pokemon {

    int id;
    String name, pictureURL, types;

    public Pokemon() {
    }

    public Pokemon(int id, String name, String pictureURL, String types) {
        this.id = id;
        this.name = name;
        this.pictureURL = pictureURL;
        this.types = types;
    }

    public Pokemon(String name, String pictureURL, String types) {
        this.name = name;
        this.pictureURL = pictureURL;
        this.types = types;
    }

    public Pokemon(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", name: " + name + ", pictureURL: " + pictureURL + ", types:" + types;
    }
}
