package com.ptit.store.presenter.account.login;

import android.content.Context;

import com.ptit.store.R;
import com.ptit.store.common.Base64UtilAccount;
import com.ptit.store.common.ResponseCode;
import com.ptit.store.models.body.FacebookLoginBody;
import com.ptit.store.models.response.AccessToken;
import com.ptit.store.models.response.ResponseBody;
import com.ptit.store.services.ApiClient;
import com.ptit.store.services.ResponseObserver;
import com.ptit.store.services.retrofit.account.LoginServices;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class LoginInteratorImpl implements LoginInterator {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public LoginInteratorImpl(Context context) {
        this.context = context;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void login(String username, String password, OnLoginSuccessListener listener) {
        Observable<Response<ResponseBody<AccessToken>>> observable =
                ApiClient.getClient().create(LoginServices.class).checkLogin(Base64UtilAccount.getBase64Account(username, password),
                        "123");
        Disposable disposable = observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        response -> {
                            switch (response.code()) {
                                case ResponseCode.OK: {
                                    listener.onLoginSuccess(response.body().getData());
                                    break;
                                }
                                case ResponseCode.UNAUTHORIZATION: {
                                    listener.onError(context.getString(R.string.wrong_password_or_email));
                                    break;
                                }
                                case ResponseCode.FORBIDDEN: {
                                    listener.onAccounNotVerify();
                                    break;
                                }
                                case ResponseCode.NOT_FOUND: {
                                    listener.onError(context.getString(R.string.api_not_found));
                                    break;
                                }

                            }
                        },
                        error -> {
                            listener.onError(context.getString(R.string.server_error));
                        }
                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void facebookLogin(String facebookAccessToken, OnGetFacebookLoginStateListener listener) {
        Disposable disposable = ApiClient.getClient()
                .create(LoginServices.class)
                .facebookLogin(new FacebookLoginBody(facebookAccessToken))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResponseObserver<Response<ResponseBody<AccessToken>>>() {
                    @Override
                    public void onSuccess(Response<ResponseBody<AccessToken>> response) {
                        switch (response.code()) {
                            case ResponseCode.OK: {
                                listener.onFacebookLoginSuccess(response.body().getData());
                            }
                            break;

                            case ResponseCode.UNAUTHORIZATION: {
                                listener.onServerError(response.message());
                            }
                            break;

                            default: {
                                listener.onServerError(response.message());
                                break;
                            }
                        }
                    }

                    @Override
                    public void onServerError(String message) {
                        listener.onServerError(message);
                    }

                    @Override
                    public void onNetworkConnectionError() {
                        listener.onNetworkConnectionError();
                    }
                });
        compositeDisposable.add(disposable);
    }
    @Override
    public void onViewDestroy() {
        context = null;
        compositeDisposable.clear();
    }
}
