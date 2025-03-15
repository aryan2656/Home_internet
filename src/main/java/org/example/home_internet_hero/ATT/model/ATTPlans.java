package org.example.home_internet_hero.ATT.model;


public class ATTPlans {
    private String planName;
    private String price;
    private String feature1;
    private String feature2;
    private String feature3;

    // Constructor
    public ATTPlans(String planName, String price, String feature1, String feature2, String feature3) {
        this.planName = planName;
        this.price = price;
        this.feature1 = feature1;
        this.feature2 = feature2;
        this.feature3 = feature3;
    }

    // Getters and Setters
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

    public String getFeature1() {
        return feature1;
    }

    public void setFeature1(String feature1) {
        this.feature1 = feature1;
    }

    public String getFeature2() {
        return feature2;
    }

    public void setFeature2(String feature2) {
        this.feature2 = feature2;
    }

    public String getFeature3() {
        return feature3;
    }

    public void setFeature3(String feature3) {
        this.feature3 = feature3;
    }
}
