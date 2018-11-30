package com.ptit.store.presenter.follow.unfollowing;

import com.ptit.store.presenter.BaseInteractor;

public interface UnSaveClothesInterator extends BaseInteractor {
    void UnSaveClothes(String clothesID,OnUnSaveSuccessListener listener);
}
