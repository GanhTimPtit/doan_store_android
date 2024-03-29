package com.ptit.store.services.google_map;


import com.ptit.store.common.Constants;
import com.ptit.store.models.google_map.GoogleAddressResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface GetMapAddressService {
    @GET("maps/api/geocode/json")
    Observable<GoogleAddressResponse> getAddress(@Query("latlng") String latlon, @Query(Constants.KEY) String apiKey);
}
