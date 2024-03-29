package com.ptit.store.view.cart;

/**
 * Created by KingIT on 4/30/2018.
 */

public interface CartActivityView {
    void hideProgress();
    void showProgress();

    void deleteAllSelectedClothes();

    void switchToDeleteMode();
    void switchToViewMode();

    void setEnableRefresh(boolean isEnable);
    void setEnableToolBar(boolean isEnable);
    void backToHomeScreen();
}
