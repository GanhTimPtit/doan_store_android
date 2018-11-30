package com.ptit.store.presenter.follow.following;

import com.ptit.store.models.response.PageList;
import com.ptit.store.models.response.SaveClothesPreview;

public interface OnGetSaveClothesSuccessListener {
    void onSuccess(PageList<SaveClothesPreview> saveClothesPreviews);
    void onError(String msg);
}
