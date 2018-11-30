package com.ptit.store.view.maps;


import com.ptit.store.models.google_map.Address;
import com.ptit.store.models.google_map.Location;

import java.util.List;

public interface LocationPickerActivityView {
    void showLocatingProgress();
    void hideLocatingProgress();
    void saveCurrentLocation(Location location, Address address);
    void showAddressLabel(String address);
    void hideAddressLabel();
    void refreshListAddressResult(List<Address> results);
}
