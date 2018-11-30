package com.ptit.store.presenter.history;

import com.ptit.store.presenter.BaseInteractor;

public interface HistoryOrderInteractor extends BaseInteractor {
    void getAllHistoryOrder(int pageIndex, int pageSize, OnGetPageHistoryOrderSuccessListener listener);
}
