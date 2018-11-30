package com.ptit.store.view.shop.fragment.search;


import com.ptit.store.models.response.ClothesSearchPreview;

import java.util.List;

/**
 * Created by KingIT on 4/22/2018.
 */

public interface SearchFragmentView {
    void showLoadingDialog();
    void hideLoadingDialog();
    void showClothesSearchPreviews(List<ClothesSearchPreview> clothesSearchPreviews);
}
