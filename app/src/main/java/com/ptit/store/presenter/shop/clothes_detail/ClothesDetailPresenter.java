package com.ptit.store.presenter.shop.clothes_detail;

import com.ptit.store.models.body.RateClothesBody;
import com.ptit.store.models.body.ItemBody;
import com.ptit.store.presenter.BasePresenter;

import java.util.Set;

/**
 * Created by KingIT on 4/23/2018.
 */

public interface ClothesDetailPresenter extends BasePresenter{
    void fetchClothesDetail(String clothesID);
    void saveClothes(String clothesID);
    void deleteSavedClothes(String clothesID);
    void firstFetchSimilarClothes(String clothesID);
    void loadMoreSimilarClothes(String clothesID);

    void firstFetchRecommendClothes(String clothesID);
    void loadMoreRecommendClothes(String clothesID);

    void rateClothes(String clothesID, RateClothesBody rateClothesBody);
    void getAllRateClothes(String clothesID);
    void getClothesState(String clothesID);
}
