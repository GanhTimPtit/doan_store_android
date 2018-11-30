package com.ptit.store.presenter.chat;


import com.ptit.store.models.model_chat.UserMessage;

public interface OnMessageChangedListener {
    void onMessageAdded(UserMessage userMessage);
    void onMessageModified(UserMessage userMessage);
}
