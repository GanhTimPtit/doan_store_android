package com.ptit.store.presenter.profile.edit_profile;

import com.ptit.store.models.response.Profile;

public interface OnEditSuccessListener {
    void onSuccess(Profile profile);
    void onError(String message);
}
