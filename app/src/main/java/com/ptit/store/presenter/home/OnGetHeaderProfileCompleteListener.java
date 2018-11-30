package com.ptit.store.presenter.home;


import com.ptit.store.models.response.HeaderProfile;

public interface OnGetHeaderProfileCompleteListener {
    void onGetHeaderProfileSuccess(HeaderProfile headerProfile);
    void onServerError(String message);
}
