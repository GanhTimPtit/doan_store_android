package com.ptit.store.presenter.account.login;

import com.ptit.store.models.response.AccessToken;
import com.ptit.store.presenter.BaseRequestListener;

public interface OnGetFacebookLoginStateListener extends BaseRequestListener {
    void onFacebookLoginSuccess(AccessToken accessToken);
}
