package com.ptit.store.view.splash;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ProgressBar;

import com.ptit.store.MainActivity;
import com.ptit.store.R;
import com.ptit.store.presenter.splash.GetAppVersionPresenter;
import com.ptit.store.presenter.splash.GetAppVersionPresenterImpl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends AppCompatActivity implements SplashView{

    ProgressBar progressBar;
    private boolean isRequestSuccess = false;
    private AsyncTask<Void, Integer, Void> splashTimer;
    private GetAppVersionPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", getApplicationContext().getPackageName());

            for (android.content.pm.Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }
        presenter = new GetAppVersionPresenterImpl(this,this);
        progressBar = findViewById(R.id.progress);
    }

    @Override
    public void startProgress() {
        splashTimer = new SplashTimer().execute();

    }

    @Override
    public boolean isInProgress() {
        return splashTimer != null && !splashTimer.isCancelled();
    }

    @Override
    public void completeLoading() {
        isRequestSuccess = true;
    }



    @Override
    public void showServerErrorDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.error_happened)
                .setMessage(R.string.error_message)
                .setCancelable(false)
                .setPositiveButton(R.string.retry, (dialogInterface, i) -> {
                    presenter.startApp();
                })
                .show();
    }

    @Override
    public void showServerMaintainDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.error_happened)
                .setMessage(R.string.error_message)
                .setCancelable(false)
                .setPositiveButton(R.string.retry, (dialogInterface, i) -> {
                    finish();
                })
                .show();
    }

    @Override
    public void showNoInternetConnectionDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.no_internet_connection)
                .setMessage(R.string.please_make_sure_the_intenet_connection_is_enable)
                .setCancelable(false)
                .setPositiveButton(R.string.retry, (dialogInterface, i) -> {
                    presenter.startApp();
                }).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.startApp();
    }


    private class SplashTimer extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            long millisPerProgress = 2500 / 100;
            int progress = 0;
            try {
                while (progress <= 80) {
                    progress++;
                    publishProgress(progress);
                    Thread.sleep(millisPerProgress);
                }
                while (!isRequestSuccess);
                while (progress <= 100) {
                    progress++;
                    publishProgress(progress);
                    Thread.sleep(millisPerProgress);
                }
            } catch (InterruptedException ignored) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // if (!Constants.LOGIN_TRUE.equals(Utils.getSharePreferenceValues(SplashActivity.this, Constants.STATUS_LOGIN))) {
//            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            //   } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            //  }
            finish();
        }
    }
}
