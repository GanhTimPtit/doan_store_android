package com.ptit.store.services.retrofit.maps;

import com.ptit.store.models.body.LatLngBody;
import com.ptit.store.models.response.ResponseBody;
import com.ptit.store.models.response.StoreBranchViewModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GetStoreBranchService {
    @POST("/api/admins/store_branch_distance")
    Observable<Response<ResponseBody<List<StoreBranchViewModel>>>> getAllBranchStore(@Body LatLngBody latLngBody);
}
