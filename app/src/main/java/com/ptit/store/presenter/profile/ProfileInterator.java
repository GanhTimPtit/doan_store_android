package com.ptit.store.presenter.profile;

import com.ptit.store.presenter.BaseInteractor;

public interface ProfileInterator extends BaseInteractor {
    void getProfile(String customerID,OnGetProfileSuccessListener listener);
}
