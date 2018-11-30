package com.ptit.store.presenter.history;

import android.content.Context;

import com.ptit.store.common.ResponseCode;
import com.ptit.store.common.UserAuth;
import com.ptit.store.models.response.PageList;
import com.ptit.store.models.response.OrderPreview;
import com.ptit.store.models.response.ResponseBody;
import com.ptit.store.services.ApiClient;
import com.ptit.store.services.retrofit.history.HistoryOrderService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class HistoryOrderInteractorImpl implements HistoryOrderInteractor {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public HistoryOrderInteractorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getAllHistoryOrder(int pageIndex, int pageSize, OnGetPageHistoryOrderSuccessListener listener) {
        Observable<Response<ResponseBody<PageList<OrderPreview>>>> observable =
                ApiClient.getClient().create(HistoryOrderService.class).getAllOrder(UserAuth.getBearerToken(context),pageIndex,pageSize,null,null);

        Disposable disposable = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response->{
                            switch (response.code()){
                                case ResponseCode.OK:{
                                    listener.onSuccess(response.body().getData());
                                    break;
                                }
                                default:{
                                    listener.onError(response.message());
                                    break;
                                }
                            }
                        },
                        error->{
                            listener.onError(error.getMessage());
                        }
                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void onViewDestroy() {
        compositeDisposable.clear();
    }
}
