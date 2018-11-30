package com.ptit.store.presenter.shop.clothes_detail;

import com.ptit.store.models.body.RateClothesBody;
import com.ptit.store.models.body.ItemBody;
import com.ptit.store.presenter.BaseInteractor;
import com.ptit.store.presenter.OnRequestCompleteListener;

import java.util.Set;

/**
 * Created by KingIT on 4/23/2018.
 */

public interface ClothesDetailInteractor extends BaseInteractor{
    void getClothesDetail(String clothesID, OnGetClothesDetailCompleteListener listener);
    void saveClothes(String clothesID, OnRequestCompleteListener listener);
    void deleteSavedClothes(String clothesID, OnRequestCompleteListener listener);

    void getSimilarClothes(String clothesID, int pageIndex, int pageSize, OnGetPageClothesPreviewCompleteListener listener);
    void getRecommendClothes(String clothesID, int pageIndex, int pageSize, OnGetPageClothesPreviewCompleteListener listener);


    void rateClothes(String clothesID, RateClothesBody rateClothesBody, OnRequestCompleteListener listener);
    void getAllRateClothes(String clothesID,OnGetPageRateClothesSuccessListener listener);
    void getClothesState(String clothesID,OnGetClothesStateSuccessListener listener);
}
