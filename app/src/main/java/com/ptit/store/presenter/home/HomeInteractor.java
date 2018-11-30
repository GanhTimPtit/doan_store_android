package com.ptit.store.presenter.home;


import com.ptit.store.presenter.BaseInteractor;

public interface HomeInteractor extends BaseInteractor {
    void getUserHeaderProfile(OnGetHeaderProfileCompleteListener listener);
    void logout(String userID, OnLogoutCompleteListener listener);
}
