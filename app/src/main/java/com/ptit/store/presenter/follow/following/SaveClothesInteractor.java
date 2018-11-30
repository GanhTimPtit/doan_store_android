package com.ptit.store.presenter.follow.following;

import com.ptit.store.presenter.BaseInteractor;

public interface SaveClothesInteractor extends BaseInteractor{
    void getSaveClothes(int pageIndex,int pageSize,OnGetSaveClothesSuccessListener listener);
}
