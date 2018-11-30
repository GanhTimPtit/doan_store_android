package com.ptit.store.presenter.splash;

import com.ptit.store.presenter.BaseInteractor;

public interface GetAppVersionInteractor extends BaseInteractor {
    void refreshAccessToken(OnRefreshAccessTokenSuccessListener listener);
    void getVersionApp(OnGetAppVersionSuccessListener listener);
}
