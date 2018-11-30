package com.ptit.store.presenter.rate;

import com.ptit.store.models.body.RateBody;
import com.ptit.store.presenter.BaseInteractor;

public interface RateInteractor  extends BaseInteractor{
    void rateShop(RateBody rateBody,OnRateSuccessListener listener);
}
