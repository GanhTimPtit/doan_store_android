package com.ptit.store.presenter.splash;


import com.ptit.store.models.response.AccessTokenPair;
import com.ptit.store.presenter.BaseRequestListener;

public interface OnRefreshAccessTokenSuccessListener extends BaseRequestListener {
    void oneRefreshTokenSuccess(AccessTokenPair accessTokenPair);
    void onRefreshTokenExpired();
}
