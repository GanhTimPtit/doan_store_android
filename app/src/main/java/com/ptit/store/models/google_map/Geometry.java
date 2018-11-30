package com.ptit.store.models.google_map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class Geometry {
    @SerializedName("location")
    @Expose
    Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
