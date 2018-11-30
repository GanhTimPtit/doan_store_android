package com.ptit.store.presenter.splash;

import android.content.Context;

import com.ptit.store.common.Constants;
import com.ptit.store.common.ResponseCode;
import com.ptit.store.common.Utils;
import com.ptit.store.models.response.AccessTokenPair;
import com.ptit.store.models.response.ResponseBody;
import com.ptit.store.services.ApiClient;
import com.ptit.store.services.ResponseObserver;
import com.ptit.store.services.retrofit.account.RefreshAccessTokenService;
import com.ptit.store.services.retrofit.splash.SplashService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class GetAppVersionInteractorImpl implements GetAppVersionInteractor {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public GetAppVersionInteractorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void refreshAccessToken(OnRefreshAccessTokenSuccessListener listener) {
        String accessToken = Utils.getSharePreferenceValues(context,Constants.PRE_ACCESS_TOKEN_LOGIN);
        String refreshToken = Utils.getSharePreferenceValues(context,Constants.PRE_REFRESH_TOKEN_LOGIN);
        AccessTokenPair accessTokenPair = new AccessTokenPair(accessToken, refreshToken);
        Disposable disposable = ApiClient.getClient().create(RefreshAccessTokenService.class)
                .refreshAccessToken(accessTokenPair)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResponseObserver<Response<ResponseBody<AccessTokenPair>>>() {
                    @Override
                    public void onSuccess(Response<ResponseBody<AccessTokenPair>> response) {
                        switch (response.code()) {
                            case ResponseCode.OK: {
                                listener.oneRefreshTokenSuccess(response.body().getData());
                            }
                            break;

                            case ResponseCode.UNAUTHORIZATION: {
                                listener.onRefreshTokenExpired();
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
    public void getVersionApp(OnGetAppVersionSuccessListener listener) {
        Observable<Response<ResponseBody<Integer>>> observable = ApiClient.getClient().create(SplashService.class)
                .getAppVersion();
        Disposable disposable = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            switch (response.code()){
                                case ResponseCode.OK:{
                                    listener.onGetAppVerion(response.body().getData());


                                    break;
                                }
                                default:{
                                    if(!Utils.checkNetwork(context)){
                                        listener.onNetworkConnectionError();
                                    }else {
                                        listener.onServerError();
                                    }
                                }
                            }
                        },
                        error -> {
                            if(!Utils.checkNetwork(context)){
                                listener.onNetworkConnectionError();
                            }else {
                                listener.onServerMaintain();
                            }
                        }

                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void onViewDestroy() {
        compositeDisposable.clear();
    }
}
