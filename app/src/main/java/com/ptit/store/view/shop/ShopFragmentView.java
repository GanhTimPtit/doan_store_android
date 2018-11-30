package com.ptit.store.view.shop;

import com.ptit.store.models.response.ClothesPreview;
import com.ptit.store.models.response.PageList;

/**
 * Created by KingIT on 4/22/2018.
 */

public interface ShopFragmentView {
    void showLoadMoreProgress();
    void hideLoadMoreProgress();
    void enableLoadMore(boolean enable);
    void enableRefreshing(boolean enable);
    void showRefreshingProgress();
    void hideRefreshingProgress();
    void addClothesPreviews(PageList<ClothesPreview> clothesPreviewPageList);
    void refreshClothesPreview(PageList<ClothesPreview> clothesPreviewPageList);
}
