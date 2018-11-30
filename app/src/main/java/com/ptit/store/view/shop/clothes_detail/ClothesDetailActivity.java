package com.ptit.store.view.shop.clothes_detail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ptit.store.R;
import com.ptit.store.adapter.SpinnerColorAdapter;
import com.ptit.store.adapter.SpinnerSizeAdapter;
import com.ptit.store.common.Config;
import com.ptit.store.adapter.EndlessLoadingRecyclerViewAdapter;
import com.ptit.store.adapter.RateClothesAdapter;
import com.ptit.store.adapter.RecyclerViewAdapter;
import com.ptit.store.adapter.SimilarClothesAdapter;
import com.ptit.store.common.Constants;
import com.ptit.store.common.UserAuth;
import com.ptit.store.common.Utils;
import com.ptit.store.custom.LoadingDialog;
import com.ptit.store.custom.RatingDialog;
import com.ptit.store.models.response.ClothesPreview;
import com.ptit.store.models.Item;
import com.ptit.store.models.body.RateClothesBody;
import com.ptit.store.models.body.ItemBody;
import com.ptit.store.models.response.ClothesViewModel;
import com.ptit.store.models.response.RateClothesViewModel;
import com.ptit.store.presenter.shop.clothes_detail.ClothesDetailPresenter;
import com.ptit.store.presenter.shop.clothes_detail.ClothesDetailPresenterImpl;
import com.ptit.store.services.ManageCart;
import com.ptit.store.view.account.register.RegisterActivity;
import com.ptit.store.view.cart.CartActivity;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.ptit.store.view.order.OrderActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class ClothesDetailActivity extends AppCompatActivity implements
        ClothesDetailActivityView,
        View.OnClickListener,
        EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener,
        RecyclerViewAdapter.OnItemClickListener,
        RatingDialog.onClickRateButton {

    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.img_clothes)
    ImageView imgClothes;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab_save)
    TextView fabSave;
    @BindView(R.id.tv_name_product)
    TextView tvNameClothes;
    @BindView(R.id.tv_cost_product)
    TextView tvCostClothes;
    @BindView(R.id.rating_product)
    MaterialRatingBar ratingClothes;
    @BindView(R.id.tv_avarage_rate)
    TextView tv_avarage_rate;
    @BindView(R.id.tv_acount_rate)
    TextView tvAcountRate;
    @BindView(R.id.tv_detail_product)
    TextView tvDescriptionCLothes;
    @BindView(R.id.rc_customer_rate)
    RecyclerView rcCustomerRate;

    @BindView(R.id.bt_add_cart)
    Button btAddCart;
    @BindView(R.id.bt_pay)
    Button btPay;

    @BindView(R.id.rc_product_similar)
    RecyclerView rcClothesSimilar;
    @BindView(R.id.ln_retry)
    LinearLayout lnRetry;
    @BindView(R.id.btn_retry)
    Button btnRetry;
    @BindView(R.id.progress_loading_similar_clothes)
    ProgressBar progressLoadingSimilarClothes;

    @BindView(R.id.rc_product_recommend)
    RecyclerView rcClothesRecommend;
    @BindView(R.id.ln_retry_recommend)
    LinearLayout lnRetryRecommend;
    @BindView(R.id.btn_retry_recommend)
    Button btnRetryRecommend;
    @BindView(R.id.progress_loading_recommend_clothes)
    ProgressBar progressLoadingRecommendClothes;


    @BindView(R.id.img_rate)
    ImageView img_rate;
    ClothesViewModel clothesViewModel;
    BottomSheetDialog dialogRating;
    String clothesID;
    PayPalConfiguration configuration;
    int total;
    String size = "M";
    String color = "Đỏ";
    ItemBody orderBody;
    private LoadingDialog loadingDialog;
    private ClothesDetailPresenter clothesDetailPresenter;
    private RateClothesAdapter rateClothesAdapter;
    private SimilarClothesAdapter similarClothesAdapter;
    private SimilarClothesAdapter recommendClothesAdapter;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_detail);
        configuration = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(Config.CLIENT_ID);
        initVariables();

    }

    @Override
    public void onStart() {
        super.onStart();
        nestedScrollView.scrollTo(-1, -1);
        nestedScrollView.smoothScrollTo(0, 0);
        if (clothesID != null) {
            clothesDetailPresenter.firstFetchSimilarClothes(clothesID);
            clothesDetailPresenter.firstFetchRecommendClothes(clothesID);
        }
    }

    private void initVariables() {
        ButterKnife.bind(this);

        loadingDialog = new LoadingDialog(this);
        clothesDetailPresenter = new ClothesDetailPresenterImpl(this, this);
        nestedScrollView.scrollTo(-1, -1);
        nestedScrollView.smoothScrollTo(0, 0);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            actionBar.setTitle(R.string.clothes_detail);
        }
        loadingDialog = new LoadingDialog(this);
        fabSave.setOnClickListener(this);
        img_rate.setOnClickListener(this);
        btAddCart.setOnClickListener(this);
        btPay.setOnClickListener(this);
        clothesID = getIntent().getStringExtra(Constants.KEY_CLOTHES_ID);


        //start service paypal
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        startService(intent);

        clothesDetailPresenter.fetchClothesDetail(clothesID);
        rcClothesSimilar.setVisibility(View.VISIBLE);
        similarClothesAdapter = new SimilarClothesAdapter(this);
        similarClothesAdapter.setLoadingMoreListener(() -> clothesDetailPresenter.loadMoreSimilarClothes(clothesID));
        similarClothesAdapter.addOnItemClickListener((adapter, viewHolder, viewType, position) -> {
            ClothesPreview clothesPreview = similarClothesAdapter.getItem(position, ClothesPreview.class);
            Intent intent1 = new Intent(ClothesDetailActivity.this, ClothesDetailActivity.class);
            intent1.putExtra(Constants.KEY_CLOTHES_ID, clothesPreview.getId());
            startActivity(intent1);
        });
        rcClothesSimilar.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        rcClothesSimilar.setAdapter(similarClothesAdapter);

        rcClothesRecommend.setVisibility(View.VISIBLE);
        recommendClothesAdapter = new SimilarClothesAdapter(this);
        recommendClothesAdapter.setLoadingMoreListener(() -> clothesDetailPresenter.loadMoreRecommendClothes(clothesID));
        recommendClothesAdapter.addOnItemClickListener((adapter, viewHolder, viewType, position) -> {
            ClothesPreview clothesPreview = recommendClothesAdapter.getItem(position, ClothesPreview.class);
            Intent intent12 = new Intent(ClothesDetailActivity.this, ClothesDetailActivity.class);
            intent12.putExtra(Constants.KEY_CLOTHES_ID, clothesPreview.getId());
            startActivity(intent12);
        });
        rcClothesRecommend.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        rcClothesRecommend.setAdapter(recommendClothesAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.REQUEST_CODE_PAYPAL: {
                if (data != null) {
                    PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                    if (confirmation != null) {
                        try {
                            String paymentDetail = confirmation.toJSONObject().toString();
                            Log.i("onActivityResult: 11", paymentDetail);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            }
            case Constants.REQUEST_CODE_CLOTHES_STATE: {
                if (resultCode == Activity.RESULT_OK) {
                    clothesDetailPresenter.getClothesState(clothesID);
                }
                break;
            }
            case Constants.REQUEST_CODE_CLOTHES_ORDER: {
                if (resultCode == Activity.RESULT_OK) {
                    Intent intent = new Intent(ClothesDetailActivity.this, OrderActivity.class);
                    intent.putExtra(Constants.KEY_ORDER_CLOTHES, item);
                }
                break;
            }
        }

    }

    @Override
    public void showClothesState(boolean state) {
        fabSave.setBackgroundResource(state == true ? R.drawable.ic_save : R.drawable.ic_nosave);
    }

    public void processPayment() {
//        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(value), "USD", "Checkout for Kidd Store",
//                PayPalPayment.PAYMENT_INTENT_SALE);
//        Intent intent = new Intent(this, PaymentActivity.class);
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
//        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
//        startActivityForResult(intent, Constants.REQUEST_CODE_PAYPAL);
//        Set<ItemBody> setOrderBodies= new HashSet<> ();
//        setOrderBodies.add(orderBody);
//        clothesDetailPresenter.orderClothes(setOrderBodies);


        if (UserAuth.isUserLoggedIn(this)) {
            Intent intent = new Intent(ClothesDetailActivity.this, OrderActivity.class);
            intent.putExtra(Constants.KEY_ORDER_CLOTHES, item);
            startActivity(intent);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Bạn chưa đăng nhập . Bạn có muốn đăng nhập ngay để sử dụng chức năng này!")
                    .setTitle("Đăng nhập")
                    .setNegativeButton("Đăng nhập", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(ClothesDetailActivity.this, RegisterActivity.class);
                            intent.putExtra("key", "cancel");
                            startActivityForResult(intent, Constants.REQUEST_CODE_CLOTHES_ORDER);
                        }
                    })
                    .setPositiveButton("Không", null)
                    .show();
        }
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

    @Override
    public void showProgress() {
        loadingDialog.show();
    }

    @Override
    public void hideProgress() {
        loadingDialog.hide();
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void showClothesDetail(ClothesViewModel clothes) {
        this.clothesViewModel = clothes;
        Glide.with(this).load(clothes.getLogoUrl()).apply(new RequestOptions().placeholder(R.drawable.logo_clothes_wall)).into(imgClothes);
        tvNameClothes.setText(clothes.getName());
        tvCostClothes.setText(Utils.formatNumberMoney(clothes.getPrice()) + " đ");
        tvDescriptionCLothes.setText(clothes.getDescription());

        rateClothesAdapter = new RateClothesAdapter(this);

        if (clothes.getRateClothesViewModels().size() == 0) {
            tvAcountRate.setText("Sản phẩm chưa có lượt đánh giá nào");
            rcCustomerRate.setVisibility(View.GONE);
        } else {
            tvAcountRate.setText("số lượt đánh giá (" + clothes.getRateClothesViewModels().size() + ")");
        }
        fabSave.setBackgroundResource(clothes.isSaved() ? R.drawable.ic_save : R.drawable.ic_nosave);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        ratingClothes.setRating(clothes.getAvarageOfRate());
        tv_avarage_rate.setText(String.format("(%.1f/5)", clothes.getAvarageOfRate()));


        rateClothesAdapter = new RateClothesAdapter(ClothesDetailActivity.this);
        rcCustomerRate.setLayoutManager(linearLayoutManager);
        rcCustomerRate.setAdapter(rateClothesAdapter);
        rateClothesAdapter.addModels(clothes.getRateClothesViewModels(), false);
    }

    @Override
    public void showErrorLoading(String message) {
        lnRetry.setVisibility(View.VISIBLE);
        lnRetryRecommend.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgressSimilarClothes() {
        progressLoadingSimilarClothes.setVisibility(View.VISIBLE);
        rcClothesSimilar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgressSimilarClothes() {
        progressLoadingSimilarClothes.setVisibility(View.GONE);
        rcClothesSimilar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorSimilarClothes() {
        lnRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideErrorSimilarClothes() {
        lnRetry.setVisibility(View.GONE);
    }

    @Override
    public void showProgressRecommendClothes() {
        progressLoadingRecommendClothes.setVisibility(View.VISIBLE);
        rcClothesRecommend.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgressRecommendClothes() {
        progressLoadingRecommendClothes.setVisibility(View.GONE);
        rcClothesRecommend.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorRecommendClothes() {
        lnRetryRecommend.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideErrorRecommendClothes() {
        lnRetryRecommend.setVisibility(View.GONE);
    }

    @Override
    public void showSimilarLoadingMoreProgress() {
        similarClothesAdapter.showLoadingItem(true);
    }

    @Override
    public void hideSimilarLoadingMoreProgress() {
        similarClothesAdapter.hideLoadingItem();
    }

    @Override
    public void showRecommendLoadingMoreProgress() {
        recommendClothesAdapter.showLoadingItem(true);
    }

    @Override
    public void hideRecommendLoadingMoreProgress() {
        recommendClothesAdapter.hideLoadingItem();
    }

    @Override
    public void enableLoadingMore(boolean enable) {
        similarClothesAdapter.enableLoadingMore(enable);
    }

    @Override
    public void enableLoadingMoreRecommend(boolean enable) {
        recommendClothesAdapter.enableLoadingMore(enable);
    }

    @Override
    public void refreshSimilarClothes(List<ClothesPreview> similarClothes) {
        similarClothesAdapter.refresh(similarClothes);
    }

    @Override
    public void loadmoreSimilarClothes(List<ClothesPreview> similarClothes) {
        similarClothesAdapter.addModels(similarClothes, false);

    }

    @Override
    public void refreshRecommendClothes(List<ClothesPreview> similarClothes) {
        recommendClothesAdapter.refresh(similarClothes);
    }

    @Override
    public void loadmoreRecommendClothes(List<ClothesPreview> similarClothes) {
        recommendClothesAdapter.addModels(similarClothes, false);
    }

    @Override
    public void switchButtonSaveJobToSaved() {
        fabSave.setBackgroundResource(R.drawable.ic_save);
        clothesViewModel.setSaved(true);
    }

    @Override
    public void switchButtonSaveJobToUnSaved() {
        fabSave.setBackgroundResource(R.drawable.ic_nosave);
        clothesViewModel.setSaved(false);
    }

    @Override
    public void showListSimilarClothes() {
        rcClothesSimilar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideListSimilarClothes() {
        rcClothesSimilar.setVisibility(View.GONE);
    }

    @Override
    public void showListRecommendClothes() {
        rcClothesRecommend.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideListRecommendClothes() {
        rcClothesRecommend.setVisibility(View.GONE);
    }

    public void hideRatingDialog() {
        dialogRating.dismiss();
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void getAllRateClothes(List<RateClothesViewModel> rateClothesViewModelList) {
        rcCustomerRate.setVisibility(View.VISIBLE);
        rateClothesAdapter.clear();
        rateClothesAdapter.addModels(rateClothesViewModelList, false);
        rateClothesAdapter.notifyDataSetChanged();
        tvAcountRate.setText("số lượt đánh giá (" + rateClothesViewModelList.size() + ")");
        ratingClothes.setRating(getAvarageRating(rateClothesViewModelList));
        tv_avarage_rate.setText(String.format("(%.1f/5)", getAvarageRating(rateClothesViewModelList)));
    }

    @Override
    public void payAndBackToHomeScreen() {
        finish();
    }

    float getAvarageRating(List<RateClothesViewModel> rateClothesViewModelList) {
        int sum = 0;
        for (RateClothesViewModel r : rateClothesViewModelList) {
            sum += r.getRating();
        }
        return (float) sum / rateClothesViewModelList.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_pay: {
                showPayDialog();
                break;
            }
            case R.id.bt_add_cart: {
                showCartDialog();
                break;

            }
            case R.id.fab_save: {
                if (UserAuth.isUserLoggedIn(this)) {
                    if (clothesViewModel.isSaved()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("Bạn có chắc chắn không theo dõi sản phẩm này nữa ?")
                                .setTitle("Hủy theo dõi")
                                .setNegativeButton("Có", (dialogInterface, i) -> clothesDetailPresenter.deleteSavedClothes(clothesID))
                                .setPositiveButton("Không", null)
                                .show();
                    } else {
                        clothesDetailPresenter.saveClothes(clothesID);
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Bạn chưa đăng nhập . Bạn có muốn đăng nhập ngay để sử dụng chức năng này!")
                            .setTitle("Đăng nhập")
                            .setNegativeButton("Đăng nhập", (dialogInterface, i) -> {
                                Intent intent = new Intent(ClothesDetailActivity.this, RegisterActivity.class);
                                intent.putExtra("key", "cancel");
                                startActivityForResult(intent, Constants.REQUEST_CODE_CLOTHES_STATE);
                            })
                            .setPositiveButton("Không", null)
                            .show();
                }

                break;
            }
            case R.id.img_rate: {
                if (UserAuth.isUserLoggedIn(this)) {
                    showRateDialog();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Bạn chưa đăng nhập . Bạn có muốn đăng nhập ngay để sử dụng chức năng này!")
                            .setTitle("Đăng nhập")
                            .setNegativeButton("Đăng nhập", (dialogInterface, i) -> {
                                Intent intent = new Intent(ClothesDetailActivity.this, RegisterActivity.class);
                                intent.putExtra("key", "cancel");
                                startActivityForResult(intent, Constants.REQUEST_CODE_CLOTHES_STATE);
                            })
                            .setPositiveButton("Không", null)
                            .show();
                }


                break;
            }
        }

    }

    void showRateDialog() {
        dialogRating = new BottomSheetDialog(this);
        dialogRating.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogRating.setContentView(R.layout.rating_dialog);
        MaterialRatingBar ratingBar;
        EditText edt_msg;
        Button btn_submit;

        ratingBar = dialogRating.findViewById(R.id.rating_product);
        edt_msg = dialogRating.findViewById(R.id.edt_cmt);
        btn_submit = dialogRating.findViewById(R.id.btn_rate);

        btn_submit.setOnClickListener(v1 -> clothesDetailPresenter.rateClothes(clothesID, new RateClothesBody(edt_msg.getText().toString(),
                (int) ratingBar.getRating())));

        dialogRating.show();
       // Window window = dialogRating.getWindow();
       // window.setLayout(GridLayoutManager.LayoutParams.MATCH_PARENT, GridLayoutManager.LayoutParams.MATCH_PARENT);
    }

    @SuppressLint("SetTextI18n")
    void showCartDialog() {
        total = 1;
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(R.layout.dialog_select_clothes);
        AppCompatSpinner spinner_color;
        AppCompatSpinner spinner_size;
        TextView txt_sub;
        TextView txt_add;
        EditText edt_quanlity;
        Button btn_pay;
        SpinnerColorAdapter spinnerColorAdapter;
        SpinnerSizeAdapter spinnerSizeAdapter;
        spinner_color = dialog.findViewById(R.id.spinner_color);
        spinner_size = dialog.findViewById(R.id.spinner_size);
        txt_sub = dialog.findViewById(R.id.txt_sub);
        txt_add = dialog.findViewById(R.id.txt_add);
        edt_quanlity = dialog.findViewById(R.id.edt_quanlity);
        btn_pay = dialog.findViewById(R.id.btn_add_cart);
        List<String> lsSize = new ArrayList<>();
        List<String> lsColor = new ArrayList<>();
        initOrderDialog(lsSize, lsColor);
        spinnerColorAdapter = new SpinnerColorAdapter(this, -1, lsColor);
        spinnerSizeAdapter = new SpinnerSizeAdapter(this, -1, lsSize);
        spinner_color.setAdapter(spinnerColorAdapter);
        spinner_size.setAdapter(spinnerSizeAdapter);
        txt_sub.setOnClickListener(v -> {
            if (total >= 2) {
                total--;
                edt_quanlity.setText(total + "");
            }
        });
        txt_add.setOnClickListener(v -> {
            total++;
            edt_quanlity.setText(total + "");
        });

        spinner_color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                color = lsColor.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                size = lsSize.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_pay.setOnClickListener(v -> {
            Item item = new Item(this.clothesViewModel, total, color, size);
            ManageCart.getCart().plusToCart(item);
            Intent intent = new Intent(ClothesDetailActivity.this, CartActivity.class);
            startActivity(intent);
            finish();
        });
        dialog.show();
//        Window window = dialog.getWindow();
//        window.setLayout(GridLayoutManager.LayoutParams.MATCH_PARENT, GridLayoutManager.LayoutParams.MATCH_PARENT);
    }

    @SuppressLint("SetTextI18n")
    void showPayDialog() {
        total = 1;
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.item_dialog);

        AppCompatSpinner spinner_color;
        AppCompatSpinner spinner_size;
        TextView txt_sub;
        TextView txt_add;
        EditText edt_quanlity;
        EditText edt_total;
        Button btn_pay;
        SpinnerColorAdapter spinnerColorAdapter;
        SpinnerSizeAdapter spinnerSizeAdapter;

        spinner_color = dialog.findViewById(R.id.spinner_color);
        spinner_size = dialog.findViewById(R.id.spinner_size);
        txt_sub = dialog.findViewById(R.id.txt_sub);
        txt_add = dialog.findViewById(R.id.txt_add);
        edt_quanlity = dialog.findViewById(R.id.edt_quanlity);
        edt_total = dialog.findViewById(R.id.edt_total);
        btn_pay = dialog.findViewById(R.id.btn_pay);

        edt_total.setText(Utils.formatNumberMoney(clothesViewModel.getPrice()));
        List<String> lsSize = new ArrayList<>();
        List<String> lsColor = new ArrayList<>();
        initOrderDialog(lsSize, lsColor);
        spinnerColorAdapter = new SpinnerColorAdapter(this, -1, lsColor);
        spinnerSizeAdapter = new SpinnerSizeAdapter(this, -1, lsSize);
        spinner_color.setAdapter(spinnerColorAdapter);
        spinner_size.setAdapter(spinnerSizeAdapter);

        txt_sub.setOnClickListener(v -> {
            if (total >= 2) {
                total--;
                edt_quanlity.setText(total + "");
                edt_total.setText(Utils.formatNumberMoney(clothesViewModel.getPrice() * total));
            }
        });

        txt_add.setOnClickListener(v -> {
            total++;
            edt_quanlity.setText(total + "");
            edt_total.setText(Utils.formatNumberMoney(clothesViewModel.getPrice() * total));
        });

        spinner_color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                color = lsColor.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                size = lsSize.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btn_pay.setOnClickListener(v -> {
            item = new Item(this.clothesViewModel, total, color, size);
            processPayment();

        });


        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(GridLayoutManager.LayoutParams.MATCH_PARENT, GridLayoutManager.LayoutParams.MATCH_PARENT);
    }


    void initOrderDialog(List<String> lsSize, List<String> lsColor) {
        lsColor.add("Đỏ");
        lsColor.add("Xanh");
        lsColor.add("Cam");

        lsSize.add("M");
        lsSize.add("X");
        lsSize.add("L");
        lsSize.add("XL");
        lsSize.add("XXL");
        lsSize.add("XXXL");
    }


    @Override
    public void onLoadMore() {
        clothesDetailPresenter.loadMoreSimilarClothes(clothesID);
    }

    @Override
    public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int viewType, int position) {
        ClothesPreview clothesPreview = this.similarClothesAdapter.getItem(position, ClothesPreview.class);
        Intent intent = new Intent(this, ClothesDetailActivity.class);
        intent.putExtra(Constants.KEY_CLOTHES_ID, clothesPreview.getId());
        startActivity(intent);
    }

    @Override
    public void onClickButton(int rate, String msg) {
    }
}
