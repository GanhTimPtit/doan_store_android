package com.ptit.store.presenter.shop.clothes;

import android.content.Context;

import com.ptit.store.R;
import com.ptit.store.common.Constants;
import com.ptit.store.common.ToastUtils;
import com.ptit.store.models.response.ClothesPreview;
import com.ptit.store.models.response.PageList;
import com.ptit.store.view.shop.clothes.ClothesActivityView;

/**
 * Created by KingIT on 4/22/2018.
 */

public class GetPageClothesPresenterImpl implements GetPageClothesPresenter{
    private Context context;
    private ClothesActivityView clothesActivityView;
    private GetPageClothesInteractor getPageClothesInteractor;
    private int currentPage = 0;

    public GetPageClothesPresenterImpl(Context context, ClothesActivityView clothesActivityView) {
        this.context = context;
        this.clothesActivityView = clothesActivityView;
        this.getPageClothesInteractor = new GetPageClothesInteratorImpl(context);
    }

    @Override
    public void onViewDestroy() {
        context = null;
        getPageClothesInteractor.onViewDestroy();
    }

    @Override
    public void loadMoreClothesPreviews(String categoryID) {
        clothesActivityView.showLoadMoreProgress();
        clothesActivityView.enableRefreshing(false);
        getPageClothesInteractor.getClothesPreviews(categoryID,currentPage + 1, Constants.PAGE_SIZE,
                new OnGetPageClothesSuccessListener() {
                    @Override
                    public void onGetPageClothesPreviewsSuccess(PageList<ClothesPreview> clothesPreviewPageList) {
                        currentPage++;
                        clothesActivityView.hideLoadMoreProgress();
                        clothesActivityView.enableRefreshing(true);
                        if (clothesPreviewPageList.getPageIndex() == clothesPreviewPageList.getTotalPage() - 1) {
                            clothesActivityView.enableLoadMore(false);
                        }
                        clothesActivityView.addClothesPreviews(clothesPreviewPageList);
                    }

                    @Override
                    public void onMessageEror(String msg) {
                        clothesActivityView.hideLoadMoreProgress();
                        clothesActivityView.enableRefreshing(true);
                        ToastUtils.quickToast(context, R.string.error_message);
                    }
                });
    }

    @Override
    public void refreshClothesPreviews(String categoryID) {
        clothesActivityView.showRefreshingProgress();
        clothesActivityView.enableRefreshing(true);
        clothesActivityView.enableLoadMore(false);
        getPageClothesInteractor.getClothesPreviews(categoryID, 0, Constants.PAGE_SIZE,
                new OnGetPageClothesSuccessListener() {
                    @Override
                    public void onGetPageClothesPreviewsSuccess(PageList<ClothesPreview> clothesPreviewPageList) {
                        currentPage=0;
                        if (clothesPreviewPageList.getPageIndex() == clothesPreviewPageList.getTotalPage() - 1) {
                            clothesActivityView.enableLoadMore(false);
                        }
                        clothesActivityView.hideRefreshingProgress();
                        clothesActivityView.enableLoadMore(true);
                        clothesActivityView.refreshClothesPreview(clothesPreviewPageList);
                    }

                    @Override
                    public void onMessageEror(String msg) {
                        clothesActivityView.hideRefreshingProgress();
                        clothesActivityView.enableRefreshing(true);
                        clothesActivityView.hideLoadMoreProgress();
                        clothesActivityView.enableLoadMore(true);
                        ToastUtils.quickToast(context, R.string.error_message);
                    }
                });
    }
}
