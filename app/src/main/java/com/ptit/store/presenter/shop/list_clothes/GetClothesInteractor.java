package com.ptit.store.presenter.shop.list_clothes;

import com.ptit.store.presenter.BaseInteractor;

/**
 * Created by KingIT on 4/22/2018.
 */

public interface GetClothesInteractor extends BaseInteractor{
    void getClothesPreviews(int pageIndex, int pageSize,
                        OnGetClothesSuccessListener listener);
}
