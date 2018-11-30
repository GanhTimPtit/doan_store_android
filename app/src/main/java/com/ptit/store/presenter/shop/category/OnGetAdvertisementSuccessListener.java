package com.ptit.store.presenter.shop.category;

import com.ptit.store.models.Advertisement;
import com.ptit.store.presenter.BaseRequestListener;


import java.util.List;

public interface OnGetAdvertisementSuccessListener extends BaseRequestListener {
    void onGetAdvertisement(List<Advertisement> advertisementList);
    void onMessageEror(String msg);
}
