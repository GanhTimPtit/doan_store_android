package com.ptit.store.presenter.account.password.forget_password;

import com.ptit.store.presenter.BasePresenter;

public interface ForgetPasswordPresenter extends BasePresenter {
    void validateEmail(String email);
}
