package com.ptit.store.models.model_chat;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;



public class UserChat implements Serializable, Parcelable {
    public static final String FULL_NAME = "firstName";
    public static final String EMAIL = "email";
    public static final String IS_ONLINE = "online";

    private String email;
    private String firstName;
    private String lastName;
    private String avatarUrl;
    private String coverUrl;
    private boolean isOnline = false;

    public UserChat(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserChat(String email, String firstName, String lastName, String avatarUrl) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatarUrl = avatarUrl;
    }

    public UserChat() {
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public UserChat(Parcel in) {
        email = in.readString();
        firstName = in.readString();
        avatarUrl = in.readString();
        coverUrl = in.readString();
        isOnline = in.readByte() != 0;
    }

    public static final Creator<UserChat> CREATOR = new Creator<UserChat>() {
        @Override
        public UserChat createFromParcel(Parcel in) {
            return new UserChat(in);
        }

        @Override
        public UserChat[] newArray(int size) {
            return new UserChat[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(email);
        parcel.writeString(firstName);
        parcel.writeString(avatarUrl);
        parcel.writeString(coverUrl);
        parcel.writeByte((byte) (isOnline ? 1 : 0));
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", isOnline=" + isOnline +
                '}';
    }
}
