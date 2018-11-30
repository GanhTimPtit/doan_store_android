package com.ptit.store.services.event_bus;

import com.ptit.store.models.response.Profile;

public class ProfileChangeEvent {
    Profile profile;

    public ProfileChangeEvent(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
