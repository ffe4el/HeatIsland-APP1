package com.example.projec7;
import java.io.Serializable;

public class MarkerInfo implements Serializable {
    private String name;
    private String address;
    private double area;
    private int user;
    private int fan;
    private int air;

    public MarkerInfo(String name, String address, double area, int user, int fan, int air) {
        this.name = name;
        this.address = address;
        this.area = area;
        this.user = user;
        this.fan = fan;
        this.air = air;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getArea() {
        return area;
    }

    public int getUser() {
        return user;
    }

    public int getFan() {
        return fan;
    }

    public int getAir() {
        return air;
    }
}
