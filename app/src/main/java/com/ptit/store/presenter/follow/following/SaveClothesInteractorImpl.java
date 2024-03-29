package com.ptit.store.presenter.follow.following;

import android.content.Context;

import com.ptit.store.common.ResponseCode;
import com.ptit.store.common.UserAuth;
import com.ptit.store.models.response.PageList;
import com.ptit.store.models.response.ResponseBody;
import com.ptit.store.models.response.SaveClothesPreview;
import com.ptit.store.services.ApiClient;
import com.ptit.store.services.retrofit.following.GetSaveClothesService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class SaveClothesInteractorImpl implements SaveClothesInteractor {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public SaveClothesInteractorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getSaveClothes(int pageIndex, int pageSize, OnGetSaveClothesSuccessListener listener) {
        Observable<Response<ResponseBody<PageList<SaveClothesPreview>>>> observable =
                ApiClient.getClient().create(GetSaveClothesService.class).getSaveClothes(UserAuth.getBearerToken(context),pageIndex,pageSize,null,null);
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
