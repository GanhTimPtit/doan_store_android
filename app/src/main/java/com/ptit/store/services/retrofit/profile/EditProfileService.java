package com.ptit.store.services.retrofit.profile;

import com.ptit.store.common.RequestConstants;
import com.ptit.store.models.body.ProfileBody;
import com.ptit.store.models.response.Profile;
import com.ptit.store.models.response.ResponseBody;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PUT;

public interface EditProfileService {
    @PUT("api/customers/profiles")
    Observable<Response<ResponseBody<Profile>>> updateProfile(@Header(RequestConstants.AUTHORIZATION) String accessToken, @Body ProfileBody profileBody);
}
