package com.ptit.store.presenter.shop.category;

import com.ptit.store.presenter.BaseInteractor;

public interface CategoryInterator extends BaseInteractor {
    void getCategory(OnGetCategorySuccessListener listener);
    void getAdvertisement(OnGetAdvertisementSuccessListener listener);
}
