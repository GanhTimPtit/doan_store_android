package com.ptit.store.custom;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.ptit.store.R;


public class LoadingDialog extends Dialog {
    public LoadingDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        setContentView(R.layout.dialog_loading);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }
}
