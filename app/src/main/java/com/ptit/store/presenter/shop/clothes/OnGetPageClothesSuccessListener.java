package com.ptit.store.presenter.shop.clothes;

import com.ptit.store.models.response.ClothesPreview;
import com.ptit.store.models.response.PageList;

/**
 * Created by KingIT on 4/22/2018.
 */

public interface OnGetPageClothesSuccessListener {
    void onGetPageClothesPreviewsSuccess(PageList<ClothesPreview> clothesPreviewPageList);
    void onMessageEror(String msg);
}
