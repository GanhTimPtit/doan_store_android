package com.ptit.store.presenter.maps;


import com.ptit.store.models.google_map.GoogleAddressResponse;
import com.ptit.store.presenter.BaseRequestListener;

public interface OnGetMapAddressCompleteListener extends BaseRequestListener {
    void onGetAddressSuccess(GoogleAddressResponse addressResponse);
}
