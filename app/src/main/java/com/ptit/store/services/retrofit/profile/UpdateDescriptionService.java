package com.ptit.store.services.retrofit.profile;

import com.ptit.store.common.RequestConstants;
import com.ptit.store.models.response.ResponseBody;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface UpdateDescriptionService {
    @POST("api/customers/description")
    Observable<Response<ResponseBody<String>>> updateDescription(@Header(RequestConstants.AUTHORIZATION) String accessToken, @Body String description);
}
