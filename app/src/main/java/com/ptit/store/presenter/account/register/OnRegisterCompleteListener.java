package com.ptit.store.presenter.account.register;

public interface OnRegisterCompleteListener  {
    void onRegisterSuccess(String username);
    void onError(String message);
    void onAccountExist();
}
