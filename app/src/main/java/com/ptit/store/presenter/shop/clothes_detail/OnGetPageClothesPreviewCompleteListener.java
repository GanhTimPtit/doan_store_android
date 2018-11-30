package com.ptit.store.presenter.shop.clothes_detail;

import com.ptit.store.models.response.ClothesPreview;
import com.ptit.store.models.response.PageList;

/**
 * Created by KingIT on 4/23/2018.
 */

public interface OnGetPageClothesPreviewCompleteListener {
    void onGetPageClothesPreviewsSuccess(PageList<ClothesPreview> clothesPreviewPageList);
    void onMessageEror(String msg);
}
