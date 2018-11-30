package com.ptit.store.presenter.profile.edit_profile;

import android.net.Uri;

import com.ptit.store.presenter.BasePresenter;

public interface EditProfilePresenter extends BasePresenter{
    void validateProfile(Uri uri,
                         String firstName, String lastName,
                         String phone,
                         String address,
                         String identityCard,
                         String avatarUrl,
                         int gender,
                         long birthday,
                         String email
    );
}
