package org.example.home_internet_hero;

public class plans {
    private String name;
    private String price;
    private String speed;
    private String offer;

    public plans(String name, String price, String speed, String offer) {
        this.name = name;
        this.price = price;
        this.speed = speed;
        this.offer = offer;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getSpeed() {
        return speed;
    }

    public String getOffer() {
        return offer;
    }
}
