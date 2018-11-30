package com.ptit.store.view.shop.fragment.category;

import com.ptit.store.models.Category;
import com.ptit.store.models.Advertisement;

import java.util.List;

public interface CategoryFragmentView {
    void showLoadingDialog();
    void hideLoadingDialog();
    void showCategoryList(List<Category> categoryList);
    void showAdvertisementList(List<Advertisement> advertisementList);
    void showServerErrorDialog();
    void showNoInternetConnectionDialog();
}
