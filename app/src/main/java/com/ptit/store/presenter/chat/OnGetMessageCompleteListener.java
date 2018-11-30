package com.ptit.store.presenter.chat;


import com.ptit.store.models.model_chat.Message;

import java.util.List;

public interface OnGetMessageCompleteListener {
    void onGetMessagesSuccess(List<Message> messages);
    void onError(String message);
}
