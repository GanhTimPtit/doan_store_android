package com.ptit.store.presenter.maps;

import android.content.Context;


import com.ptit.store.R;
import com.ptit.store.models.google_map.GoogleAddressResponse;
import com.ptit.store.services.GoogleMapClient;
import com.ptit.store.services.ResponseObserver;
import com.ptit.store.services.google_map.GetMapAddressService;
import com.ptit.store.services.google_map.GetMapLatLonService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class LocationPickerInteractorImpl implements LocationPickerInteractor {
    private Context context;
    private CompositeDisposable compositeDisposable;
    private Disposable activeLatLngQueryDisposable;
    private Disposable activeLocatingDisposable;

    public LocationPickerInteractorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onViewDestroy() {
        compositeDisposable.clear();
    }

    @Override
    public void cancelGetMapAddressRequest() {
        if (activeLocatingDisposable != null && !activeLocatingDisposable.isDisposed()) {
            activeLocatingDisposable.dispose();
        }
    }

    @Override
    public void getMapAddress(double lat, double lon, OnGetMapAddressCompleteListener listener) {
        if (activeLocatingDisposable != null && !activeLocatingDisposable.isDisposed()) {
            activeLocatingDisposable.dispose();
        }
        String latLng = lat + "," + lon;
        activeLocatingDisposable = GoogleMapClient.getClient()
                .create(GetMapAddressService.class)
                .getAddress(latLng, context.getString(R.string.google_geocoding_api_key))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(new ResponseObserver<GoogleAddressResponse>() {
                    @Override
                    public void onSuccess(GoogleAddressResponse response) {
                        listener.onGetAddressSuccess(response);
                    }

                    @Override
                    public void onServerError(String message) {
                        listener.onServerError(message);
                    }

                    @Override
                    public void onNetworkConnectionError() {
                        listener.onNetworkConnectionError();
                    }
                });
        compositeDisposable.add(activeLocatingDisposable);
    }

    @Override
    public void listAllMapAddress(String queryAddress, OnGetMapAddressCompleteListener listener) {
        if (activeLatLngQueryDisposable != null && !activeLatLngQueryDisposable.isDisposed()) {
            activeLatLngQueryDisposable.dispose();
        }
        activeLatLngQueryDisposable = GoogleMapClient.getClient()
                .create(GetMapLatLonService.class)
                .queryAddress(queryAddress, context.getString(R.string.google_geocoding_api_key))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(new ResponseObserver<GoogleAddressResponse>() {
                    @Override
                    public void onSuccess(GoogleAddressResponse response) {
                        listener.onGetAddressSuccess(response);
                    }

                    @Override
                    public void onServerError(String message) {
                        listener.onServerError(message);
                    }

                    @Override
                    public void onNetworkConnectionError() {
                        listener.onNetworkConnectionError();
                    }
                });
        compositeDisposable.add(activeLatLngQueryDisposable);
    }
}
