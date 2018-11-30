package com.ptit.store.services.retrofit.splash;

import com.ptit.store.models.response.ResponseBody;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;

public interface SplashService {
    @GET("/api/admins/mobile/version")
    Observable<Response<ResponseBody<Integer>>> getAppVersion();
}
