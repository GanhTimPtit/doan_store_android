package com.ptit.store.presenter.history;

import com.ptit.store.models.response.PageList;
import com.ptit.store.models.response.OrderPreview;

public interface OnGetPageHistoryOrderSuccessListener {
    void onSuccess(PageList<OrderPreview> orderViewModelPageList);
    void onError(String msg);
}
