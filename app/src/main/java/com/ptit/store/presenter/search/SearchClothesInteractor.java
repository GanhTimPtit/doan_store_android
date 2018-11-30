package com.ptit.store.presenter.search;


import com.ptit.store.presenter.BaseInteractor;

public interface SearchClothesInteractor extends BaseInteractor {
    void callClothesSearchPreview(OnGetClothesSearchPreviewSuccessListener listener);
}
