package com.ptit.store.view.account.login;

public interface LoginView  {
    void showLoadingDialog();
    void hideLoadingDialog();
    void showUserNameError();
    void showPasswordError();
    void backToHomeScreen(int resultCode);

}
