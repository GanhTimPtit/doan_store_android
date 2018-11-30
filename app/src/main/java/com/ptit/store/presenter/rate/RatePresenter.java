package com.ptit.store.presenter.rate;

import com.ptit.store.models.body.RateBody;
import com.ptit.store.presenter.BasePresenter;

public interface RatePresenter extends BasePresenter {
    void validateCmt(String cmt, RateBody rateBody);
}
