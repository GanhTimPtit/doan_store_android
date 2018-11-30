package com.ptit.store.view.account.password.reset_password;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import com.ptit.store.R;
import com.ptit.store.custom.LoadingDialog;
import com.ptit.store.presenter.account.password.forget_password.ForgetPasswordPresenter;
import com.ptit.store.presenter.account.password.forget_password.ForgetPasswordPresenterImpl;

public class ForgetPasswordActivity extends AppCompatActivity implements ForgetPasswordView{
    Toolbar toolbar;
    EditText edt_email;
    Button btn_send;
    LoadingDialog loadingDialog;
    ForgetPasswordPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initWidget();
    }

    public void initWidget(){
        toolbar = findViewById(R.id.toolbar);
        edt_email = findViewById(R.id.edt_email);
        btn_send = findViewById(R.id.btn_Send);
        loadingDialog = new LoadingDialog(this);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v->{
            finish();
        });
        presenter = new ForgetPasswordPresenterImpl(this,this);

        btn_send.setOnClickListener(v->{
            presenter.validateEmail(edt_email.getText().toString());
        });
    }

    @Override
    public void showEmailInputError() {
        edt_email.setError(getString(R.string.must_not_empty));
    }

    @Override
    public void showEmailInputInvalid() {
        edt_email.setError(getString(R.string.invalid_email));

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
    public void backToHome() {
        finish();
    }
}
