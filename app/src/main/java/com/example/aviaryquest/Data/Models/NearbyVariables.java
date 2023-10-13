package com.example.aviaryquest.Data.Models;

public class NearbyVariables {

    /***
     *  comName:Common Name,
     sciName:Scientific Name,
     locName:Location Name*/
    private String comName,sciName,locName;

    public NearbyVariables() {

    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public void setSciName(String sciName) {
        this.sciName = sciName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getComName() {
        return comName;
    }

    public String getSciName() {
        return sciName;
    }

    public String getLocName() {
        return locName;
    }

}
