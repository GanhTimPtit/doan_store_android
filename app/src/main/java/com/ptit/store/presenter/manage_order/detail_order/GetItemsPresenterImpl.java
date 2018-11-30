package com.ptit.store.presenter.manage_order.detail_order;

import android.content.Context;


import com.ptit.store.common.ToastUtils;
import com.ptit.store.models.response.ItemPreview;
import com.ptit.store.view.manage_order.detail_order.DetailOrderActivityView;

import java.util.List;


/**
 * Created by KingIT on 4/22/2018.
 */

public class GetItemsPresenterImpl implements GetItemsPresenter{
    private Context context;
    private DetailOrderActivityView detailOrderActivityView;
    private GetItemsInteractor getClothesInteractor;
    private int currentPage = 0;

    public GetItemsPresenterImpl(Context context, DetailOrderActivityView detailOrderActivityView) {
        this.context = context;
        this.detailOrderActivityView = detailOrderActivityView;
        this.getClothesInteractor= new GetItemsInteratorImpl(context);
    }

    @Override
    public void onViewDestroy() {
        context = null;
        getClothesInteractor.onViewDestroy();
    }

    @Override
    public void showItemPreviews(String orderID) {
        detailOrderActivityView.showLoadingDialog();

        getClothesInteractor.getItemPreviews(orderID, new OnGetItemsSuccessListener() {
                    @Override
                    public void onGetListPreviewsSuccess(List<ItemPreview> itemPreviews) {
                        detailOrderActivityView.showItemPreview(itemPreviews);
                        detailOrderActivityView.hideLoadingDialog();
                    }

                    @Override
                    public void onMessageEror(String msg) {
                        detailOrderActivityView.hideLoadingDialog();
                        ToastUtils.quickToast(context, msg);
                    }
                });
    }
}
