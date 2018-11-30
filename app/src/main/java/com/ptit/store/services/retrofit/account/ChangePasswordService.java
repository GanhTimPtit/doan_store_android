package com.ptit.store.services.retrofit.account;

import com.ptit.store.common.RequestConstants;
import com.ptit.store.models.body.NewPassword;
import com.ptit.store.models.response.ResponseBody;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ChangePasswordService {
    @POST("/api/auths/customer/newPassword")
    Observable<Response<ResponseBody<String>>> changePassword(@Header(RequestConstants.AUTHORIZATION) String accessToken, @Body NewPassword newPassword);
}
