package com.ptit.store.presenter.search;



import com.ptit.store.models.response.ClothesSearchPreview;
import com.ptit.store.presenter.BaseRequestListener;

import java.util.List;

public interface OnGetClothesSearchPreviewSuccessListener extends BaseRequestListener {
    void onGetClothesSearchPreview(List<ClothesSearchPreview> searchPreviewList);
    void onMessageEror(String msg);
}
