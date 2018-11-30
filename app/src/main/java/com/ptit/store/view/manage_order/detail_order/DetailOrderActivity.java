package com.ptit.store.view.manage_order.detail_order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import com.ptit.store.R;
import com.ptit.store.adapter.DetailOrderAdapter;
import com.ptit.store.adapter.RecyclerViewAdapter;
import com.ptit.store.common.Constants;
import com.ptit.store.custom.LoadingDialog;
import com.ptit.store.models.response.ItemPreview;
import com.ptit.store.presenter.manage_order.detail_order.GetItemsPresenter;
import com.ptit.store.presenter.manage_order.detail_order.GetItemsPresenterImpl;
import com.ptit.store.view.shop.clothes_detail.ClothesDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailOrderActivity extends AppCompatActivity implements RecyclerViewAdapter.OnItemClickListener,DetailOrderActivityView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rc_item)
    RecyclerView rcItem;

    private LoadingDialog loadingDialog;
    private GetItemsPresenter getItemsPresenter;
    private DetailOrderAdapter detailOrderAdapter;
    private String orderID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        ButterKnife.bind(this);
        initData();

    }

    private void initData() {
        loadingDialog= new LoadingDialog(this);
        orderID=(String) getIntent().getSerializableExtra(Constants.KEY_ORDER_CLOTHES);
        getItemsPresenter= new GetItemsPresenterImpl(this, this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            actionBar.setTitle(R.string.detail_order);
        }
        detailOrderAdapter = new DetailOrderAdapter(this);

        detailOrderAdapter.addOnItemClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcItem.setLayoutManager(linearLayoutManager);
        rcItem.addItemDecoration(new DividerItemDecoration(this, linearLayoutManager.getOrientation()));
        rcItem.setHasFixedSize(true);
        rcItem.setAdapter(detailOrderAdapter);
        getItemsPresenter.showItemPreviews(orderID);
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
    public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int viewType, int position) {
        ItemPreview itemPreview = this.detailOrderAdapter.getItem(position, ItemPreview.class);
        Intent intent = new Intent(DetailOrderActivity.this, ClothesDetailActivity.class);
        intent.putExtra(Constants.KEY_CLOTHES_ID, itemPreview.getClothesID());
        startActivity(intent);
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
    public void showItemPreview(List<ItemPreview> itemPreviews) {
        detailOrderAdapter.addModels(itemPreviews, false);
    }
}
