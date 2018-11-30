package com.ptit.store.presenter.search;

import android.content.Context;


import com.ptit.store.common.ResponseCode;
import com.ptit.store.models.response.ClothesSearchPreview;
import com.ptit.store.models.response.ResponseBody;
import com.ptit.store.services.ApiClient;
import com.ptit.store.services.ResponseObserver;
import com.ptit.store.services.retrofit.clothes.ClothesService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class SearchClothesInteractorImpl implements SearchClothesInteractor {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public SearchClothesInteractorImpl(Context context) {
        this.context = context;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void callClothesSearchPreview(OnGetClothesSearchPreviewSuccessListener listener) {
        Disposable disposable = ApiClient.getClient().create(ClothesService.class)
                .getClothesSearchPreview()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(new ResponseObserver<Response<ResponseBody<List<ClothesSearchPreview>>>>() {
                    @Override
                    public void onSuccess(Response<ResponseBody<List<ClothesSearchPreview>>> response) {
                        switch (response.code()) {
                            case ResponseCode.OK: {
                                listener.onGetClothesSearchPreview(response.body().getData());
                            }
                            break;

                            default: {
                                listener.onMessageEror(response.message());
                                break;
                            }
                        }
                    }

                    @Override
                    public void onServerError(String message) {
                        listener.onServerError(message);
                    }

                    @Override
                    public void onNetworkConnectionError() {
                        listener.onNetworkConnectionError();
                    }
                });
        compositeDisposable.add(disposable);
    }


    @Override
    public void onViewDestroy() {
        context=null;
        compositeDisposable.clear();
    }
}
