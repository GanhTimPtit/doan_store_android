package com.ptit.store.presenter.profile;

import android.content.Context;

import com.ptit.store.common.ResponseCode;
import com.ptit.store.common.UserAuth;
import com.ptit.store.models.response.Profile;
import com.ptit.store.models.response.ResponseBody;
import com.ptit.store.services.ApiClient;
import com.ptit.store.services.retrofit.profile.GetProfileService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ProfileInteratorImpl implements ProfileInterator {
    Context context;
    CompositeDisposable compositeDisposable;

    public ProfileInteratorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getProfile(String customerID, OnGetProfileSuccessListener listener) {
        Observable<Response<ResponseBody<Profile>>> observable =
                ApiClient.getClient().create(GetProfileService.class).getProfile(UserAuth.getBearerToken(context));

        Disposable disposable = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            switch (response.code()){
                                case ResponseCode.OK:{
                                    listener.onSuccess(response.body().getData());
                                    break;
                                }
                                case ResponseCode.NOT_FOUND:{
                                    listener.onError(response.message());
                                    break;
                                }
                                default:{
                                    listener.onError(response.message());
                                    break;
                                }
                            }
                        },
                        error -> {
                            listener.onError(error.getMessage());
                        }
                );

        compositeDisposable.add(disposable);
    }

    @Override
    public void onViewDestroy() {
        context = null;
        compositeDisposable.clear();
    }
}
