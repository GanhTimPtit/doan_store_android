package com.ptit.store.view.manage_order.detail_order;


import com.ptit.store.models.response.ItemPreview;

import java.util.List;

public interface DetailOrderActivityView {
    void showLoadingDialog();
    void hideLoadingDialog();
    void showItemPreview(List<ItemPreview> itemPreviews);
}
