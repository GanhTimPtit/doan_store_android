package com.ptit.store.presenter.shop.clothes_detail;

import com.ptit.store.models.response.RateClothesViewModel;

import java.util.List;

public interface OnGetPageRateClothesSuccessListener {
    void onGetRateClothesSuccess(List<RateClothesViewModel> rateClothesViewModelList);
    void onError(String msg);
}
