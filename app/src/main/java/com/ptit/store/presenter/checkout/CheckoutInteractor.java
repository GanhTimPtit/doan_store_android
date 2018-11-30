package com.ptit.store.presenter.checkout;

import com.ptit.store.models.body.ItemBody;
import com.ptit.store.models.body.OrderBody;
import com.ptit.store.presenter.BaseInteractor;
import com.ptit.store.presenter.OnRequestCompleteListener;

import java.util.Set;

public interface CheckoutInteractor extends BaseInteractor{
    void checkout(OrderBody orderBody, OnRequestCompleteListener listener);
}
