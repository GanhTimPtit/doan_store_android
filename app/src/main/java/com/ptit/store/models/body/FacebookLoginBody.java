package com.ptit.store.models.body;

import java.io.Serializable;

public class FacebookLoginBody implements Serializable {
    private String accessToken;

    public FacebookLoginBody(String accessToken) {
        this.accessToken = accessToken;
    }

    public FacebookLoginBody() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
