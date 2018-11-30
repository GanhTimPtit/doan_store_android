package com.ptit.store.presenter.shop.category;

import android.content.Context;

import com.ptit.store.common.ToastUtils;
import com.ptit.store.models.Category;
import com.ptit.store.models.Advertisement;
import com.ptit.store.view.shop.fragment.category.CategoryFragmentView;

import java.util.List;

public class CategoryPresenterImpl implements CategoryPresenter {
    private Context context;
    private CategoryInterator categoryInterator;
    private CategoryFragmentView categoryFragmentView;

    public CategoryPresenterImpl(Context context, CategoryFragmentView categoryFragmentView) {
        this.context = context;
        this.categoryFragmentView= categoryFragmentView;
        this.categoryInterator = new CategoryInteratorImpl(context);
    }

    @Override
    public void fetchListCategory() {
        categoryFragmentView.showLoadingDialog();
        categoryInterator.getCategory(new OnGetCategorySuccessListener() {
            @Override
            public void onGetCategory(List<Category> categoryList) {
                categoryFragmentView.showCategoryList(categoryList);
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
                categoryFragmentView.showLoadingDialog();
            }
        });
        categoryFragmentView.hideLoadingDialog();
    }

    @Override
    public void fetchListAdvertisement() {
        categoryFragmentView.showLoadingDialog();
        categoryInterator.getAdvertisement(new OnGetAdvertisementSuccessListener() {
            @Override
            public void onGetAdvertisement(List<Advertisement> advertisementList) {
                categoryFragmentView.showAdvertisementList(advertisementList);
            }

            @Override
            public void onMessageEror(String msg) {
                ToastUtils.quickToast(context, msg);
            }

            @Override
            public void onServerError(String message) {
                ToastUtils.quickToast(context, message);
            }

            @Override
            public void onNetworkConnectionError() {
                categoryFragmentView.showNoInternetConnectionDialog();
            }
        });
        categoryFragmentView.hideLoadingDialog();
    }

    @Override
    public void onViewDestroy() {
        categoryInterator.onViewDestroy();
    }
}
