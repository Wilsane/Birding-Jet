package com.example.aviaryquest.Data.Models;

public class NearbyVariables {

    /***
     *  comName:Common Name,
     sciName:Scientific Name,
     locName:Location Name*/
    private String comName;
    private String sciName;
    private String locName;
    private String birdPicUrl;

    public String getBirdPicUrl() {
        return birdPicUrl;
    }

    public void setBirdPicUrl(String birdPicUrl) {
        this.birdPicUrl = birdPicUrl;
    }


    public NearbyVariables() {
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

    public void setComName(String comName) {
        this.comName = comName;
    }

    public void setSciName(String sciName) {
        this.sciName = sciName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }
}
