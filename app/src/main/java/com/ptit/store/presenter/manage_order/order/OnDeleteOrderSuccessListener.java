package com.ptit.store.presenter.manage_order.order;

import com.ptit.store.presenter.OnRequestCompleteListener;

public interface OnDeleteOrderSuccessListener extends OnRequestCompleteListener {
    void onForbiddenResponse(String msg);
}
