package com.ptit.store.presenter.shop.category;

import com.ptit.store.models.Category;
import com.ptit.store.presenter.BaseRequestListener;


import java.util.List;

public interface OnGetCategorySuccessListener extends BaseRequestListener {
    void onGetCategory(List<Category> categoryList);
    void onMessageEror(String msg);
}
