package com.ptit.store.presenter.follow.unfollowing;

import com.ptit.store.models.response.PageList;
import com.ptit.store.models.response.SaveClothesPreview;

public interface OnUnSaveSuccessListener {
    void onSuccess(PageList<SaveClothesPreview> saveClothesPreviewPageList);
    void onError(String msg);
}
