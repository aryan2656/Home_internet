package org.example.home_internet_hero.Fido.model;


public class FidoPlans {

    private String planName;
    private String price;
    private String speed;
    private String offer;

    public FidoPlans(String planName, String price, String speed, String offer) {
        this.planName = planName;
        this.price = price;
        this.speed = speed;
        this.offer = offer;
    }

    // Getters and setters
    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }
}
