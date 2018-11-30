package com.ptit.store.presenter.manage_order.order;


import com.ptit.store.presenter.BaseInteractor;
import com.ptit.store.presenter.OnRequestCompleteListener;

import java.util.Set;

public interface GetOrderInteractor extends BaseInteractor {
    void callPageOrderPreview(int status, int pageIndex, int pageSize, OnGetPageOrderSuccessListener listener);
    void callDeleteOrder(String orderID, OnDeleteOrderSuccessListener listener);
}
