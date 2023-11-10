package com.example.aviaryquest.LoggedIn.HomeTasks;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.aviaryquest.Adapters.Nearby_RV_Adapter;
import com.example.aviaryquest.Data.EbirdJson;
import com.example.aviaryquest.Data.Models.NearbyVariables;
import com.example.aviaryquest.Utilities.Msg;
import com.example.aviaryquest.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Nearby extends Fragment {

    RecyclerView recyclerView;
    CardView country_filter,distance_filter;
    ExtendedFloatingActionButton filters;
    Boolean isFloatBtnsVisible;
    private Nearby_RV_Adapter adapter;
    FusedLocationProviderClient fusedLocationProviderClient;
    Dialog distanceDialog;
    private static final String API_KEY = "b9s2f5kpo8hr";//annnd yess😫😫😫 I know, terrible way of doing
    char filterChosen;
    ProgressBar progressBar;
    Msg msg;

    SeekBar seekBar;
    int progress=0;
    TextView txt_searchRadius,txt_NoData;

    //Distance popup window variables
    ImageView btn_closeInPopup,img_NoData;
    Button btn_searchInPopup;

    private final static int REQUEST_CODE = 100;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nearby, container, false);

        msg=new Msg();
        //Initialize recyclerview and set its properties
        recyclerView = view.findViewById(R.id.nearby_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        //Initialize floating buttons
        distance_filter=view.findViewById(R.id.crd_distance);
        country_filter=view.findViewById(R.id.crd_country);
        filters=view.findViewById(R.id.fab_filters);

        progressBar=view.findViewById(R.id.progressBar);

        //No data found
        txt_NoData=view.findViewById(R.id.txt_noData);
        img_NoData=view.findViewById(R.id.img_noData);



        distance_filter.setVisibility(View.GONE);
        country_filter.setVisibility(View.GONE);
        filters.shrink();

        isFloatBtnsVisible=false;


        //Initialize and set distance dialog pop window properties
        distanceDialog=new Dialog(getActivity());
        distanceDialog.setContentView(R.layout.distance_selector_window);
        distanceDialog.dismiss();
        //searchRadius.setText("Radius: "+seekBar.getProgress()+"/"+seekBar.getMax());

        filters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isFloatBtnsVisible){
                    distance_filter.setVisibility(View.VISIBLE);
                    country_filter.setVisibility(View.VISIBLE);


                    isFloatBtnsVisible=true;
                    filters.extend();
                }else{
                    closeFloatBtns();
                }
            }
        });
        getCurrentLocation('c');
        distance_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_distanceDialog();
            }
        });

        country_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterChosen='c';
                getCurrentLocation(filterChosen);
                closeFloatBtns();
            }
        });


        return view;
    }

    private void closeFloatBtns(){
        distance_filter.setVisibility(View.GONE);
        country_filter.setVisibility(View.GONE);
        isFloatBtnsVisible=false;
        filters.shrink();
    }


    //Popup window, for the user to choose the search radius
    public void show_distanceDialog(){
        btn_closeInPopup=distanceDialog.findViewById(R.id.closeButton);
        btn_searchInPopup=distanceDialog.findViewById(R.id.searchButton);
        seekBar=distanceDialog.findViewById(R.id.seekBar);
        txt_searchRadius=distanceDialog.findViewById(R.id.txt_selectedRadius);

        txt_searchRadius.setText("Radius: "+seekBar.getProgress()+"/"+seekBar.getMax());
        seekBar.setMax(50);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress=progressValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                txt_searchRadius.setText("Radius: "+progress+"km");
            }
        });

        btn_closeInPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distanceDialog.dismiss();
            }
        });

        btn_searchInPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterChosen='d';
                getCurrentLocation(filterChosen);
                distanceDialog.dismiss();
                closeFloatBtns();
            }
        });
        distanceDialog.show();
    }



    //Obtain the user's current location and addresses
    @SuppressLint("MissingPermission")
    private void getCurrentLocation(char filter) {
        progressBar.setVisibility(View.VISIBLE);
        //Initialize Location Manager
        LocationManager locationManager = (LocationManager) getActivity()
                .getSystemService(Context.LOCATION_SERVICE);
        //Check conditions
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            //When location service is enabled, get last location
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    progressBar.setVisibility(View.VISIBLE);
                    if(location != null){
                        Geocoder geocoder=new Geocoder(getActivity(),Locale.getDefault());
                        recyclerView.setVisibility(View.VISIBLE);
                        img_NoData.setVisibility(View.GONE);
                        txt_NoData.setVisibility(View.GONE);

                        try {
                            List<Address> addresses=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

                            if (filter=='c')
                                FetchData_usingCountry(addresses.get(0).getCountryCode());

                            else {
                                if(progress==0) {
                                    FetchData_usingDistance(addresses.get(0).getLatitude(), addresses.get(0).getLongitude(), 20);
                                }
                                else
                                    FetchData_usingDistance(addresses.get(0).getLatitude(), addresses.get(0).getLongitude(), progress);
                            }
                        } catch (IOException e) {
                            Drawable errImg=getResources().getDrawable(R.drawable.baseline_error_24);
                            msg.display(getActivity(),e.getMessage(),"Error Found",errImg,"err");
                        }
                    }
                    else{
                        //When the location result is null, initialize location request
                        LocationRequest locationRequest=new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);

                        //Initialize location call back
                        LocationCallback locationCallback=new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                //Initialize location
                                Location location1=locationResult.getLastLocation();
                                Geocoder geocoder=new Geocoder(getActivity(),Locale.getDefault());

                                try {
                                    List<Address> addresses=geocoder.getFromLocation(location1.getLatitude(),location1.getLongitude(),1);

                                    if (filter=='c')
                                        FetchData_usingCountry(addresses.get(0).getCountryCode());

                                    else {
                                        if(progress==0) {
                                            FetchData_usingDistance(addresses.get(0).getLatitude(), addresses.get(0).getLongitude(), 20);
                                        }
                                        else
                                            FetchData_usingDistance(addresses.get(0).getLatitude(), addresses.get(0).getLongitude(), progress);
                                    }

                                } catch (IOException e) {
                                    Drawable errImg=getResources().getDrawable(R.drawable.baseline_error_24);
                                    msg.display(getActivity(),e.getMessage(),"Error Found",errImg,"err");
                                }
                            }
                        };
                        //Request location updates
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                                locationCallback, Looper.myLooper());
                    }
                }
            });
        }else{
            //When the location service is disabled, Open location settings
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
    //Retrieve birds in the country
    private void FetchData_usingCountry(String regionCode){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.ebird.org/v2/data/obs/"+regionCode+"/recent/notable/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EbirdJson jsonPlaceholder = retrofit.create(EbirdJson.class);

        Call<List<NearbyVariables>> call = jsonPlaceholder.getNotableObservations();

        call.enqueue(new Callback<List<NearbyVariables>>() {
            @Override
            public void onResponse(Call<List<NearbyVariables>> call, Response<List<NearbyVariables>> response) {
                if(!response.isSuccessful()){
                    Drawable errImg=getResources().getDrawable(R.drawable.baseline_error_24);
                    msg.display(getActivity(),response.message(),"Birds in the country",errImg,"err");                    return;
                }
                progressBar.setVisibility(View.GONE);
                List<NearbyVariables> ebirdsList=response.body();
                adapter=new Nearby_RV_Adapter(getContext(),ebirdsList);
                recyclerView.setAdapter(adapter);
                Drawable errImg=getResources().getDrawable(R.drawable.baseline_check_circle_24);
                msg.display(getActivity(),"Successfully uploaded","Birds in the country",errImg,"success");
                String rawJson = response.raw().body().toString();
                Log.d("Raw JSON Response", rawJson);
            }

            // Handle network errors
            @Override
            public void onFailure(Call<List<NearbyVariables>> call, Throwable t) {
                Drawable errImg=getResources().getDrawable(R.drawable.baseline_error_24);
                msg.display(getActivity(),t.getMessage(),"Error Found",errImg,"err");
            }
        });
    }

    //Retrieve birds near the user, with the specified radius
    private void FetchData_usingDistance(double latitude, double longitude,int distance) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.ebird.org/v2/").addConverterFactory(GsonConverterFactory.create())
                .build();

        EbirdJson eBirdService = retrofit.create(EbirdJson.class);

        Call<List<NearbyVariables>> call = eBirdService.getRecentObservations(latitude,longitude,distance,API_KEY);

        call.enqueue(new Callback<List<NearbyVariables>>() {
            @Override
            public void onResponse(Call<List<NearbyVariables>> call, Response<List<NearbyVariables>> response) {
                if (response.isSuccessful()) {
                    List<NearbyVariables> ebirdsList = response.body();
                    if(ebirdsList.isEmpty()) {
                        // Handle the case where the response is empty or invalid
                        recyclerView.setVisibility(View.GONE);
                        img_NoData.setVisibility(View.VISIBLE);
                        txt_NoData.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        adapter = new Nearby_RV_Adapter(getContext(), ebirdsList);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setVisibility(View.VISIBLE);
                        Drawable errImg=getResources().getDrawable(R.drawable.baseline_check_circle_24);
                        msg.display(getActivity(),"Successfully loaded","Birds within "+distance+"Km",errImg,"success");
                    }
                } else {
                    // Handle the case where the response is not successful
                    Drawable errImg=getResources().getDrawable(R.drawable.baseline_check_circle_24);
                    msg.display(getActivity(),"Error found",response.message(),errImg,"err");

                }
            }

            @Override
            public void onFailure(Call<List<NearbyVariables>> call, Throwable t) {
                // Handle network errors
                Drawable errImg=getResources().getDrawable(R.drawable.baseline_error_24);
                msg.display(getActivity(),t.getMessage(),"Error Found",errImg,"err");            }
        });
    }








}