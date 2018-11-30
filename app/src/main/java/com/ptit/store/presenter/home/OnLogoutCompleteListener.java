package com.ptit.store.presenter.home;


public interface OnLogoutCompleteListener  {
    void onLogoutSuccess();
    void onServerError(String message);
}
