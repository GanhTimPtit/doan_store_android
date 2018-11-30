package com.ptit.store.presenter;


public interface OnRequestCompleteListener {
    void onSuccess();
    void onServerError(String message);
}
