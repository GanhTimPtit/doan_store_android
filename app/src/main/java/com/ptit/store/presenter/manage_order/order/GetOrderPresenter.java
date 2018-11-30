package com.ptit.store.presenter.manage_order.order;

import com.ptit.store.presenter.BasePresenter;

import java.util.Set;

public interface GetOrderPresenter extends BasePresenter {
    void onLoadmoreOrder(int status);
    void onRefreshOrder(int status);
    void deleteOrder(String orderID);
}
