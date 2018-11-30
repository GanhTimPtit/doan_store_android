package com.ptit.store.presenter.checkout;

import com.ptit.store.models.body.ItemBody;
import com.ptit.store.models.body.OrderBody;
import com.ptit.store.presenter.BasePresenter;

import java.util.Set;

public interface CheckoutPresenter extends BasePresenter {
    void fetchOrder(OrderBody orderBody);
}
