package com.ptit.store.presenter.maps;

import android.content.Context;

import com.ptit.store.models.google_map.Address;
import com.ptit.store.models.google_map.GoogleAddressResponse;
import com.ptit.store.models.google_map.Location;
import com.ptit.store.view.maps.LocationPickerActivityView;

import java.util.List;


public class LocationPickerPresenterImpl implements LocationPickerPresenter {
    private Context context;
    private LocationPickerActivityView locationPickerActivityView;
    private LocationPickerInteractor locationPickerInteractor;

    public LocationPickerPresenterImpl(Context context,
                                       LocationPickerActivityView locationPickerActivityView) {
        this.context = context;
        this.locationPickerActivityView = locationPickerActivityView;
        this.locationPickerInteractor = new LocationPickerInteractorImpl(context);
    }

    @Override
    public void onViewDestroy() {
        locationPickerInteractor.onViewDestroy();
    }

    @Override
    public void fetchMapAddress(double lat, double lon) {
        locationPickerActivityView.showLocatingProgress();
        locationPickerInteractor.getMapAddress(lat, lon, new OnGetMapAddressCompleteListener() {
            @Override
            public void onGetAddressSuccess(GoogleAddressResponse addressResponse) {
                List<Address> addresses = addressResponse.getResults();
                if(addresses.isEmpty()) {
                    locationPickerActivityView.hideAddressLabel();
                } else {
                    Address address = addresses.get(0);
                    Location location = address.getGeometry().getLocation();
                    locationPickerActivityView.hideLocatingProgress();
                    locationPickerActivityView.saveCurrentLocation(location, address);
                    locationPickerActivityView.showAddressLabel(address.getFormattedAddress());
                }
            }

            @Override
            public void onServerError(String message) {
                locationPickerActivityView.hideAddressLabel();
            }

            @Override
            public void onNetworkConnectionError() {
                locationPickerActivityView.hideAddressLabel();
            }
        });
    }

    @Override
    public void queryMapAddress(String queryKey) {
        locationPickerInteractor.listAllMapAddress(queryKey, new OnGetMapAddressCompleteListener() {
            @Override
            public void onGetAddressSuccess(GoogleAddressResponse addressResponse) {
                locationPickerActivityView.refreshListAddressResult(addressResponse.getResults());
            }

            @Override
            public void onServerError(String message) {
                locationPickerActivityView.refreshListAddressResult(null);
            }

            @Override
            public void onNetworkConnectionError() {
                locationPickerActivityView.refreshListAddressResult(null);
            }
        });
    }
}
