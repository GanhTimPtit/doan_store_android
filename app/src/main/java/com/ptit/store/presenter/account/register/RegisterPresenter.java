package com.ptit.store.presenter.account.register;

import com.ptit.store.models.body.CustomerRegisterBody;
import com.ptit.store.presenter.BasePresenter;

public interface RegisterPresenter extends BasePresenter{
    void validateUsernameAndPassword(String username, String password, CustomerRegisterBody body);


}
