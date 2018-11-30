package com.ptit.store.presenter.account.password.forget_password;

import com.ptit.store.presenter.BaseInteractor;

public interface ForgetPasswordInterator extends BaseInteractor {
    void ForgetPassword(String email,OnSuccessListener listener);
}
