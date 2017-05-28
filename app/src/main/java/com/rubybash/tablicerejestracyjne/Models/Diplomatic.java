package com.rubybash.tablicerejestracyjne.Models;

/**
 * Created by michalzuk on 5/24/2017.
 */

public class Diplomatic {

    private String shortcut;
    private String country;

    public Diplomatic(String shortcut, String country) {
        this.shortcut = shortcut;
        this.country = country;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
