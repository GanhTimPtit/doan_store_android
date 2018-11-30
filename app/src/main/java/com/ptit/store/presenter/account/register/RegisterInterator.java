package com.ptit.store.presenter.account.register;

import com.ptit.store.models.body.CustomerRegisterBody;
import com.ptit.store.presenter.BaseInteractor;

public interface RegisterInterator extends BaseInteractor{
    void register(String username,String password,
                  CustomerRegisterBody body,
                  OnRegisterCompleteListener listener);
}
