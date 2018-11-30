package com.ptit.store.presenter.search;

import android.content.Context;


import com.ptit.store.common.ToastUtils;
import com.ptit.store.models.response.ClothesSearchPreview;
import com.ptit.store.view.shop.fragment.search.SearchFragmentView;

import java.util.List;

public class SearchClothesPresenterImpl implements SearchClothesPresenter {
    private Context context;
    private SearchFragmentView searchFragmentView;
    private SearchClothesInteractor searchClothesInteractor;

    public SearchClothesPresenterImpl(Context context, SearchFragmentView searchFragmentView) {
        this.context = context;
        this.searchFragmentView = searchFragmentView;
        this.searchClothesInteractor = new SearchClothesInteractorImpl(context);
    }

    @Override
    public void fetchListClothesSearchPreview() {
        searchClothesInteractor.callClothesSearchPreview(new OnGetClothesSearchPreviewSuccessListener() {
            @Override
            public void onGetClothesSearchPreview(List<ClothesSearchPreview> categoryList) {
                searchFragmentView.showClothesSearchPreviews(categoryList);
            }

            @Override
            public void onMessageEror(String msg) {
                ToastUtils.quickToast(context, msg);
            }

            @Override
            public void onServerError(String message) {
                ToastUtils.quickToast(context,message);
            }

            @Override
            public void onNetworkConnectionError() {
                ToastUtils.quickToast(context, "Không có mạng (not network)");
            }
        });
    }




    @Override
    public void onViewDestroy() {
        searchClothesInteractor.onViewDestroy();
    }
}
