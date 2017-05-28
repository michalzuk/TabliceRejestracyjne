package com.rubybash.tablicerejestracyjne.Models;

/**
 * Created by michalzuk on 07.05.17.
 */

public class Province {

    private String shortcut;
    private String city;
    private String province;

    public Province(String shortcut, String city, String province) {
        this.shortcut = shortcut;
        this.city = city;
        this.province = province;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
