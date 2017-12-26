package com.example.esoukhanov.group_project_organizer;

/**
 * Created by esoukhanov on 2017-12-03.
 */

public class ThermoItem {
    private String name;
    private double morningTemp;
    private double afternoonTemp;
    private double eveningTemp;
    private boolean selected;

    public ThermoItem(String name) {
        this.name = name;
        selected = false;
        //init with default values
        morningTemp = 19.5;
        afternoonTemp = 21.5;
        eveningTemp = 20.0;
    }

    public ThermoItem(String name, double mTemp, double aTemp, double eTemp) {
        this.name = name;
        selected = false;
        //init with default values
        morningTemp = mTemp;
        afternoonTemp = aTemp;
        eveningTemp = eTemp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMorningTemp() {
        return morningTemp;
    }

    public void setMoningTemp(double morningTemp) {
        this.morningTemp = morningTemp;
    }

    public double getAfternoonTemp() {
        return afternoonTemp;
    }

    public void setAfternoonTemp(double afternoonTemp) {
        this.afternoonTemp = afternoonTemp;
    }

    public double getEveningTemp() {
        return eveningTemp;
    }

    public void setEveningTemp(double eveningTemp) {
        this.eveningTemp = eveningTemp;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
