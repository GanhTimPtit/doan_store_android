package com.ptit.store.presenter.account.password.forget_password;

import android.content.Context;

import com.ptit.store.common.Constants;
import com.ptit.store.common.ResponseCode;
import com.ptit.store.common.UserAuth;
import com.ptit.store.common.Utils;
import com.ptit.store.models.response.ResponseBody;
import com.ptit.store.services.ApiClient;
import com.ptit.store.services.retrofit.account.ForgetPasswordService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ForgetPasswordInteratorImpl implements ForgetPasswordInterator {
    Context context;
    CompositeDisposable compositeDisposable;

    public ForgetPasswordInteratorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void ForgetPassword(String email, OnSuccessListener listener) {
        String customerID = Utils.getSharePreferenceValues(context, Constants.CUSTOMER_ID);
        Observable<Response<ResponseBody<String>>> observable =
                ApiClient.getClient().create(ForgetPasswordService.class).forgetPassword(UserAuth.getBearerToken(context), email);

        Disposable disposable = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            switch (response.code()) {
                                case ResponseCode.OK: {
                                    listener.onSuccess();
                                    break;
                                }
                                case ResponseCode.GONE: {
                                    listener.onInvalidEmail();
                                    break;
                                }
                                default: {
                                    listener.onError(response.message());
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
        compositeDisposable.clear();
    }
}
