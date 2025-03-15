package org.example.home_internet_hero;

public class plans {

    private String plan;
    private int price;
    private int id;

    public plans(String plan, int price,int id) {
        this.plan = plan;
        this.price = price;
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getID(int id) {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}
