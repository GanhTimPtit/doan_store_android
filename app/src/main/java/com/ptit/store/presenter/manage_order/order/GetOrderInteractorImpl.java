package com.ptit.store.presenter.manage_order.order;

import android.content.Context;


import com.ptit.store.common.ResponseCode;
import com.ptit.store.common.UserAuth;
import com.ptit.store.models.response.OrderPreview;
import com.ptit.store.models.response.PageList;
import com.ptit.store.models.response.ResponseBody;
import com.ptit.store.presenter.OnRequestCompleteListener;
import com.ptit.store.services.ApiClient;
import com.ptit.store.services.retrofit.history.ManageOrderService;

import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class GetOrderInteractorImpl implements GetOrderInteractor {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public GetOrderInteractorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void callPageOrderPreview(int status, int pageIndex, int pageSize, OnGetPageOrderSuccessListener listener) {
        Observable<Response<ResponseBody<PageList<OrderPreview>>>> observable=null;
        if(status!=-1) {
            observable = ApiClient.getClient().create(ManageOrderService.class).getPageOrder(UserAuth.getBearerToken(context), status, pageIndex, pageSize, null, null);
        }else{
            observable = ApiClient.getClient().create(ManageOrderService.class).getPageOrderComplete(UserAuth.getBearerToken(context), pageIndex, pageSize, null, null);
        }
        Disposable disposable = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response->{
                            switch (response.code()){
                                case ResponseCode.OK:{
                                    listener.onSuccess(response.body().getData());
                                    break;
                                }
                                default:{
                                    listener.onError(response.message());
                                    break;
                                }
                            }
                        },
                        error->{
                            listener.onError(error.getMessage());
                        }
                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void callDeleteOrder(String orderID, OnDeleteOrderSuccessListener listener) {
        Disposable disposable = ApiClient.getClient()
                .create(ManageOrderService.class)
                .deleteOrder(UserAuth.getBearerToken(context), orderID)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response->{
                            switch (response.code()){
                                case ResponseCode.OK:{
                                    listener.onSuccess();
                                    break;
                                }
                                case ResponseCode.UNAUTHORIZATION:{
                                    listener.onServerError(response.message());
                                    break;
                                }
                                case ResponseCode.FORBIDDEN:{
                                    listener.onForbiddenResponse(response.message());
                                    break;
                                }
                                default:{
                                    listener.onServerError(response.message());
                                    break;
                                }
                            }
                        },
                        error->{
                            listener.onServerError(error.getMessage());
                        }
                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void onViewDestroy() {
        compositeDisposable.clear();
    }
}
