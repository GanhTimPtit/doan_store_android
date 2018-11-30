package com.ptit.store.view.order;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ptit.store.MainActivity;
import com.ptit.store.R;
import com.ptit.store.adapter.CartAdapter;
import com.ptit.store.common.Constants;
import com.ptit.store.common.ToastUtils;
import com.ptit.store.common.Utils;
import com.ptit.store.custom.LoadingDialog;
import com.ptit.store.models.Item;
import com.ptit.store.models.body.ItemBody;
import com.ptit.store.models.body.OrderBody;
import com.ptit.store.models.google_map.Location;
import com.ptit.store.presenter.checkout.CheckoutPresenter;
import com.ptit.store.presenter.checkout.CheckoutPresenterImpl;
import com.ptit.store.services.ManageCart;
import com.ptit.store.view.maps.LocationPickerActivity;

import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderActivity extends AppCompatActivity implements OrderActivityView {

    private static final int GET_LOCATION_REQUEST_CODE = 1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bt_pay)
    Button btPay;
    @BindView(R.id.edit_customer_name)
    EditText etName;
    @BindView(R.id.edit_customer_phone)
    EditText etPhone;
    @BindView(R.id.edit_customer_location)
    EditText etLocation;
    @BindView(R.id.rg_choise)
    RadioGroup rgChoise;
    @BindView(R.id.rd_choise_direct)
    RadioButton rgDirect;
    @BindView(R.id.rd_choise_online)
    RadioButton rgOnline;
    @BindView(R.id.rc_item)
    RecyclerView rcItem;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_price_ship)
    TextView tvPriceShip;
    @BindView(R.id.tv_total_order)
    TextView tvTotalOrder;
    private OrderBody orderBody;

    private LoadingDialog loadingDialog;
    private CartAdapter cartAdapter;
    CheckoutPresenter presenter;
    String choise="";
    Set<ItemBody> setItemBodies;
    private Location currentLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initVariables(savedInstanceState);
        initData();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }
    protected void initVariables(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        etLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etLocation.setError(null);
                Intent intent = new Intent(OrderActivity.this, LocationPickerActivity.class);
                Location location;
                if (currentLocation == null) {
                    location = new Location(Constants.DEFAULT_LAT, Constants.DEFAULT_LON);
                    intent.putExtra(Constants.ADDRESS, Constants.DEFAULT_ADDRESS);
                } else {
                    location = currentLocation;
                    intent.putExtra(Constants.ADDRESS, etLocation.getText().toString());
                }
                intent.putExtra(Constants.LOCATION, location);

                startActivityForResult(intent, GET_LOCATION_REQUEST_CODE);
            }
        });
        rgChoise.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rd_choise_direct:{
                        choise="Thanh toán trực tiếp";
                        break;
                    }
                    case R.id.rd_choise_online:{
                        choise="Thanh toán Online";
                        break;
                    }
                }
            }
        });
        btPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etName.getText().toString().trim().equals("")){
                    etName.setError("Không để trống trường này");
                    return;
                }
                if(etPhone.getText().toString().trim().equals("")){
                    etPhone.setError("Không để trống trường này");
                    return;
                }
                if(etLocation.getText().toString().trim().equals("")){
                    etLocation.setError("Không để trống trường này");
                    return;
                }
                if(setItemBodies.size()==0) {
                    if (ManageCart.getCart().getItems().size() != 0) {
                        for (Item item : ManageCart.getCart().getItems()) {
                            setItemBodies.add(new ItemBody(item.getColor(),
                                    item.getSize(),
                                    item.getCount(),
                                    item.getClothes().getPrice(),
                                    item.getClothes().getId()));
                        }
                        orderBody.setItemBodySet(setItemBodies);
                        orderBody.setTotalCost(ManageCart.getCart().getTotalMoney() + 21000);
                    }
                }

                if(choise.equals("")){
                    ToastUtils.quickToast(getApplicationContext(), "Bạn chưa chọn hình thức thanh toán");
                    return;
                }else{
                    orderBody.setPayments(choise);
                }
                orderBody.setNameCustomer(etName.getText().toString().trim());
                orderBody.setPhone(etPhone.getText().toString().trim());
                orderBody.setLocation(etLocation.getText().toString().trim());
                orderBody.setLat(currentLocation.getLat());
                orderBody.setLog(currentLocation.getLng());
                presenter.fetchOrder(orderBody);
            }
        });
    }
    public void initData(){
        presenter = new CheckoutPresenterImpl(this, this);
        loadingDialog = new LoadingDialog(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            actionBar.setTitle(R.string.check_order);
        }
        Item item= (Item) getIntent().getSerializableExtra(Constants.KEY_ORDER_CLOTHES);

        etName.setText(Utils.getHeaderProfile(this).getFullName());
        if(Utils.getHeaderProfile(this).getPhone()!=null){
        etPhone.setText(Utils.getHeaderProfile(this).getPhone());
        }else {
            etPhone.setText("");
        }
        tvPriceShip.setText("Phí vận chuyển: 21.000 đ");
        cartAdapter = new CartAdapter(this, null);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcItem.setLayoutManager(linearLayoutManager);
        rcItem.addItemDecoration(new DividerItemDecoration(this, linearLayoutManager.getOrientation()));
        rcItem.setHasFixedSize(true);
        rcItem.setAdapter(cartAdapter);

        orderBody= new OrderBody();
        setItemBodies= new HashSet<>();
       showCart(item);

    }
    private void showCart(Item item){
        if(item!=null){
            cartAdapter.addModel(item,false);
            ItemBody itemBody= new ItemBody(item.getColor(),
                    item.getSize(),
                    item.getCount(),
                    item.getClothes().getPrice(),
                    item.getClothes().getId());
            setItemBodies.add(itemBody);
            orderBody.setItemBodySet(setItemBodies);
            orderBody.setTotalCost(item.getCount()*item.getClothes().getPrice() + 21000);
            tvTotalPrice.setText("Tổng đơn hàng: " + Utils.formatNumberMoney(item.getCount()*item.getClothes().getPrice()) + " đ");
            tvTotalOrder.setText("Tổng thanh toán: " + Utils.formatNumberMoney(orderBody.getTotalCost()) +" đ");
        }else {
            cartAdapter.addModels(ManageCart.getCart().getItems(), false);

            if (ManageCart.getCart().getItems().size() != 0) {
                for (Item itemCart : ManageCart.getCart().getItems()) {
                    setItemBodies.add(new ItemBody(itemCart.getColor(),
                            itemCart.getSize(),
                            itemCart.getCount(),
                            itemCart.getClothes().getPrice(),
                            itemCart.getClothes().getId()));
                }
                orderBody.setItemBodySet(setItemBodies);
                orderBody.setTotalCost(ManageCart.getCart().getTotalMoney() + 21000);
                tvTotalPrice.setText("Tổng đơn hàng: " + Utils.formatNumberMoney(ManageCart.getCart().getTotalMoney()) + " đ");
                tvTotalOrder.setText("Tổng thanh toán: " + Utils.formatNumberMoney(orderBody.getTotalCost())+" đ");
            }
        }
    }

    @Override
    public void hideProgress() {
        loadingDialog.hide();
    }

    @Override
    public void showProgress() {
        loadingDialog.show();
    }


    @Override
    public void backToHomeScreen() {
        new android.app.AlertDialog.Builder(this)
                .setTitle(R.string.notification_order)
                .setMessage("Đơn đặt hàng của bạn đã thành công! Bạn vui lòng chờ xác nhận và giao hàng từ cửa hàng.")
                .setCancelable(false)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    startActivity(new Intent(OrderActivity.this, MainActivity.class));
                    finish();
                })
                .show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case GET_LOCATION_REQUEST_CODE: {
                if (resultCode == Activity.RESULT_OK) {
                    Location location = (Location) data.getSerializableExtra(Constants.LOCATION);
                    String address = data.getStringExtra(Constants.ADDRESS);
                    etLocation.setError(null);
                    etLocation.setSelected(true);
                    etLocation.setText(address);
                    currentLocation = location;
                }
            }
            break;

            default: {
                break;
            }
        }
    }
}
