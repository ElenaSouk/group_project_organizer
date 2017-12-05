package com.example.esoukhanov.group_project_organizer;

/**
 * Created by milab on 2017-12-04.
 */

public class ListItim {
    private String date;
    private String litars;
    private String price;
    private String km;
    public ListItim(String date, String litars, String price, String km){
        this.date=date;
        this.litars=litars;
        this.price=price;
        this.km=km;
    }

    public String getDate() {
        return date;
    }

    public String getLitars() {
        return litars;
    }

    public String getPrice() {
        return price;
    }

    public String getKm() {
        return km;
    }
}
