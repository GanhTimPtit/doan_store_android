package com.ptit.store.presenter.shop.list_clothes;

import android.content.Context;

import com.ptit.store.R;
import com.ptit.store.common.Constants;
import com.ptit.store.common.ToastUtils;
import com.ptit.store.models.response.ClothesPreview;
import com.ptit.store.models.response.PageList;
import com.ptit.store.view.shop.ShopFragmentView;

/**
 * Created by KingIT on 4/22/2018.
 */

public class GetClothesPresenterImpl implements GetClothesPresenter{
    private Context context;
    private ShopFragmentView shopFragmentView;
    private GetClothesInteractor getClothesInteractor;
    private int currentPage = 0;

    public GetClothesPresenterImpl(Context context, ShopFragmentView shopFragmentView) {
        this.context = context;
        this.shopFragmentView = shopFragmentView;
        this.getClothesInteractor= new GetClothesInteratorImpl(context);
    }

    @Override
    public void onViewDestroy() {
        context = null;
        getClothesInteractor.onViewDestroy();
    }

    @Override
    public void loadMoreClothesPreviews() {
        shopFragmentView.showLoadMoreProgress();
        shopFragmentView.enableRefreshing(false);
        getClothesInteractor.getClothesPreviews(currentPage + 1, Constants.PAGE_SIZE,
                new OnGetClothesSuccessListener() {
                    @Override
                    public void onGetPageClothesPreviewsSuccess(PageList<ClothesPreview> clothesPreviewPageList) {
                        currentPage++;
                        shopFragmentView.hideLoadMoreProgress();
                        shopFragmentView.enableRefreshing(true);
                        if (clothesPreviewPageList.getPageIndex() == clothesPreviewPageList.getTotalPage() - 1) {
                            shopFragmentView.enableLoadMore(false);
                        }
                        shopFragmentView.addClothesPreviews(clothesPreviewPageList);
                    }

                    @Override
                    public void onMessageEror(String msg) {
                        shopFragmentView.hideLoadMoreProgress();
                        shopFragmentView.enableRefreshing(true);
                        ToastUtils.quickToast(context, R.string.error_message);
                    }
                });
    }

    @Override
    public void refreshClothesPreviews() {
        shopFragmentView.showRefreshingProgress();
        shopFragmentView.enableRefreshing(true);
        shopFragmentView.enableLoadMore(false);
        getClothesInteractor.getClothesPreviews(0, Constants.PAGE_SIZE,
                new OnGetClothesSuccessListener() {
                    @Override
                    public void onGetPageClothesPreviewsSuccess(PageList<ClothesPreview> clothesPreviewPageList) {
                        currentPage=0;
                        if (clothesPreviewPageList.getPageIndex() == clothesPreviewPageList.getTotalPage() - 1) {
                            shopFragmentView.enableLoadMore(false);
                        }
                        shopFragmentView.hideRefreshingProgress();
                        shopFragmentView.enableLoadMore(true);
                        shopFragmentView.refreshClothesPreview(clothesPreviewPageList);
                    }

                    @Override
                    public void onMessageEror(String msg) {
                        shopFragmentView.hideRefreshingProgress();
                        shopFragmentView.enableRefreshing(true);
                        shopFragmentView.hideLoadMoreProgress();
                        shopFragmentView.enableLoadMore(true);
                        ToastUtils.quickToast(context, R.string.error_message);
                    }
                });
    }
}
