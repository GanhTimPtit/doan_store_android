package com.ptit.store.presenter.home;


import com.ptit.store.presenter.BasePresenter;

public interface HomePresenter extends BasePresenter {
    void fetchHeaderProfile();
//    void registerUnseenMessageListener();
//    void unregisterUnseenMessageListener();
    void logout();
}
