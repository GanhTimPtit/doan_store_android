package com.ptit.store.presenter.maps;

import com.ptit.store.presenter.BaseInteractor;



public interface LocationPickerInteractor extends BaseInteractor {
    void getMapAddress(double lat, double lon, OnGetMapAddressCompleteListener listener);
    void listAllMapAddress(String queryAddress, OnGetMapAddressCompleteListener listener);
    void cancelGetMapAddressRequest();
}
