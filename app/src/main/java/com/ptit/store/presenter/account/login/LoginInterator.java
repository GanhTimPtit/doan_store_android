package com.ptit.store.presenter.account.login;

import com.ptit.store.presenter.BaseInteractor;

public interface LoginInterator extends BaseInteractor {
    void login(String username,String password,OnLoginSuccessListener listener);
    void facebookLogin(String facebookAccessToken, OnGetFacebookLoginStateListener listener);
}
