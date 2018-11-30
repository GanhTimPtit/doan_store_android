package com.ptit.store.presenter.feedback;

public interface OnSendFeedbackSuccessListener {
    void onSuccess();
    void onError(String message);
}
