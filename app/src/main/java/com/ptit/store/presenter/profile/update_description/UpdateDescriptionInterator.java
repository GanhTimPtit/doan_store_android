package com.ptit.store.presenter.profile.update_description;

import com.ptit.store.presenter.BaseInteractor;

/**
 * Created by La Vo Tinh on 20/04/2018.
 */

public interface UpdateDescriptionInterator extends BaseInteractor{
    void updateDescription(String customerID,String description,OnUpdateDescriptionSuccessListener listener);
}
