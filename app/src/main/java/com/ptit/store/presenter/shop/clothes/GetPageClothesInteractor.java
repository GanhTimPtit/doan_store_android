package com.ptit.store.presenter.shop.clothes;

import com.ptit.store.presenter.BaseInteractor;


/**
 * Created by KingIT on 4/22/2018.
 */

public interface GetPageClothesInteractor extends BaseInteractor{
    void getClothesPreviews(String categoryID, int pageIndex, int pageSize,
                            OnGetPageClothesSuccessListener listener);

}
