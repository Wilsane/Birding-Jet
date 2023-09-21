package com.example.aviaryquest.Data;

import com.example.aviaryquest.Data.Models.NearbyVariables;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;


//Using ebird 2.0 api
public interface EbirdJson {

    /**Obtaining all birds in the user's region*/
    @GET("?key=b9s2f5kpo8hr")
    Call<List<NearbyVariables>> getNotableObservations();

    /**Obtain hotpots near the user, according the defined distance*/
    @GET("data/obs/geo/recent")
    Call<List<NearbyVariables>> getRecentObservations(
            @Query("lat") double latitude,
            @Query("lng") double longitude,
            @Query("dist") int searchRadius,
            @Header("X-eBirdApiToken") String apiKey
    );
}
