package com.ptit.store.presenter.profile;

import com.ptit.store.models.response.Profile;

public interface OnGetProfileSuccessListener {
    void onSuccess(Profile profile);
    void onError(String message);
}
