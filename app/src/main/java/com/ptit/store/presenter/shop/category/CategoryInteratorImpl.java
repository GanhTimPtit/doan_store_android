package com.ptit.store.presenter.shop.category;

import android.content.Context;

import com.ptit.store.common.ResponseCode;
import com.ptit.store.models.Category;
import com.ptit.store.models.Advertisement;
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

public class CategoryInteratorImpl implements CategoryInterator{
    private Context context;
    private CompositeDisposable compositeDisposable;

    public CategoryInteratorImpl(Context context) {
        this.context = context;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getCategory(OnGetCategorySuccessListener listener) {
        Disposable disposable = ApiClient.getClient().create(ClothesService.class)
                .getCategory()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(new ResponseObserver<Response<ResponseBody<List<Category>>>>() {
                    @Override
                    public void onSuccess(Response<ResponseBody<List<Category>>> response) {
                        switch (response.code()) {
                            case ResponseCode.OK: {
                                listener.onGetCategory(response.body().getData());
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
    public void getAdvertisement(OnGetAdvertisementSuccessListener listener) {
        Disposable disposable = ApiClient.getClient().create(ClothesService.class)
                .getAdvertisement()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(new ResponseObserver<Response<ResponseBody<List<Advertisement>>>>() {
                    @Override
                    public void onSuccess(Response<ResponseBody<List<Advertisement>>> response) {
                        switch (response.code()) {
                            case ResponseCode.OK: {
                                listener.onGetAdvertisement(response.body().getData());
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
