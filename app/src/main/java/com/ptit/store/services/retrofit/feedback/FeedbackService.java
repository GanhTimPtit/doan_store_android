package com.ptit.store.services.retrofit.feedback;

import com.ptit.store.common.RequestConstants;
import com.ptit.store.models.response.ResponseBody;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface FeedbackService {
    @POST("/api/customers/{customerID}/feedback")
    Observable<Response<ResponseBody<String>>> sendFeedback(@Header(RequestConstants.AUTHORIZATION) String accessToken,
                                                            @Body String feedback);
}
