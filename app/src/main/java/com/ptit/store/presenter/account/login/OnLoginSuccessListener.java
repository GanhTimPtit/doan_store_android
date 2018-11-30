package com.ptit.store.presenter.account.login;

import com.ptit.store.models.response.AccessToken;

public interface OnLoginSuccessListener {
    void onLoginSuccess(AccessToken accessToken);
    void onAccounNotVerify();
    void onError(String message);
}
