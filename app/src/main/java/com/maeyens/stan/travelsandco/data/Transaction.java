package com.maeyens.stan.travelsandco.data;

/**
 * Created by Stan on 12-Jun-16.
 */
public class Transaction {
    private int id;
    private String description;
    private double value;
    private String addedBy;

    public Transaction(int id, String description, double value, String addedBy){
        this.description = description;
        this.value = value;
        this.addedBy = addedBy;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getID() {
        return id;
    }
}
