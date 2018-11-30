package com.ptit.store.services.retrofit.rate;

import com.ptit.store.common.RequestConstants;
import com.ptit.store.models.body.RateBody;
import com.ptit.store.models.response.ResponseBody;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PUT;

public interface RateService {
    @PUT("/api/customers/rate")
    Observable<Response<ResponseBody<String>>> rateShop(@Header(RequestConstants.AUTHORIZATION) String accessToken, @Body RateBody rateBody);
}
