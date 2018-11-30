package com.ptit.store.presenter.shop.clothes_detail;

import com.ptit.store.models.response.ClothesViewModel;

/**
 * Created by KingIT on 4/23/2018.
 */

public interface OnGetClothesDetailCompleteListener {
    void onGetClothesDetailComplete(ClothesViewModel clothesViewModel);
    void onMessageEror(String msg);
}
