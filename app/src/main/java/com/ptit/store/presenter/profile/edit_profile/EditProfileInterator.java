package com.ptit.store.presenter.profile.edit_profile;

import com.ptit.store.models.body.ProfileBody;
import com.ptit.store.presenter.BaseInteractor;

public interface EditProfileInterator extends BaseInteractor {
    void updateProfile(ProfileBody profileBody, OnEditSuccessListener listener);
}
