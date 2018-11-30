package com.ptit.store.presenter.shop.clothes_detail;

import android.content.Context;

import com.ptit.store.R;
import com.ptit.store.common.ResponseCode;
import com.ptit.store.common.UserAuth;
import com.ptit.store.models.response.ClothesPreview;
import com.ptit.store.models.response.PageList;
import com.ptit.store.models.body.RateClothesBody;
import com.ptit.store.models.response.ClothesViewModel;
import com.ptit.store.models.response.ResponseBody;
import com.ptit.store.presenter.OnRequestCompleteListener;
import com.ptit.store.services.ApiClient;
import com.ptit.store.services.retrofit.clothes.ClothesService;
import com.ptit.store.services.retrofit.following.UnSaveClothesService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by KingIT on 4/23/2018.
 */

public class ClothesDetailInteractorImpl implements ClothesDetailInteractor {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public ClothesDetailInteractorImpl(Context context) {
        this.context = context;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onViewDestroy() {
        this.compositeDisposable.clear();
    }

    @Override
    public void getClothesDetail(String clothesID, OnGetClothesDetailCompleteListener listener) {
        Observable<Response<ResponseBody<ClothesViewModel>>> observable = null;
        if(UserAuth.isUserLoggedIn(context)){
            observable = ApiClient.getClient().create(ClothesService.class)
                    .getClothesViewModelWithAuth(UserAuth.getBearerToken(context), clothesID);
        }else {
            observable = ApiClient.getClient().create(ClothesService.class)
                    .getClothesViewModelWithoutAuth(clothesID);
        }
        Disposable disposable = observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        response -> {
                            switch (response.code()) {
                                case ResponseCode.OK: {
                                    listener.onGetClothesDetailComplete(response.body().getData());
                                    break;
                                }
                                case ResponseCode.NOT_FOUND: {
                                    listener.onMessageEror(response.message());
                                    break;
                                }
                                default: {
                                    listener.onMessageEror(response.message());
                                    break;
                                }

                            }
                        }, error -> {
                            listener.onMessageEror(context.getString(R.string.server_error));
                        });
        compositeDisposable.add(disposable);
    }

    @Override
    public void getSimilarClothes(String clothesID, int pageIndex, int pageSize,
                                  OnGetPageClothesPreviewCompleteListener listener) {
        Observable<Response<ResponseBody<PageList<ClothesPreview>>>> observable =
                ApiClient.getClient().create(ClothesService.class)
                        .getSimilarClothesPreview(clothesID, pageIndex, pageSize, null, null);

        Disposable disposable = observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(response -> {
                    switch (response.code()) {
                        case ResponseCode.OK: {
                            listener.onGetPageClothesPreviewsSuccess(response.body().getData());
                            break;
                        }
                        case ResponseCode.NOT_FOUND: {
                            listener.onMessageEror(response.message());
                            break;
                        }
                        default: {
                            listener.onMessageEror(response.message());
                            break;
                        }
                    }
                }, error -> {
                    listener.onMessageEror(context.getString(R.string.server_error));
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void getRecommendClothes(String clothesID, int pageIndex, int pageSize, OnGetPageClothesPreviewCompleteListener listener) {
        Observable<Response<ResponseBody<PageList<ClothesPreview>>>> observable =
                ApiClient.getClient().create(ClothesService.class)
                        .getRecommendClothesPreview(clothesID, pageIndex, pageSize, null, "asc");

        Disposable disposable = observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(response -> {
                    switch (response.code()) {
                        case ResponseCode.OK: {
                            listener.onGetPageClothesPreviewsSuccess(response.body().getData());
                            break;
                        }
                        case ResponseCode.NOT_FOUND: {
                            listener.onMessageEror(response.message());
                            break;
                        }
                        default: {
                            listener.onMessageEror(response.message());
                            break;
                        }
                    }
                }, error -> {
                    listener.onMessageEror(context.getString(R.string.server_error));
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void rateClothes(String clothesID, RateClothesBody rateClothesBody, OnRequestCompleteListener listener) {
        Disposable disposable = ApiClient.getClient().create(ClothesService.class)
                .rateClothes(UserAuth.getBearerToken(context), clothesID, rateClothesBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        response -> {
                            switch (response.code()) {
                                case ResponseCode.OK: {
                                    listener.onSuccess();
                                    break;
                                }
                                case ResponseCode.NOT_FOUND: {
                                    listener.onServerError(response.message());
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
                        }
                );

        compositeDisposable.add(disposable);
    }

    @Override
    public void getAllRateClothes(String clothesID, OnGetPageRateClothesSuccessListener listener) {
        Disposable disposable = ApiClient.getClient().create(ClothesService.class)
                .getAllRateClothes(clothesID, null, null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        response -> {
                            switch (response.code()) {
                                case ResponseCode.OK: {
                                    listener.onGetRateClothesSuccess(response.body().getData());
                                    break;
                                }
                                case ResponseCode.NOT_FOUND: {
                                    listener.onError(response.message());
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
        compositeDisposable.add(disposable);
    }

    @Override
    public void saveClothes(String clothesID, OnRequestCompleteListener listener) {
        Disposable disposable = ApiClient.getClient().create(ClothesService.class)
                .saveClothes(UserAuth.getBearerToken(context), clothesID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        response -> {
                            switch (response.code()) {
                                case ResponseCode.OK: {
                                    listener.onSuccess();
                                    break;
                                }
                                case ResponseCode.NOT_FOUND: {
                                    listener.onServerError("Customer not found");
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
    public void deleteSavedClothes(String clothesID, OnRequestCompleteListener listener) {
        Disposable disposable = ApiClient.getClient().create(UnSaveClothesService.class)
                .UnSaveClothes(UserAuth.getBearerToken(context), clothesID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        response -> {
                            switch (response.code()) {
                                case ResponseCode.OK: {
                                    listener.onSuccess();
                                    break;
                                }
                                case ResponseCode.NOT_FOUND: {
                                    listener.onServerError("Customer not found");
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
    public void getClothesState(String clothesID, OnGetClothesStateSuccessListener listener) {
        Observable<Response<ResponseBody<Boolean>>> observable = ApiClient.getClient().create(ClothesService.class)

                .getClothesState(UserAuth.getBearerToken(context),clothesID);

        Disposable disposable = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response->{
                            switch (response.code()){
                                case ResponseCode.OK:{
                                    listener.onGetStateSuccess(response.body().getData());
                                    break;
                                }
                                case ResponseCode.NOT_FOUND:{
                                    listener.onError("Not found id");
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
}
