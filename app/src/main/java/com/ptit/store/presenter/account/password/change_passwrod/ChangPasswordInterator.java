package com.ptit.store.presenter.account.password.change_passwrod;

import com.ptit.store.models.body.NewPassword;
import com.ptit.store.presenter.BaseInteractor;

public interface ChangPasswordInterator extends BaseInteractor{
    void changPassword(NewPassword newPassword,OnChangePasswordSuccessListener listener);
}
