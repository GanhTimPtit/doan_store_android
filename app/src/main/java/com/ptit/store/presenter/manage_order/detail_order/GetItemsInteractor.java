package com.ptit.store.presenter.manage_order.detail_order;


import com.ptit.store.presenter.BaseInteractor;

/**
 * Created by KingIT on 4/22/2018.
 */

public interface GetItemsInteractor extends BaseInteractor {
    void getItemPreviews(String orderID,
                         OnGetItemsSuccessListener listener);
}
