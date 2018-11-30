package com.ptit.store.view.shop.clothes_detail;

import com.ptit.store.models.response.ClothesPreview;
import com.ptit.store.models.response.ClothesViewModel;
import com.ptit.store.models.response.RateClothesViewModel;

import java.util.List;

/**
 * Created by KingIT on 4/23/2018.
 */

public interface ClothesDetailActivityView {
    void showProgress();
    void hideProgress();
    void showClothesDetail(ClothesViewModel clothesViewModel);
    void showErrorLoading(String message);


    void showProgressSimilarClothes();
    void hideProgressSimilarClothes();

    void showErrorSimilarClothes();
    void hideErrorSimilarClothes();

    void showProgressRecommendClothes();
    void hideProgressRecommendClothes();

    void showErrorRecommendClothes();
    void hideErrorRecommendClothes();

    void showSimilarLoadingMoreProgress();
    void hideSimilarLoadingMoreProgress();

    void showRecommendLoadingMoreProgress();
    void hideRecommendLoadingMoreProgress();

    void enableLoadingMore(boolean enable);

    void enableLoadingMoreRecommend(boolean enable);

    void refreshSimilarClothes(List<ClothesPreview> similarClothes);
    void loadmoreSimilarClothes(List<ClothesPreview> similarClothes);

    void refreshRecommendClothes(List<ClothesPreview> similarClothes);
    void loadmoreRecommendClothes(List<ClothesPreview> similarClothes);

    void switchButtonSaveJobToSaved();
    void switchButtonSaveJobToUnSaved();

    void showListSimilarClothes();
    void hideListSimilarClothes();

    void showListRecommendClothes();
    void hideListRecommendClothes();

    void hideRatingDialog();

    void getAllRateClothes(List<RateClothesViewModel> rateClothesViewModelList);

    void payAndBackToHomeScreen();

    void showClothesState(boolean state);
}
