package com.ptit.store.presenter.manage_order.order;


import com.ptit.store.models.response.OrderPreview;
import com.ptit.store.models.response.PageList;

public interface OnGetPageOrderSuccessListener {
    void onSuccess(PageList<OrderPreview> confirmOrderPreviewPageList);
    void onError(String msg);
}
