package com.ptit.store.presenter;

import com.google.firebase.firestore.FirebaseFirestore;

import com.ptit.store.common.Constants;
import com.ptit.store.models.model_chat.UserChat;


public class ApplicationInteractorImpl implements ApplicationInteractor {
    @Override
    public void onViewDestroy() {

    }

    @Override
    public void updateUserOnlineState(String userID, boolean isOnline, OnRequestCompleteListener listener) {
        FirebaseFirestore.getInstance().collection(Constants.USERS_COLLECTION)
                .document(userID)
                .update(UserChat.IS_ONLINE, isOnline)
                .addOnSuccessListener(aVoid -> {
                    if (listener != null) {
                        listener.onSuccess();
                    }
                })
                .addOnFailureListener(e -> {
                    if (listener != null) {
                        listener.onServerError(e.getMessage());
                    }
                });
    }
}
