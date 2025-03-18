package org.example.home_internet_hero.rogers.model;

public class RogersPlans {
    private String planName;
    private String price;
    private String poweredBy;
    private String support;
    private String speed;
    private String device;

    public RogersPlans(String planName, String price, String poweredBy, String support, String speed, String device) {
        this.planName = planName;
        this.price = price;
        this.poweredBy = poweredBy;
        this.support = support;
        this.speed = speed;
        this.device = device;
    }

    public String getPlanName() {
        return planName;
    }

    public String getPrice() {
        return price;
    }

    public String getPoweredBy() {
        return poweredBy;
    }

    public String getSupport() {
        return support;
    }

    public String getSpeed() {
        return speed;
    }

    public String getDevice() {
        return device;
    }
}
