package com.ptit.store.presenter.chat;


import com.ptit.store.presenter.BaseInteractor;
import com.ptit.store.presenter.OnRequestCompleteListener;

public interface ChatInteractor extends BaseInteractor {
    void sendMessage(String message, OnRequestCompleteListener listener);
    void registerOnMessageChangedListener(OnMessageChangedListener listener);
    void unregisterOnMessageChangedListener();

  //  void getFriends(int pageIndex, int pageSize, OnGetMessageCompleteListener listener);
}
