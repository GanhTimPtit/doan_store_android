package com.ptit.store.presenter.account.resend_email;

public interface OnVerifyEmailSuccessListener {
    void onSuccess(String username);
    void onError(String message);
}
