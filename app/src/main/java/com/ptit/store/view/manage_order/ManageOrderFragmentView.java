package com.ptit.store.view.manage_order;

import com.ptit.store.models.response.OrderPreview;

import java.util.List;

public interface ManageOrderFragmentView {
    void showLoadMoreProgress();
    void hideLoadMoreProgress();
    void enableLoadMore(boolean enable);
    void enableRefreshing(boolean enable);
    void showRefreshingProgress();
    void hideRefreshingProgress();
    void loadMoreOrderViewModel(List<OrderPreview> orderPreviews);
    void refreshOrderViewModel(List<OrderPreview> orderPreviews);
    void showNoResult();
    void hideNoResult();
    void showLoadingDialog();
    void hideLoadingDialog();

    void deleteOrderSuccess();
}
