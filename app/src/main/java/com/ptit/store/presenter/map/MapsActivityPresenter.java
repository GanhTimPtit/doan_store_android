package com.ptit.store.presenter.map;

import com.ptit.store.models.body.LatLngBody;
import com.ptit.store.presenter.BasePresenter;

public interface MapsActivityPresenter extends BasePresenter {
    void getStoreBranch(LatLngBody latLngBody);
}
