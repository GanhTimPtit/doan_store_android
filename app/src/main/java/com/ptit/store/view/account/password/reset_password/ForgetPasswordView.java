package com.ptit.store.view.account.password.reset_password;

public interface ForgetPasswordView {
    void showEmailInputError();
    void showEmailInputInvalid();
    void showLoadingDialog();
    void hideLoadingDialog();
    void backToHome();
}
