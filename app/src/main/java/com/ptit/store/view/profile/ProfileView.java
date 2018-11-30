package com.ptit.store.view.profile;

import com.ptit.store.models.response.Profile;

public interface ProfileView {
    void showLoadingDialog();
    void hideLoadingDialog();

    void showProfile(Profile profile);
}
