package com.ptit.store.view.splash;

public interface SplashView {
    void startProgress();
    boolean isInProgress();
    void completeLoading();
    void showServerErrorDialog();
    void showServerMaintainDialog();
    void showNoInternetConnectionDialog();
}
