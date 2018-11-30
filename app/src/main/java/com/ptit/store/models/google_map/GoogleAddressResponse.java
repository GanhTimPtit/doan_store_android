package com.ptit.store.models.google_map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class GoogleAddressResponse {
    @SerializedName("results")
    @Expose
    private List<Address> results;

    public List<Address> getResults() {
        return results;
    }

    public void setResults(List<Address> results) {
        this.results = results;
    }
}
