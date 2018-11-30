package com.ptit.store.presenter.feedback;

import android.content.Context;

import com.ptit.store.common.ResponseCode;
import com.ptit.store.common.UserAuth;
import com.ptit.store.models.response.ResponseBody;
import com.ptit.store.services.ApiClient;
import com.ptit.store.services.retrofit.feedback.FeedbackService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class FeedbackInteractorImpl implements FeedbackInteractor {
    Context context;
    CompositeDisposable compositeDisposable;

    public FeedbackInteractorImpl(Context context) {
        this.context = context;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void sendFeedback(String feedback, OnSendFeedbackSuccessListener listener) {
        Observable<Response<ResponseBody<String>>> observable = ApiClient.getClient().create(FeedbackService.class)
                .sendFeedback(UserAuth.getBearerToken(context), feedback);

        Disposable disposable = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            switch (response.code()) {
                                case ResponseCode.OK: {
                                    listener.onSuccess();
                                }
                                default: {
                                    listener.onError(response.message());
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
