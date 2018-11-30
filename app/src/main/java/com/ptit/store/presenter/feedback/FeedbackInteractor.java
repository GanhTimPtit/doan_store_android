package com.ptit.store.presenter.feedback;

import com.ptit.store.presenter.BaseInteractor;

public interface FeedbackInteractor extends BaseInteractor {
    void sendFeedback(String feedback,OnSendFeedbackSuccessListener listener);
}
