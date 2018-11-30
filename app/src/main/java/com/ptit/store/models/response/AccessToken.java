package com.ptit.store.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AccessToken {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("accessToken")
    @Expose
    private String accessToken;
    @SerializedName("refreshToken")
    @Expose
    private String refreshToken;
    @SerializedName("expireIn")
    @Expose
    private long expireIn;
    @SerializedName("deviceID")
    @Expose
    private String deviceID;

    public AccessToken(FacebookAuthResponse data) {
        setAccessToken(data.getAccessToken());
        setRefreshToken(data.getRefreshToken());
        setExpireIn(data.getExpireIn());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public long getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(long expireIn) {
        this.expireIn = expireIn;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }
}
