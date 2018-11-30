package com.ptit.store.presenter.maps;


import com.ptit.store.presenter.BasePresenter;

public interface LocationPickerPresenter extends BasePresenter {
    void fetchMapAddress(double lat, double lon);
    void queryMapAddress(String queryKey);
}
