package com.ptit.store.presenter.account.login;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.internal.LinkedTreeMap;
import com.ptit.store.R;
import com.ptit.store.common.Constants;
import com.ptit.store.common.ToastUtils;
import com.ptit.store.common.UserAuth;
import com.ptit.store.common.Utils;
import com.ptit.store.models.response.AccessToken;
import com.ptit.store.models.response.HeaderProfile;
import com.ptit.store.services.event_bus.HeaderProfileEvent;
import com.ptit.store.services.event_bus.UserAuthorizationChangedEvent;
import com.ptit.store.view.account.login.LoginView;

import org.greenrobot.eventbus.EventBus;

public class LoginPresenterImpl implements LoginPresenter {
    private Context context;
    private LoginView loginView;
    private LoginInterator loginInterator;

    public LoginPresenterImpl(Context context, LoginView loginView) {
        this.context = context;
        this.loginView = loginView;
        this.loginInterator = new LoginInteratorImpl(context);
    }

    @Override
    public void validateUsernameAndPassword(String username, String password) {
        if (username.isEmpty()) {
            loginView.showUserNameError();
            return;
        }
        if (password.isEmpty()) {
            loginView.showPasswordError();
            return;
        }

        loginView.showLoadingDialog();
        loginInterator.login(username, password, new OnLoginSuccessListener() {
            @Override
            public void onLoginSuccess(AccessToken accessToken) {
                UserAuth.saveAccessToken(context, accessToken);
                FirebaseMessaging.getInstance().subscribeToTopic(accessToken.getId());
                EventBus.getDefault().post(new UserAuthorizationChangedEvent());
                loginView.backToHomeScreen(Activity.RESULT_OK);
                Toast.makeText(context, context.getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                loginView.hideLoadingDialog();
            }

            @Override
            public void onAccounNotVerify() {
                Toast.makeText(context, context.getString(R.string.account_not_verify), Toast.LENGTH_SHORT).show();
                loginView.hideLoadingDialog();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                loginView.hideLoadingDialog();

            }
        });
    }

    @Override
    public void validateFacebookLogin(String facebookAccessToken) {
        loginView.showLoadingDialog();
        loginInterator.facebookLogin(facebookAccessToken, new OnGetFacebookLoginStateListener() {
            @Override
            public void onFacebookLoginSuccess(AccessToken accessToken) {
                UserAuth.saveAccessToken(context, accessToken);
                FirebaseMessaging.getInstance().subscribeToTopic(accessToken.getId());
                EventBus.getDefault().post(new UserAuthorizationChangedEvent());
                loginView.backToHomeScreen(Activity.RESULT_OK);
                loginView.hideLoadingDialog();
            }

            @Override
            public void onServerError(String message) {
                ToastUtils.quickToast(context, R.string.error_message);
                loginView.hideLoadingDialog();
            }

            @Override
            public void onNetworkConnectionError() {
                ToastUtils.quickToast(context, R.string.no_internet_connection);
                loginView.hideLoadingDialog();
            }
        });
    }

    @Override
    public void onViewDestroy() {
        context = null;
        loginInterator.onViewDestroy();
    }
}
