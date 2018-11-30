package com.ptit.store.view.profile.edit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.ptit.store.R;
import com.ptit.store.common.Constants;
import com.ptit.store.common.Utils;
import com.ptit.store.models.response.Profile;

import java.util.ArrayList;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity {
    EditText edt_firstName;
    EditText edt_lastName;
    EditText edt_birthDay;
    AppCompatSpinner spinner;
    EditText edt_address;
    EditText edt_email;
    EditText edt_phone;
    EditText edt_card;
    int gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        initWidget();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            Profile profile = (Profile) intent.getSerializableExtra(Constants.PROFILE);
            edt_firstName.setText(profile.getFullName() == null ? "-" : profile.getFullName().substring(0,profile.getFullName().toString().lastIndexOf(" ")));
            edt_lastName.setText(profile.getFullName() == null ? "-" : profile.getFullName().substring(profile.getFullName().toString().lastIndexOf(" ")));
            edt_birthDay.setText(Utils.getDateFromMilliseconds(profile.getBirthday()));
            edt_address.setText(profile.getAddress() == null ? "-" : profile.getAddress());
            edt_phone.setText(profile.getPhone() == null ? "-" : profile.getPhone());
            edt_card.setText(profile.getIdentityCard() == null ? "-" : profile.getIdentityCard());
            edt_email.setText(profile.getEmail() == null ? "-" : profile.getEmail());
        }

        List<String> lsGenger = new ArrayList<>();
        lsGenger.add("Nữ");
        lsGenger.add("Nam");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lsGenger);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    void initWidget() {
        edt_firstName = findViewById(R.id.edt_firstname);
        edt_lastName = findViewById(R.id.edt_lastname);
        edt_birthDay = findViewById(R.id.edt_birthday);
        spinner = findViewById(R.id.spinner_gender);
        edt_address = findViewById(R.id.edt_address);
        edt_email = findViewById(R.id.edt_email);
        edt_phone = findViewById(R.id.edt_phone);
        edt_card = findViewById(R.id.edt_id_card);

    }
}
