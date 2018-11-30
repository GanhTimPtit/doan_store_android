package com.ptit.store.presenter.account.password.forget_password;

public interface OnSuccessListener {
    void onSuccess();
    void onError(String msg);
    void onInvalidEmail();
}
