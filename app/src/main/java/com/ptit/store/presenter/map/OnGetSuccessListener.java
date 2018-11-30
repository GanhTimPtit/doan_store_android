package com.ptit.store.presenter.map;

import com.ptit.store.models.response.StoreBranchViewModel;

import java.util.List;

public interface OnGetSuccessListener {
    void onSuccess(List<StoreBranchViewModel> storeBranchViewModels);
    void onError(String error);
}
