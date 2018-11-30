package com.ptit.store.presenter.account.resend_email;

import com.ptit.store.presenter.BaseInteractor;

public interface VerifyEmailInteractor extends BaseInteractor {
    void verifyEmail(String username,OnVerifyEmailSuccessListener listener);
}
