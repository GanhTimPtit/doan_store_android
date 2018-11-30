package com.ptit.store.view.account.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ptit.store.MainActivity;
import com.ptit.store.R;
import com.ptit.store.SQLiteHelper.DBManager;
import com.ptit.store.common.Config;
import com.ptit.store.common.Constants;
import com.ptit.store.common.ToastUtils;
import com.ptit.store.common.UserAuth;
import com.ptit.store.common.Utils;
import com.ptit.store.custom.LoadingDialog;
import com.ptit.store.models.body.FacebookLoginBody;
import com.ptit.store.presenter.account.login.LoginPresenter;
import com.ptit.store.presenter.account.login.LoginPresenterImpl;
import com.ptit.store.view.account.register.RegisterActivity;
import com.paypal.android.sdk.payments.PayPalConfiguration;

import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginView {

    EditText edt_username;
    EditText edt_password;
    Button btn_login;
    TextView txt_signup;
    DBManager db;
    ProgressBar progressBar;
    LoadingDialog loadingDialog;
    LoginPresenter presenter;
    Button loginButton;
    CallbackManager callbackManager;
    ImageView img;
    FacebookLoginBody facebookLoginBody;
    private PayPalConfiguration configuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            presenter = new LoginPresenterImpl(this, this);
            facebookLoginBody = new FacebookLoginBody();
            FacebookSdk.sdkInitialize(getApplicationContext());
            AppEventsLogger.activateApp(this);
            callbackManager = CallbackManager.Factory.create();
            configuration = new PayPalConfiguration()
                    .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                    .clientId(Config.CLIENT_ID);

            initWidget();
            initFacebookLogin();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // getDataFromIntent();

    }


    private void initFacebookLogin() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                presenter.validateFacebookLogin(loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                ToastUtils.quickToast(LoginActivity.this, R.string.error_message);
            }

            @Override
            public void onError(FacebookException exception) {
                if (exception instanceof FacebookAuthorizationException) {
                    if (AccessToken.getCurrentAccessToken() != null) {
                        LoginManager loginManager = LoginManager.getInstance();
                        loginManager.logOut();
                        loginManager.logInWithReadPermissions(LoginActivity.this,
                                Arrays.asList("public_profile", "email"));
                    }
                } else {
                    ToastUtils.quickToast(LoginActivity.this, R.string.error_message);
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoginManager.getInstance().logOut();
    }

    void initWidget() {
        edt_password = findViewById(R.id.edt_pass);
        edt_username = findViewById(R.id.edt_user_name);
        btn_login = findViewById(R.id.btn_login);
        txt_signup = findViewById(R.id.txt_signup);
        progressBar = findViewById(R.id.progress);
        loginButton = findViewById(R.id.login_button);
        img = findViewById(R.id.img_logo);

        txt_signup.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        loginButton.setOnClickListener(this);

        loadingDialog = new LoadingDialog(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login: {
                presenter.validateUsernameAndPassword(edt_username.getText().toString(), edt_password.getText().toString());
                break;
            }
            case R.id.txt_signup: {
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivityForResult(intent, Constants.REQUEST_CODE_SIGNUP);
                break;
            }
            case R.id.login_button: {
                LoginManager.getInstance().logInWithReadPermissions(this,
                        Arrays.asList("public_profile", "email"));

                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.REQUEST_CODE_SIGNUP: {
                if (resultCode == Constants.RESULT_CODE_SIGNUP) {
                    edt_username.setText(data.getStringExtra(Constants.USER));
                }
                break;
            }
        }
    }

    @Override
    public void showLoadingDialog() {
        loadingDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        loadingDialog.hide();
    }

    @Override
    public void showUserNameError() {
        edt_username.setError("Wrong Info !");
    }

    @Override
    public void showPasswordError() {
        edt_password.setError("Wrong Info !");
    }

    @Override
    public void backToHomeScreen(int resultCode) {
        setResult(resultCode);
        finish();
    }
}
