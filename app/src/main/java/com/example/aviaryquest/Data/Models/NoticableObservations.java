package com.example.aviaryquest.Data.Models;
import com.google.gson.annotations.SerializedName;

public class NoticableObservations {



    @SerializedName("speciesCode")
    private String speciesCode = null;

    @SerializedName("comName")
    private String comName = null;

    @SerializedName("sciName")
    private String sciName = null;

    @SerializedName("locId")
    private String locId = null;

    @SerializedName("locName")
    private String locName = null;

    @SerializedName("obsDt")
    private String obsDt = null;

    @SerializedName("howMany")
    private Integer howMany = null;

    @SerializedName("lat")
    private Double lat = null;

    @SerializedName("lng")
    private Double lng = null;

    @SerializedName("obsValid")
    private Boolean obsValid = null;

    @SerializedName("obsReviewed")
    private Boolean obsReviewed = null;

    @SerializedName("locationPrivate")
    private Boolean locationPrivate = null;

    public NoticableObservations(String speciesCode, String name, String sciName, String locId, String locName, String obsDt, int howMany, double latitude, double longitude, boolean obsValid, boolean obsReviewed, boolean locationPrivate, String subId) {
        this.locName=locName;
        this.lat=latitude;
        this.lng=longitude;

    }
    public String getSpeciesCode() {
        return speciesCode;
    }

    public String getComName() {
        return comName;
    }

    public String getSciName() {
        return sciName;
    }

    public String getLocId() {
        return locId;
    }

    public String getLocName() {
        return locName;
    }

    public String getObsDt() {
        return obsDt;
    }

    public Integer getHowMany() {
        return howMany;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public Boolean getObsValid() {
        return obsValid;
    }

    public Boolean getObsReviewed() {
        return obsReviewed;
    }

    public Boolean getLocationPrivate() {
        return locationPrivate;
    }

    // Getters and setters (if needed) go here
}

