package com.ptit.store.presenter.map;

import com.ptit.store.models.body.LatLngBody;
import com.ptit.store.presenter.BaseInteractor;

public interface MapsActivityInterator  extends BaseInteractor{
    void getAllStoreBranch(LatLngBody latLngBody,OnGetSuccessListener listener);
}
