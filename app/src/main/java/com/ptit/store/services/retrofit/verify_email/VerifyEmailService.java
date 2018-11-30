package com.ptit.store.services.retrofit.verify_email;

import com.ptit.store.models.response.ResponseBody;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface VerifyEmailService {
    @POST("api/auths/resend/registration/confirm/{username}")
    Observable<Response<ResponseBody<String>>> verifyEmail(@Path("username") String username);
}
