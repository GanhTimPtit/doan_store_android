package com.ptit.store.presenter.checkout;

import android.content.Context;

import com.ptit.store.common.ResponseCode;
import com.ptit.store.common.UserAuth;
import com.ptit.store.models.body.OrderBody;
import com.ptit.store.models.response.ResponseBody;
import com.ptit.store.presenter.OnRequestCompleteListener;
import com.ptit.store.services.ApiClient;
import com.ptit.store.services.retrofit.checkout.CheckoutService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class CheckoutInteractorImpl implements CheckoutInteractor{
    private Context context;
    private CompositeDisposable compositeDisposable;

    public CheckoutInteractorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void checkout(OrderBody orderBody, OnRequestCompleteListener listener) {
        Observable<Response<ResponseBody<String>>> observable = ApiClient.getClient().create(CheckoutService.class)
                .insertOrder(UserAuth.getBearerToken(context),orderBody);
        Disposable disposable = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response->{
                            switch (response.code()){
                                case ResponseCode.OK:{
                                    listener.onSuccess();
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
