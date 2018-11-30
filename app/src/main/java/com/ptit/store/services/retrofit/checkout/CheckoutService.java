package com.ptit.store.services.retrofit.checkout;

import com.ptit.store.common.RequestConstants;
import com.ptit.store.models.body.ItemBody;
import com.ptit.store.models.body.OrderBody;
import com.ptit.store.models.response.ResponseBody;

import java.util.Set;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PUT;

public interface CheckoutService {
    @PUT("/api/customers/orders")
    Observable<Response<ResponseBody<String>>> insertOrder(@Header(RequestConstants.AUTHORIZATION) String accessToken,
                                                           @Body OrderBody orderBody);
}
