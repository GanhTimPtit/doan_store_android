package com.ptit.store.services.retrofit.following;

import com.ptit.store.common.RequestConstants;
import com.ptit.store.models.response.PageList;
import com.ptit.store.models.response.ResponseBody;
import com.ptit.store.models.response.SaveClothesPreview;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface UnSaveClothesService {
    @DELETE("/api/customers/save_clothes/{id}")
    Observable<Response<ResponseBody<PageList<SaveClothesPreview>>>> UnSaveClothes(@Header(RequestConstants.AUTHORIZATION) String accessToken,
                                                                                   @Path("id") String clothesID);
}
