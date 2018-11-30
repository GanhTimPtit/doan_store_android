package com.ptit.store.presenter.follow.unfollowing;

import android.content.Context;
import android.widget.Toast;

import com.ptit.store.models.response.PageList;
import com.ptit.store.models.response.SaveClothesPreview;
import com.ptit.store.view.follow.SaveClothesView;

public class UnSaveClothesPresenterImpl implements UnSaveClothesPresenter {
    Context context;
    SaveClothesView saveClothesView;
    UnSaveClothesInterator unSaveClothesInterator;

    public UnSaveClothesPresenterImpl(Context context, SaveClothesView saveClothesView) {
        this.context = context;
        this.saveClothesView = saveClothesView;
        this.unSaveClothesInterator = new UnSaveClothesInteratorImpl(context);
    }

    @Override
    public void UnSaveClothes(String clothesID) {
        saveClothesView.showLoadingDialog();
        unSaveClothesInterator.UnSaveClothes(clothesID, new OnUnSaveSuccessListener() {
            @Override
            public void onSuccess(PageList<SaveClothesPreview> saveClothesPreviewPageList) {
                saveClothesView.hideLoadingDialog();
                saveClothesView.refreshSaveClothes(saveClothesPreviewPageList.getResults());
                if(saveClothesPreviewPageList.getTotalItem()==0){
                    saveClothesView.showNoResult();
                }else {
                    saveClothesView.hideNoResult();
                }
            }

            @Override
            public void onError(String msg) {
                saveClothesView.hideLoadingDialog();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onViewDestroy() {
        unSaveClothesInterator.onViewDestroy();
    }
}
