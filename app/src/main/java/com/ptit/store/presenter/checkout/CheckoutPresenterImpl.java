package com.ptit.store.presenter.checkout;

import android.content.Context;
import android.widget.Toast;

import com.ptit.store.R;

import com.ptit.store.models.body.OrderBody;
import com.ptit.store.presenter.OnRequestCompleteListener;
import com.ptit.store.services.ManageCart;

import com.ptit.store.view.order.OrderActivityView;

import java.util.Set;

public class CheckoutPresenterImpl implements CheckoutPresenter {
    private Context context;
    private OrderActivityView orderActivityView;
    private CheckoutInteractor checkoutInteractor;

    public CheckoutPresenterImpl(Context context, OrderActivityView orderActivityView) {
        this.context = context;
        this.orderActivityView = orderActivityView;
        this.checkoutInteractor = new CheckoutInteractorImpl(context);
    }

    @Override
    public void fetchOrder(OrderBody orderBody) {
        orderActivityView.showProgress();
        checkoutInteractor.checkout(orderBody, new OnRequestCompleteListener() {
            @Override
            public void onSuccess() {
                orderActivityView.hideProgress();
                orderActivityView.backToHomeScreen();
                ManageCart.getCart().getItems().clear();
            }

            @Override
            public void onServerError(String message) {
                orderActivityView.hideProgress();
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onViewDestroy() {
        checkoutInteractor.onViewDestroy();
    }
}
