package com.ptit.store.presenter.manage_order.detail_order;

import android.content.Context;


import com.ptit.store.R;
import com.ptit.store.common.ResponseCode;
import com.ptit.store.common.UserAuth;
import com.ptit.store.services.ApiClient;
import com.ptit.store.services.retrofit.history.ManageOrderService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by KingIT on 4/22/2018.
 */

public class GetItemsInteratorImpl implements GetItemsInteractor{
    private Context context;
    private CompositeDisposable compositeDisposable;

    public GetItemsInteratorImpl(Context context) {
        this.context = context;
        compositeDisposable= new CompositeDisposable();
    }

    @Override
    public void getItemPreviews(String orderID, OnGetItemsSuccessListener listener) {
        Disposable disposable = ApiClient.getClient().create(ManageOrderService.class)
                .getAllItemsPreview(UserAuth.getBearerToken(context),orderID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(response -> {
                    switch (response.code()) {
                        case ResponseCode.OK: {
                            listener.onGetListPreviewsSuccess(response.body().getData());
                            break;
                        }
                        case ResponseCode.UNAUTHORIZATION:{
                            listener.onMessageEror(response.message());
                            break;
                        }
                        case ResponseCode.NOT_FOUND: {
                            listener.onMessageEror(response.message());
                            break;
                        }
                        default:{
                            listener.onMessageEror(response.message());
                            break;
                        }
                    }
        }, error -> {
            listener.onMessageEror(context.getString(R.string.server_error));
        });
        compositeDisposable.add(disposable);
    }

    @Override
    public void onViewDestroy() {
        this.compositeDisposable.clear();
    }
}
