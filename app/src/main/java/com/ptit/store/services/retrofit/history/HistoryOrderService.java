package com.ptit.store.services.retrofit.history;

import com.ptit.store.common.RequestConstants;
import com.ptit.store.models.response.PageList;
import com.ptit.store.models.response.OrderPreview;
import com.ptit.store.models.response.ResponseBody;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface HistoryOrderService {
    @GET("/api/customers/orders")
    Observable<Response<ResponseBody<PageList<OrderPreview>>>> getAllOrder(@Header(RequestConstants.AUTHORIZATION) String accessToken,
                                                                           @Query(RequestConstants.PAGE_INDEX_QUERY) int pageIndex,
                                                                           @Query(RequestConstants.PAGE_SIZE_QUERY) int pageSize,
                                                                           @Query(RequestConstants.SORT_BY_QUERY) String sortBy,
                                                                           @Query(RequestConstants.SORT_TYPE_QUERY) String sortType);
}
