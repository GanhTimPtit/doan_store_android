package com.ptit.store.services.google_map;

import com.ptit.store.common.Constants;
import com.ptit.store.models.google_map.GoogleAddressResponse;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface GetMapLatLonService {
    @GET("maps/api/geocode/json?components=country:VN")
    Observable<GoogleAddressResponse> queryAddress(@Query("address") String address, @Query(Constants.KEY) String apiKey);
}
