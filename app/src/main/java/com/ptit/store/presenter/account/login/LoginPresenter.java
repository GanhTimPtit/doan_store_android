package com.ptit.store.presenter.account.login;

import com.ptit.store.presenter.BasePresenter;

public interface LoginPresenter extends BasePresenter{
    void validateUsernameAndPassword(String username,String password);
    void validateFacebookLogin(String facebookAccessToken);
}
