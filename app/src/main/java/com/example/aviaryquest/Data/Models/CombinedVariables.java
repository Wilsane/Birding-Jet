package com.example.aviaryquest.Data.Models;

import androidx.recyclerview.widget.RecyclerView;

public class CombinedVariables {

    private ImageVariables imageVariables;
    private NearbyVariables nearbyVariables;

    public ImageVariables getImageVariables() {
        return imageVariables;
    }

    public void setImageVariables(ImageVariables imageVariables) {
        this.imageVariables = imageVariables;
    }

    public NearbyVariables getNearbyVariables() {
        return nearbyVariables;
    }

    public void setNearbyVariables(NearbyVariables nearbyVariables) {
        this.nearbyVariables = nearbyVariables;
    }


}
