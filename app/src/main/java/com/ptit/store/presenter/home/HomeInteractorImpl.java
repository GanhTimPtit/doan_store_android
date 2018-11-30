package com.ptit.store.presenter.home;

import android.content.Context;
;
import com.google.firebase.firestore.ListenerRegistration;

import com.ptit.store.common.ResponseCode;
import com.ptit.store.common.UserAuth;

import com.ptit.store.services.ApiClient;
import com.ptit.store.services.retrofit.profile.GetHeaderProfileService;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class HomeInteractorImpl implements HomeInteractor {
    private Context context;
    private CompositeDisposable compositeDisposable;
    private ListenerRegistration unseenMessageRegistration;

    public HomeInteractorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onViewDestroy() {
        compositeDisposable.clear();
    }

    @Override
    public void getUserHeaderProfile(OnGetHeaderProfileCompleteListener listener) {
        Disposable disposable = ApiClient.getClient().create(GetHeaderProfileService.class)
                .getHeaderProfile(UserAuth.getBearerToken(context))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                            switch (response.code()) {
                                case ResponseCode.OK: {
                                    listener.onGetHeaderProfileSuccess(response.body().getData());
                                    break;
                                }
                                default: {
                                    listener.onServerError(response.message());
                                    break;
                                }
                            }
                        },
                        error -> {
                            listener.onServerError(error.getMessage());

                        });
        compositeDisposable.add(disposable);
    }


    @Override
    public void logout(String userID, OnLogoutCompleteListener listener) {
//        FirebaseFirestore.getInstance().collection(Constants.CANDIDATES_COLLECTION)
//                .document(userID)
//                .set(new UserOnlineState(false), SetOptions.merge())
//                .addOnSuccessListener(aVoid -> {
//                    listener.onLogoutSuccess();
//                })
//                .addOnFailureListener(e -> {
//                    listener.onServerError(e.getMessage());
//                });
    }
}
