package com.ptit.store.presenter.profile.edit_profile;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ptit.store.common.Constants;
import com.ptit.store.common.Utils;
import com.ptit.store.models.body.ProfileBody;
import com.ptit.store.models.response.HeaderProfile;
import com.ptit.store.models.response.Profile;
import com.ptit.store.services.event_bus.HeaderProfileEvent;
import com.ptit.store.services.event_bus.ProfileChangeEvent;
import com.ptit.store.view.profile.update_profile.UpdateProfileView;

import org.greenrobot.eventbus.EventBus;

public class EditProfilePresenterImpl implements EditProfilePresenter {
    private Context context;
    private UpdateProfileView editProfileView;
    private EditProfileInterator editProfileInterator;

    public EditProfilePresenterImpl(Context context, UpdateProfileView editProfileView) {
        this.context = context;
        this.editProfileView = editProfileView;
        this.editProfileInterator = new EditProfileInteractorImpl(context);
    }

    @Override
    public void validateProfile(Uri uri,
                                String firstName, String lastName,
                                String phone,
                                String address,
                                String identityCard,
                                String avatarUrl,
                                int gender,
                                long birthday,
                                String email) {
        if (firstName.isEmpty()) {
            editProfileView.showFullNameError();
            return;
        }
        if (lastName.isEmpty()) {
            editProfileView.showFullNameError();
            return;
        }
        if (phone.isEmpty()) {
            editProfileView.showPhoneError();
            return;
        }
        if (address.isEmpty()) {
            editProfileView.showAddressError();
            return;
        }
        if (identityCard.isEmpty()) {
            editProfileView.showIDCardError();
            return;
        }
        if (email.isEmpty()) {
            editProfileView.showEmailError();
            return;
        }
        String customerID = Utils.getSharePreferenceValues(context, Constants.PRE_USER_ID);

        editProfileView.showLoadingDiaolog();
        ProfileBody profileBody = new ProfileBody(firstName, lastName, phone, address, identityCard, gender, birthday, email);
        if (uri != null) {
            FirebaseApp.initializeApp(context);
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
          //  FirebaseOptions opts = FirebaseApp.getInstance().getOptions();
            StorageReference sr = firebaseStorage.getReference();
            StorageReference storageReference = sr.getStorage().getReference().child("Customer/" + customerID);
            storageReference.putFile(uri).addOnSuccessListener(taskSnapshot -> {
                profileBody.setAvatarUrl(taskSnapshot.getDownloadUrl().toString());
                Log.i("firebaswURI", "onSuccess: " + taskSnapshot.getDownloadUrl().toString());
                updateProfile(profileBody, customerID);
            }).addOnFailureListener(e -> {
                Log.i("firebaswURI", "onSuccess:" + e.getCause().toString());
                Toast.makeText(context, "Upload Image Fail!", Toast.LENGTH_SHORT).show();
                return;
            });
        } else {
            profileBody.setAvatarUrl(avatarUrl);
        }

        updateProfile(profileBody, customerID);


    }

    public void updateProfile(ProfileBody profileBody, String customerID) {
        editProfileInterator.updateProfile(profileBody, new OnEditSuccessListener() {
            @Override
            public void onSuccess(Profile profile) {
                editProfileView.hideLoadingDialog();
                editProfileView.backToProfileScreen();
                EventBus.getDefault().post(new ProfileChangeEvent(profile));
                HeaderProfile headerProfile = new HeaderProfile(customerID, profile.getFullName(),
                        profile.getAvatarUrl(), profile.getEmail(), profile.getPhone());
                EventBus.getDefault().post(new HeaderProfileEvent(headerProfile));
                Utils.saveHeaderProfile(context,headerProfile);
            }

            @Override
            public void onError(String message) {
                editProfileView.hideLoadingDialog();
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onViewDestroy() {
        editProfileInterator.onViewDestroy();
    }
}
