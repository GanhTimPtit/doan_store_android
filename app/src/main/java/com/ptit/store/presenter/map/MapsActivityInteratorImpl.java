package com.ptit.store.presenter.map;

import android.content.Context;

import com.ptit.store.common.ResponseCode;
import com.ptit.store.models.body.LatLngBody;
import com.ptit.store.models.response.ResponseBody;
import com.ptit.store.models.response.StoreBranchViewModel;
import com.ptit.store.services.ApiClient;
import com.ptit.store.services.retrofit.maps.GetStoreBranchService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class MapsActivityInteratorImpl implements MapsActivityInterator {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public MapsActivityInteratorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getAllStoreBranch(LatLngBody latLngBody, OnGetSuccessListener listener) {
        Observable<Response<ResponseBody<List<StoreBranchViewModel>>>> observable =
                ApiClient.getClient().create(GetStoreBranchService.class).getAllBranchStore(latLngBody);

        Disposable disposable = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            switch (response.code()) {
                                case ResponseCode.OK: {
                                    listener.onSuccess(response.body().getData());
                                    break;
                                }
                                default: {
                                    listener.onError(response.message());
                                    break;
                                }
                            }

                        },
                        error -> {
                            listener.onError(error.getMessage());
                        }

                );
    }

    @Override
    public void onViewDestroy() {
        compositeDisposable.clear();
    }
}
