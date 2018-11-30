package com.ptit.store.presenter.shop.clothes;

import com.ptit.store.presenter.BasePresenter;

/**
 * Created by KingIT on 4/22/2018.
 */

public interface GetPageClothesPresenter extends BasePresenter{
    void loadMoreClothesPreviews(String categoryID);
    void refreshClothesPreviews(String categoryID);
}
