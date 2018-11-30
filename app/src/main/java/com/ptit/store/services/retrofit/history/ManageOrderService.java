package com.ptit.store.services.retrofit.history;





import com.ptit.store.common.RequestConstants;
import com.ptit.store.models.response.ItemPreview;
import com.ptit.store.models.response.OrderPreview;
import com.ptit.store.models.response.PageList;
import com.ptit.store.models.response.ResponseBody;

import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ManageOrderService {
    @POST("/api/customers/orders/{status}")
    Observable<Response<ResponseBody<PageList<OrderPreview>>>> getPageOrder(@Header(RequestConstants.AUTHORIZATION) String accessToken,
                                                                           @Path("status") int status,
                                                                           @Query(RequestConstants.PAGE_INDEX_QUERY) int pageIndex,
                                                                           @Query(RequestConstants.PAGE_SIZE_QUERY) int pageSize,
                                                                           @Query(RequestConstants.SORT_BY_QUERY) String sortBy,
                                                                           @Query(RequestConstants.SORT_TYPE_QUERY) String sortType);
    @GET("/api/customers/orders/complete")
    Observable<Response<ResponseBody<PageList<OrderPreview>>>> getPageOrderComplete(@Header(RequestConstants.AUTHORIZATION) String accessToken,
                                                                            @Query(RequestConstants.PAGE_INDEX_QUERY) int pageIndex,
                                                                            @Query(RequestConstants.PAGE_SIZE_QUERY) int pageSize,
                                                                            @Query(RequestConstants.SORT_BY_QUERY) String sortBy,
                                                                            @Query(RequestConstants.SORT_TYPE_QUERY) String sortType);

    @GET("/api/customers/orders/{oid}")
    Observable<Response<ResponseBody<List<ItemPreview>>>> getAllItemsPreview(@Header(RequestConstants.AUTHORIZATION) String accessToken,
                                                                             @Path("oid") String orderID);

    @PUT("/api/admins/orders/confirm")
    Observable<Response<ResponseBody<String>>> confirmOrder(@Body Set<String> orderIDSet);

    @DELETE("/api/customers/orders/{oid}")
    Observable<Response<ResponseBody<String>>> deleteOrder(@Header(RequestConstants.AUTHORIZATION) String accessToken,
                                                           @Path("oid") String orderID);
}
