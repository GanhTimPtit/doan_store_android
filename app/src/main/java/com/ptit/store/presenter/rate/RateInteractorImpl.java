package com.ptit.store.presenter.rate;

import android.content.Context;

import com.ptit.store.common.ResponseCode;
import com.ptit.store.common.UserAuth;
import com.ptit.store.models.body.RateBody;
import com.ptit.store.models.response.ResponseBody;
import com.ptit.store.services.ApiClient;
import com.ptit.store.services.retrofit.rate.RateService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class RateInteractorImpl implements RateInteractor {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public RateInteractorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void rateShop(RateBody rateBody, OnRateSuccessListener listener) {
        Observable<Response<ResponseBody<String>>> observable =
                ApiClient.getClient().create(RateService.class).rateShop(
                        UserAuth.getBearerToken(context), rateBody);
        Disposable disposable = observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        response -> {
                            switch (response.code()) {
                                case ResponseCode.OK: {
                                    listener.onRateSuccess();
                                    break;
                                }
                                case ResponseCode.NOT_FOUND: {
                                    listener.onError(response.message());
                                    break;
                                }
                            }
                        },
                        error -> {
                            listener.onError(error.getMessage());
                        }
                );

        compositeDisposable.add(disposable);
    }

    @Override
    public void onViewDestroy() {
        context = null;
        compositeDisposable.clear();
    }
}
