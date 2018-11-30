package com.ptit.store.presenter.manage_order.order;

import android.content.Context;


import com.ptit.store.R;
import com.ptit.store.common.Constants;
import com.ptit.store.common.ToastUtils;
import com.ptit.store.models.response.OrderPreview;
import com.ptit.store.models.response.PageList;
import com.ptit.store.view.manage_order.ManageOrderFragmentView;

public class GetOrderPresenterImpl implements GetOrderPresenter {
    private Context context;
    private ManageOrderFragmentView manageOrderFragmentView;
    private GetOrderInteractor getOrderInteractor;
    int currenIndex = 0;
    public GetOrderPresenterImpl(Context context, ManageOrderFragmentView manageOrderFragmentView) {
        this.context = context;
        this.manageOrderFragmentView = manageOrderFragmentView;
        this.getOrderInteractor = new GetOrderInteractorImpl(context);
    }

    @Override
    public void onLoadmoreOrder(int status) {
        manageOrderFragmentView.showLoadMoreProgress();
        manageOrderFragmentView.enableRefreshing(false);
        getOrderInteractor.callPageOrderPreview(status,currenIndex + 1, Constants.PAGE_SIZE,
                new OnGetPageOrderSuccessListener() {
                    @Override
                    public void onSuccess(PageList<OrderPreview> confirmOrderPreviewPageList) {
                        currenIndex++;
                        manageOrderFragmentView.hideLoadMoreProgress();
                        manageOrderFragmentView.enableRefreshing(true);
                        if (confirmOrderPreviewPageList.getPageIndex() == confirmOrderPreviewPageList.getTotalPage() - 1) {
                            manageOrderFragmentView.enableLoadMore(false);
                        }
                        manageOrderFragmentView.loadMoreOrderViewModel(confirmOrderPreviewPageList.getResults());
                    }

                    @Override
                    public void onError(String msg) {
                        manageOrderFragmentView.hideLoadMoreProgress();
                        manageOrderFragmentView.enableRefreshing(true);
                        ToastUtils.quickToast(context, R.string.error_message);
                    }
                });
    }

    @Override
    public void onRefreshOrder(int status) {
        manageOrderFragmentView.showRefreshingProgress();
        manageOrderFragmentView.enableRefreshing(true);
        getOrderInteractor.callPageOrderPreview(status, 0, Constants.PAGE_SIZE, new OnGetPageOrderSuccessListener() {
            @Override
            public void onSuccess(PageList<OrderPreview> confirmOrderPreviewPageList) {
                currenIndex=0;
                if (confirmOrderPreviewPageList.getPageIndex() == confirmOrderPreviewPageList.getTotalPage() - 1) {
                    manageOrderFragmentView.enableLoadMore(false);
                }
                manageOrderFragmentView.hideRefreshingProgress();
                manageOrderFragmentView.enableLoadMore(true);
                manageOrderFragmentView.refreshOrderViewModel(confirmOrderPreviewPageList.getResults());
                if(confirmOrderPreviewPageList.getTotalItem()==0){
                    manageOrderFragmentView.showNoResult();
                }else {
                    manageOrderFragmentView.hideNoResult();
                }
            }

            @Override
            public void onError(String msg) {
                manageOrderFragmentView.hideNoResult();
                manageOrderFragmentView.hideRefreshingProgress();
                manageOrderFragmentView.enableRefreshing(true);
                ToastUtils.quickToast(context, msg);
            }
        });
    }

    @Override
    public void deleteOrder(String orderID) {
        manageOrderFragmentView.showLoadingDialog();
        getOrderInteractor.callDeleteOrder(orderID, new OnDeleteOrderSuccessListener() {
            @Override
            public void onForbiddenResponse(String msg) {
                manageOrderFragmentView.hideLoadingDialog();
                ToastUtils.quickToast(context,msg);
            }

            @Override
            public void onSuccess() {
                manageOrderFragmentView.hideLoadingDialog();
                manageOrderFragmentView.deleteOrderSuccess();
                ToastUtils.quickToast(context, "Hủy đơn hàng thành công");
            }

            @Override
            public void onServerError(String message) {
                manageOrderFragmentView.hideLoadingDialog();
                ToastUtils.quickToast(context,message);
            }
        });
    }

    @Override
    public void onViewDestroy() {
        getOrderInteractor.onViewDestroy();
    }
}
