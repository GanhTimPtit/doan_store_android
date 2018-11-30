package com.ptit.store.presenter.manage_order.detail_order;



import com.ptit.store.models.response.ItemPreview;

import java.util.List;

/**
 * Created by KingIT on 4/22/2018.
 */

public interface OnGetItemsSuccessListener {
    void onGetListPreviewsSuccess(List<ItemPreview> itemPreviewList);
    void onMessageEror(String msg);
}
