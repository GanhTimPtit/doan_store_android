package com.ptit.store.presenter.chat;


import com.ptit.store.presenter.BasePresenter;

public interface ChatPresenter extends BasePresenter {
    void registerOnMessageAddedListener();
    void unregisterOnMessageAddedListener();

    void validateSendingMessage(String message);
}
