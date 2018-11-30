package com.ptit.store.services.retrofit.account;



import com.ptit.store.models.response.AccessTokenPair;
import com.ptit.store.models.response.ResponseBody;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface RefreshAccessTokenService {
    @POST("api/auths/accessToken/refresh")
    Observable<Response<ResponseBody<AccessTokenPair>>> refreshAccessToken(@Body AccessTokenPair accessTokenPair);
}
