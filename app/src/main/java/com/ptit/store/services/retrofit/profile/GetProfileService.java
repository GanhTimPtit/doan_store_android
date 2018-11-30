package com.ptit.store.services.retrofit.profile;

import com.ptit.store.common.RequestConstants;
import com.ptit.store.models.response.Profile;
import com.ptit.store.models.response.ResponseBody;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface GetProfileService {
    @GET("api/customers/profiles")
    Observable<Response<ResponseBody<Profile>>> getProfile(@Header(RequestConstants.AUTHORIZATION) String accessToken);
}
