package com.ptit.store.presenter.shop.clothes_detail;

import android.content.Context;
import android.widget.Toast;

import com.ptit.store.common.Constants;

import com.ptit.store.models.response.ClothesPreview;
import com.ptit.store.models.response.PageList;
import com.ptit.store.models.body.RateClothesBody;
import com.ptit.store.models.response.ClothesViewModel;

import com.ptit.store.models.response.RateClothesViewModel;
import com.ptit.store.presenter.OnRequestCompleteListener;
import com.ptit.store.services.event_bus.OnSaveClothesEvent;
import com.ptit.store.view.shop.clothes_detail.ClothesDetailActivityView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by KingIT on 4/23/2018.
 */

public class ClothesDetailPresenterImpl implements ClothesDetailPresenter {
    private Context context;
    private ClothesDetailActivityView clothesDetailActivityView;
    private ClothesDetailInteractor clothesDetailInteractor;
    private int currentPage = 0;
    private int currentPage1 = 0;

    public ClothesDetailPresenterImpl(Context context, ClothesDetailActivityView clothesDetailActivityView) {
        this.context = context;
        this.clothesDetailActivityView = clothesDetailActivityView;
        this.clothesDetailInteractor = new ClothesDetailInteractorImpl(context);
    }

    @Override
    public void onViewDestroy() {
        clothesDetailInteractor.onViewDestroy();
    }

    @Override
    public void fetchClothesDetail(String clothesID) {
        clothesDetailActivityView.showProgress();
        clothesDetailInteractor.getClothesDetail(clothesID, new OnGetClothesDetailCompleteListener() {
            @Override
            public void onGetClothesDetailComplete(ClothesViewModel clothesViewModel) {
                clothesDetailActivityView.showClothesDetail(clothesViewModel);
                clothesDetailActivityView.hideProgress();
            }

            @Override
            public void onMessageEror(String msg) {
                clothesDetailActivityView.hideProgress();
                clothesDetailActivityView.showErrorLoading(msg);
            }
        });
    }

    @Override
    public void saveClothes(String clothesID) {
        clothesDetailActivityView.showProgress();
        clothesDetailInteractor.saveClothes(clothesID, new OnRequestCompleteListener() {
            @Override
            public void onSuccess() {
                clothesDetailActivityView.hideProgress();
                clothesDetailActivityView.switchButtonSaveJobToSaved();
                EventBus.getDefault().post(new OnSaveClothesEvent());
            }

            @Override
            public void onServerError(String message) {
                clothesDetailActivityView.hideProgress();
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void deleteSavedClothes(String clothesID) {
        clothesDetailActivityView.showProgress();
        clothesDetailInteractor.deleteSavedClothes(clothesID, new OnRequestCompleteListener() {
            @Override
            public void onSuccess() {
                clothesDetailActivityView.hideProgress();
                clothesDetailActivityView.switchButtonSaveJobToUnSaved();
                EventBus.getDefault().post(new OnSaveClothesEvent());
            }

            @Override
            public void onServerError(String message) {
                clothesDetailActivityView.hideProgress();
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void firstFetchSimilarClothes(String clothesID) {
        clothesDetailActivityView.showProgressSimilarClothes();
        clothesDetailInteractor.getSimilarClothes(clothesID, 0,
                Constants.PAGE_SIZE, new OnGetPageClothesPreviewCompleteListener() {
                    @Override
                    public void onGetPageClothesPreviewsSuccess(PageList<ClothesPreview> clothesPreviewPageList) {
                        if (clothesPreviewPageList.getTotalItem() == 0) {
                            clothesDetailActivityView.hideListSimilarClothes();
                        }
                        clothesDetailActivityView.hideProgressSimilarClothes();
                        if (clothesPreviewPageList.getPageIndex() == clothesPreviewPageList.getTotalPage() - 1) {
                            clothesDetailActivityView.enableLoadingMore(false);
                        } else {
                            clothesDetailActivityView.enableLoadingMore(true);
                        }
                        clothesDetailActivityView.hideErrorSimilarClothes();
                        clothesDetailActivityView.refreshSimilarClothes(clothesPreviewPageList.getResults());
                    }

                    @Override
                    public void onMessageEror(String msg) {
                        clothesDetailActivityView.showListSimilarClothes();
                        clothesDetailActivityView.hideProgressSimilarClothes();
                        clothesDetailActivityView.showErrorSimilarClothes();
                    }
                });
    }

    @Override
    public void loadMoreSimilarClothes(String clothesID) {
        clothesDetailActivityView.showSimilarLoadingMoreProgress();
        clothesDetailInteractor.getSimilarClothes(clothesID, currentPage + 1,
                Constants.PAGE_SIZE, new OnGetPageClothesPreviewCompleteListener() {
                    @Override
                    public void onGetPageClothesPreviewsSuccess(PageList<ClothesPreview> clothesPreviewPageList) {
                        currentPage++;
                        if (clothesPreviewPageList.getPageIndex() == clothesPreviewPageList.getTotalPage() - 1) {
                            clothesDetailActivityView.enableLoadingMore(false);
                        }
                        clothesDetailActivityView.hideSimilarLoadingMoreProgress();
                        clothesDetailActivityView.loadmoreSimilarClothes(clothesPreviewPageList.getResults());
                    }

                    @Override
                    public void onMessageEror(String msg) {
                        clothesDetailActivityView.hideSimilarLoadingMoreProgress();
                    }
                });
    }

    @Override
    public void firstFetchRecommendClothes(String clothesID) {
        clothesDetailActivityView.showProgressRecommendClothes();
        clothesDetailInteractor.getRecommendClothes(clothesID, 0,
                Constants.PAGE_SIZE, new OnGetPageClothesPreviewCompleteListener() {
                    @Override
                    public void onGetPageClothesPreviewsSuccess(PageList<ClothesPreview> clothesPreviewPageList) {
                        if (clothesPreviewPageList.getTotalItem() == 0) {
                            clothesDetailActivityView.hideListRecommendClothes();
                        }
                        clothesDetailActivityView.hideProgressRecommendClothes();
                        if (clothesPreviewPageList.getPageIndex() == clothesPreviewPageList.getTotalPage() - 1) {
                            clothesDetailActivityView.enableLoadingMoreRecommend(false);
                        } else {
                            clothesDetailActivityView.enableLoadingMoreRecommend(true);
                        }
                        clothesDetailActivityView.hideErrorRecommendClothes();
                        clothesDetailActivityView.refreshRecommendClothes(clothesPreviewPageList.getResults());
                    }

                    @Override
                    public void onMessageEror(String msg) {
                        clothesDetailActivityView.showListRecommendClothes();
                        clothesDetailActivityView.hideProgressRecommendClothes();
                        clothesDetailActivityView.showErrorRecommendClothes();
                    }
                });
    }

    @Override
    public void loadMoreRecommendClothes(String clothesID) {
        clothesDetailActivityView.showRecommendLoadingMoreProgress();
        clothesDetailInteractor.getRecommendClothes(clothesID, currentPage1 + 1,
                Constants.PAGE_SIZE, new OnGetPageClothesPreviewCompleteListener() {
                    @Override
                    public void onGetPageClothesPreviewsSuccess(PageList<ClothesPreview> clothesPreviewPageList) {
                        currentPage1++;
                        if (clothesPreviewPageList.getPageIndex() == clothesPreviewPageList.getTotalPage() - 1) {
                            clothesDetailActivityView.enableLoadingMoreRecommend(false);
                        }
                        clothesDetailActivityView.hideRecommendLoadingMoreProgress();
                        clothesDetailActivityView.loadmoreRecommendClothes(clothesPreviewPageList.getResults());
                    }

                    @Override
                    public void onMessageEror(String msg) {
                        clothesDetailActivityView.hideRecommendLoadingMoreProgress();
                    }
                });
    }

    @Override
    public void rateClothes(String clothesID, RateClothesBody rateClothesBody) {
        clothesDetailActivityView.showProgress();
        clothesDetailInteractor.rateClothes(clothesID, rateClothesBody, new OnRequestCompleteListener() {
            @Override
            public void onSuccess() {
                clothesDetailActivityView.hideRatingDialog();
                getAllRateClothes(clothesID);
            }

            @Override
            public void onServerError(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                clothesDetailActivityView.hideProgress();
            }
        });
    }

    @Override
    public void getAllRateClothes(String clothesID) {
        clothesDetailInteractor.getAllRateClothes(clothesID, new OnGetPageRateClothesSuccessListener() {
            @Override
            public void onGetRateClothesSuccess(List<RateClothesViewModel> rateClothesViewModelList) {
                clothesDetailActivityView.getAllRateClothes(rateClothesViewModelList);
                clothesDetailActivityView.hideProgress();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public void getClothesState(String clothesID) {
        clothesDetailActivityView.showProgress();
        clothesDetailInteractor.getClothesState(clothesID, new OnGetClothesStateSuccessListener() {
            @Override
            public void onGetStateSuccess(boolean state) {
                clothesDetailActivityView.showProgress();
                clothesDetailActivityView.showClothesState(state);
            }

            @Override
            public void onError(String msg) {
                clothesDetailActivityView.hideProgress();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

            }
        });
    }
}
