package com.ptit.store.services.retrofit.register;

import com.ptit.store.common.RequestConstants;
import com.ptit.store.models.body.CustomerRegisterBody;
import com.ptit.store.models.response.ResponseBody;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RegisterService {
    @POST("/api/auths/customer/register")
    Observable<Response<ResponseBody<String>>> CustomerRegister(@Header(RequestConstants.AUTHORIZATION) String base64Account,
                                                                @Body CustomerRegisterBody body);
}
