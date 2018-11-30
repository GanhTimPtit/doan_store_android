package com.ptit.store.services.retrofit.account;

import com.ptit.store.common.RequestConstants;

import com.ptit.store.models.body.FacebookLoginBody;
import com.ptit.store.models.response.AccessToken;
import com.ptit.store.models.response.HeaderProfile;
import com.ptit.store.models.response.ResponseBody;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;

import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LoginServices {
    @POST("api/auths/customer/login")
    Observable<Response<ResponseBody<AccessToken>>> checkLogin(@Header(RequestConstants.AUTHORIZATION) String base64Account,
                                                               @Body String fcmToken);


    @POST("api/auths/customer/facebook")
    Observable<Response<ResponseBody<AccessToken>>> facebookLogin(@Body FacebookLoginBody facebookLoginBody);

    @PUT("api/auths/facebook/register/{fcmToken}")
    Observable<Response<ResponseBody<HeaderProfile>>> facebookRegister(@Body FacebookLoginBody facebookLoginBody,
                                                                       @Path("fcmToken") String fcmToken);
}
