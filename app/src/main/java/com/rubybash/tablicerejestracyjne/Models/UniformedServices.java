package com.rubybash.tablicerejestracyjne.Models;

/**
 * Created by michalzuk on 5/24/2017.
 */

public class UniformedServices {

    private String shortcut;
    private String service;
    private String additional;

    public UniformedServices(String shortcut, String service, String additional) {
        this.shortcut = shortcut;
        this.service = service;
        this.additional = additional;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }
}
