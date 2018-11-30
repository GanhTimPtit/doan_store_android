package com.ptit.store.services.retrofit.clothes;

import com.ptit.store.common.RequestConstants;
import com.ptit.store.models.Category;
import com.ptit.store.models.response.ClothesPreview;
import com.ptit.store.models.response.ClothesSearchPreview;
import com.ptit.store.models.response.PageList;
import com.ptit.store.models.body.RateClothesBody;
import com.ptit.store.models.Advertisement;
import com.ptit.store.models.response.ClothesViewModel;
import com.ptit.store.models.response.RateClothesViewModel;
import com.ptit.store.models.response.ResponseBody;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by KingIT on 4/22/2018.
 */

public interface ClothesService {
    @GET("/api/products/clothes/{id}/category")
    Observable<Response<ResponseBody<PageList<ClothesPreview>>>> getClothesPreview(@Path("id") String categoryID,
                                                                                   @Query(RequestConstants.PAGE_INDEX_QUERY) int pageIndex,
                                                                                   @Query(RequestConstants.PAGE_SIZE_QUERY) int pageSize,
                                                                                   @Query(RequestConstants.SORT_BY_QUERY) String sortBy,
                                                                                   @Query(RequestConstants.SORT_TYPE_QUERY) String sortType);


    @GET("/api/products/clothes")
    Observable<Response<ResponseBody<PageList<ClothesPreview>>>> getClothesPreview(@Query(RequestConstants.PAGE_INDEX_QUERY) int pageIndex,
                                                                                   @Query(RequestConstants.PAGE_SIZE_QUERY) int pageSize,
                                                                                   @Query(RequestConstants.SORT_BY_QUERY) String sortBy,
                                                                                   @Query(RequestConstants.SORT_TYPE_QUERY) String sortType);

    @GET("/api/customers/clothes/{id}")
    Observable<Response<ResponseBody<ClothesViewModel>>> getClothesViewModelWithAuth(@Header(RequestConstants.AUTHORIZATION) String accessToken,
                                                                                     @Path("id") String clothesID);

    @GET("/api/products/clothes/{id}")
    Observable<Response<ResponseBody<ClothesViewModel>>> getClothesViewModelWithoutAuth(@Path("id") String clothesID);

    @GET("/api/products/similarClothes/{id}")
    Observable<Response<ResponseBody<PageList<ClothesPreview>>>> getSimilarClothesPreview(@Path("id") String clothesID,
                                                                                          @Query(RequestConstants.PAGE_INDEX_QUERY) int pageIndex,
                                                                                          @Query(RequestConstants.PAGE_SIZE_QUERY) int pageSize,
                                                                                          @Query(RequestConstants.SORT_BY_QUERY) String sortBy,
                                                                                          @Query(RequestConstants.SORT_TYPE_QUERY) String sortType);

    @GET("/api/products/recommendClothes/{id}")
    Observable<Response<ResponseBody<PageList<ClothesPreview>>>> getRecommendClothesPreview(@Path("id") String clothesID,
                                                                                          @Query(RequestConstants.PAGE_INDEX_QUERY) int pageIndex,
                                                                                          @Query(RequestConstants.PAGE_SIZE_QUERY) int pageSize,
                                                                                          @Query(RequestConstants.SORT_BY_QUERY) String sortBy,
                                                                                          @Query(RequestConstants.SORT_TYPE_QUERY) String sortType);

    @GET("/api/products/rateClothes/{id}")
    Observable<Response<ResponseBody<List<RateClothesViewModel>>>> getAllRateClothes(@Path("id") String clothesID,
                                                                                     @Query(RequestConstants.SORT_BY_QUERY) String sortBy,
                                                                                     @Query(RequestConstants.SORT_TYPE_QUERY) String sortType);

    @POST("/api/customers/save_clothes/{id}")
    Observable<Response<ResponseBody<String>>> saveClothes(@Header(RequestConstants.AUTHORIZATION) String accessToken,
                                                           @Path("id") String clothesID);

    @PUT("/api/customers/rateClothes/{id}")
    Observable<Response<ResponseBody<String>>> rateClothes(@Header(RequestConstants.AUTHORIZATION) String accessToken,
                                                           @Path("id") String clothesID,
                                                           @Body RateClothesBody body);

    @GET("api/customers/orders/{clothesID}/state")
    Observable<Response<ResponseBody<Boolean>>> getClothesState(@Header(RequestConstants.AUTHORIZATION) String accessToken,
                                                            @Path("clothesID") String clothesID);

    @GET("api/products/advertisement")
    Observable<Response<ResponseBody<List<Advertisement>>>> getAdvertisement();

    @GET("api/products/category")
    Observable<Response<ResponseBody<List<Category>>>> getCategory();

    @GET("/api/products/search/clothes/")
    Observable<Response<ResponseBody<List<ClothesSearchPreview>>>> getClothesSearchPreview();
}
