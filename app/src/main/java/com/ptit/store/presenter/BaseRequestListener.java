package com.ptit.store.presenter;



public interface BaseRequestListener {
    void onServerError(String message);
    void onNetworkConnectionError();
}
