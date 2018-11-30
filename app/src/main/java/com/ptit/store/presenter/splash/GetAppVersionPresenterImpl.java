package com.ptit.store.presenter.splash;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.ptit.store.R;
import com.ptit.store.common.ToastUtils;
import com.ptit.store.common.UserAuth;
import com.ptit.store.models.response.AccessTokenPair;
import com.ptit.store.view.splash.SplashView;

public class GetAppVersionPresenterImpl implements GetAppVersionPresenter , OnRefreshAccessTokenSuccessListener{
    Context context;
    SplashView splashView;
    GetAppVersionInteractor getAppVersionInteractor;

    public GetAppVersionPresenterImpl(Context context, SplashView splashView) {
        this.context = context;
        this.splashView = splashView;
        this.getAppVersionInteractor = new GetAppVersionInteractorImpl(context);
    }

    @Override
    public void startApp() {
        if (!splashView.isInProgress()) {
            splashView.startProgress();
        }
        getAppVersionInteractor.getVersionApp(new OnGetAppVersionSuccessListener() {
            @Override
            public void onGetAppVerion(int version) {

                PackageInfo packageInfo;
                try {
                    packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                } catch (PackageManager.NameNotFoundException e) {
                    ToastUtils.quickToast(context, R.string.error_happened);
                    return;
                }
                if (packageInfo.versionCode <= version) {
                    new AlertDialog.Builder(context)
                            .setCancelable(false)
                            .setTitle(R.string.update_app)
                            .setMessage(R.string.update_app_message)
                            .setPositiveButton(R.string.update, (dialogInterface, i) -> {
                                String url = "https://play.google.com/store/apps/details?id=" + context.getPackageName();
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                context.startActivity(browserIntent);
                            })
                            .setNegativeButton(R.string.exit, (dialogInterface, i) -> {
                                ((Activity) context).finish();
                            })
                            .show();
                    return;
                }

                if (UserAuth.isUserLoggedIn(context)) {
                    getAppVersionInteractor.refreshAccessToken(GetAppVersionPresenterImpl.this);
                } else {
                    onRefreshTokenExpired();
                }

            }

            @Override
            public void onServerMaintain() {
                splashView.showServerMaintainDialog();
            }

            @Override
            public void onNetworkConnectionError() {
                splashView.showServerErrorDialog();
            }

            @Override
            public void onServerError() {
                splashView.showNoInternetConnectionDialog();
            }
        });
    }

    @Override
    public void onViewDestroy() {
        getAppVersionInteractor.onViewDestroy();
    }

    @Override
    public void oneRefreshTokenSuccess(AccessTokenPair accessTokenPair) {
        UserAuth.saveAccessTokenPair(context, accessTokenPair);
        splashView.completeLoading();
    }

    @Override
    public void onRefreshTokenExpired() {
        UserAuth.saveAccessTokenPair(context, null);
        splashView.completeLoading();
    }

    @Override
    public void onServerError(String message) {
        splashView.showServerErrorDialog();
    }

    @Override
    public void onNetworkConnectionError() {
        splashView.showNoInternetConnectionDialog();
    }
}
