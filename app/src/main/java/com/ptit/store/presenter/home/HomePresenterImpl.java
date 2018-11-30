package com.ptit.store.presenter.home;

import android.content.Context;

import com.google.firebase.messaging.FirebaseMessaging;
import com.ptit.store.MainView;
import com.ptit.store.R;
import com.ptit.store.common.ToastUtils;
import com.ptit.store.common.UserAuth;
import com.ptit.store.common.Utils;
import com.ptit.store.models.response.HeaderProfile;
import com.ptit.store.services.event_bus.UserAuthorizationChangedEvent;


import org.greenrobot.eventbus.EventBus;

import java.util.HashSet;
import java.util.Set;


public class HomePresenterImpl implements HomePresenter {
    private Context context;
    private MainView mainView;
    private HomeInteractor homeInteractor;
    private Set<String> unseenMessageRoomIDs;

    public HomePresenterImpl(Context context, MainView mainView) {
        this.context = context;
        this.mainView = mainView;
        this.homeInteractor = new HomeInteractorImpl(context);
        this.unseenMessageRoomIDs = new HashSet<>();
    }

    @Override
    public void logout() {
        mainView.showProgress();
        String userID = UserAuth.getUserID(context);
        homeInteractor.logout(userID, new OnLogoutCompleteListener() {
            @Override
            public void onLogoutSuccess() {
                mainView.hideProgress();
                FirebaseMessaging.getInstance().unsubscribeFromTopic(userID);
                UserAuth.saveAccessToken(context, null);
                Utils.saveHeaderProfile(context, null);
                EventBus.getDefault().post(new UserAuthorizationChangedEvent());
                ToastUtils.quickToast(context, R.string.logout_success);
            }

            @Override
            public void onServerError(String message) {
                mainView.hideProgress();
                ToastUtils.quickToast(context, R.string.logout_failure);
            }


        });
    }

    @Override
    public void fetchHeaderProfile() {
        homeInteractor.getUserHeaderProfile(new OnGetHeaderProfileCompleteListener() {
            @Override
            public void onGetHeaderProfileSuccess(HeaderProfile headerProfile) {
                Utils.saveHeaderProfile(context, headerProfile);
                mainView.showHeaderProfile(headerProfile);
            }

            @Override
            public void onServerError(String message) {
                ToastUtils.quickToast(context,message);
            }

        });
    }

//    @Override
//    public void registerUnseenMessageListener() {
//        homeInteractor.registerUnseenMessageListener(UserAuth.getUserID(context), new OnLastMessageSeenStateChangeListener() {
//            @Override
//            public void onLastMessageSeenStateChange(String roomID, boolean seen) {
//                if(seen) {
//                    unseenMessageRoomIDs.remove(roomID);
//                } else {
//                    unseenMessageRoomIDs.add(roomID);
//                }
//                mainView.onUnseenMessageNumberChange(unseenMessageRoomIDs.size());
//            }
//
//            @Override
//            public void onServerError(String message) {
//
//            }
//
//            @Override
//            public void onNetworkConnectionError() {
//
//            }
//        });
//    }

//    @Override
//    public void unregisterUnseenMessageListener() {
//        homeInteractor.unregisterUnseenMessageListener();
//    }

    @Override
    public void onViewDestroy() {
        this.context = null;
        this.homeInteractor.onViewDestroy();
    }
}
