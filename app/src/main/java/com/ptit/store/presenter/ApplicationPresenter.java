package com.ptit.store.presenter;


public interface ApplicationPresenter extends BasePresenter {
    void changeOnlineState(boolean isOnline, OnRequestCompleteListener listener);
}
