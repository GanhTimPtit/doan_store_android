package com.ptit.store.view.history_order;

import com.ptit.store.models.response.OrderPreview;

import java.util.List;

public interface HistoryOrderView {
    void showLoadMoreProgress();
    void hideLoadMoreProgress();
    void enableLoadMore(boolean enable);
    void enableRefreshing(boolean enable);
    void showRefreshingProgress();
    void hideRefreshingProgress();
    void loadMoreOrderViewModel(List<OrderPreview> saveClothesPreviews);
    void refreshOrderViewModel(List<OrderPreview> saveClothesViews);
    void showNoResult();
    void hideNoResult();
    void showLoadingDialog();
    void hideLoadingDialog();
}
