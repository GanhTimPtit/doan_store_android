package com.ptit.store.services.retrofit.profile;


import com.ptit.store.common.RequestConstants;
import com.ptit.store.models.response.HeaderProfile;
import com.ptit.store.models.response.ResponseBody;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface GetHeaderProfileService {
    @GET("api/customers/headerProfiles")
    Observable<Response<ResponseBody<HeaderProfile>>> getHeaderProfile(@Header(RequestConstants.AUTHORIZATION) String accessToken);
}
