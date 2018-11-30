package com.ptit.store.presenter.account.register;

import android.content.Context;
import android.widget.Toast;

import com.ptit.store.R;
import com.ptit.store.common.Utils;
import com.ptit.store.models.body.CustomerRegisterBody;
import com.ptit.store.view.account.register.RegisterView;

public class RegisterPresenterImpl implements RegisterPresenter {
    private Context context;
    private RegisterView registerView;
    private RegisterInterator registerInterator;

    public RegisterPresenterImpl(Context context, RegisterView registerView) {
        this.context = context;
        this.registerView = registerView;
        this.registerInterator = new RegisterInteratorImpl(context);
    }

    @Override
    public void onViewDestroy() {
        registerInterator.onViewDestroy();
    }

    @Override
    public void validateUsernameAndPassword(String username, String password, CustomerRegisterBody customerRegisterBody) {
        //Xử lý lỗi dữ liệu đầu vào
        if (username.isEmpty()) {
            registerView.showUserNameError();
            return;
        }
        if (password.isEmpty()) {
            registerView.showPasswordError();
            return;
        }
        if(!Utils.isEmailValid(username)){
            registerView.showInvalidUser();
            return;
        }


        registerView.showLoadingDialog();

        registerInterator.register(username, password, customerRegisterBody, new OnRegisterCompleteListener() {
            @Override
            public void onRegisterSuccess(String username) {
                registerView.hideLoadingDialog();
                Toast.makeText(context, context.getString(R.string.register_successfully), Toast.LENGTH_SHORT).show();
                registerView.gotoVerifyActivity(username);
            }

            @Override
            public void onError(String message) {
                registerView.hideLoadingDialog();
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAccountExist() {
                registerView.hideLoadingDialog();
                Toast.makeText(context, context.getString(R.string.account_existed), Toast.LENGTH_SHORT).show();
            }
        });
    }



}
