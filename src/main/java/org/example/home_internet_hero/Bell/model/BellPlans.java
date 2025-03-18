package org.example.home_internet_hero.Bell.model;


public class BellPlans {

    private String planName;
    private String price;
    private String downloadSpeed;
    private String uploadSpeed;

    public BellPlans(String planName, String price, String downloadSpeed, String uploadSpeed) {
        this.planName = planName;
        this.price = price;
        this.downloadSpeed = downloadSpeed;
        this.uploadSpeed = uploadSpeed;
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

    public String getDownloadSpeed() {
        return downloadSpeed;
    }

    public void setDownloadSpeed(String downloadSpeed) {
        this.downloadSpeed = downloadSpeed;
    }

    public String getUploadSpeed() {
        return uploadSpeed;
    }

    public void setUploadSpeed(String uploadSpeed) {
        this.uploadSpeed = uploadSpeed;
    }
}
