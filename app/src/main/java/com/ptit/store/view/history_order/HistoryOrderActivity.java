package com.ptit.store.view.history_order;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.ptit.store.R;
import com.ptit.store.adapter.EndlessLoadingRecyclerViewAdapter;
import com.ptit.store.adapter.OrderPreviewAdapter;
import com.ptit.store.adapter.RecyclerViewAdapter;
import com.ptit.store.common.Constants;
import com.ptit.store.custom.LoadingDialog;
import com.ptit.store.models.response.ItemPreview;
import com.ptit.store.models.response.OrderPreview;
import com.ptit.store.presenter.history.HistoryOrderPresenter;
import com.ptit.store.presenter.history.HistoryOrderPresenterImpl;
import com.ptit.store.view.manage_order.detail_order.DetailOrderActivity;
import com.ptit.store.view.shop.clothes_detail.ClothesDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryOrderActivity extends AppCompatActivity implements HistoryOrderView,
        RecyclerViewAdapter.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener,
        EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener{

    @BindView(R.id.rc_order)
    RecyclerView rc_order;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rl_no_result)
    RelativeLayout rl_no_result;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    LoadingDialog loadingDialog;
    OrderPreviewAdapter adapter;
    HistoryOrderPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order);
        ButterKnife.bind(this);
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    void initData() {
        presenter = new HistoryOrderPresenterImpl(this,this);
        loadingDialog = new LoadingDialog(this);
        adapter = new OrderPreviewAdapter(this,null);
        adapter.addOnItemClickListener(this);
        adapter.setLoadingMoreListener(this);

        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v->{
            finish();
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rc_order.setLayoutManager(linearLayoutManager);
        rc_order.addItemDecoration(new DividerItemDecoration(this, linearLayoutManager.getOrientation()));
        rc_order.setHasFixedSize(true);
        rc_order.setAdapter(adapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryLight, R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(this);

        presenter.onRefreshHistoryOrder();

    }

    @Override
    public void showLoadMoreProgress() {
        adapter.showLoadingItem(true);
    }

    @Override
    public void hideLoadMoreProgress() {
        adapter.hideLoadingItem();
    }

    @Override
    public void enableLoadMore(boolean enable) {
        adapter.enableLoadingMore(enable);
    }

    @Override
    public void enableRefreshing(boolean enable) {
        swipeRefreshLayout.setEnabled(enable);
    }

    @Override
    public void showRefreshingProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideRefreshingProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void loadMoreOrderViewModel(List<OrderPreview> orderPreviews) {
        adapter.addModels(orderPreviews, false);
    }

    @Override
    public void refreshOrderViewModel(List<OrderPreview> orderPreviews) {
        adapter.refresh(orderPreviews);
    }

    @Override
    public void showNoResult() {
        swipeRefreshLayout.setVisibility(View.GONE);
        rl_no_result.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoResult() {
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        rl_no_result.setVisibility(View.GONE);
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
    public void onRefresh() {
        presenter.onRefreshHistoryOrder();
    }

    @Override
    public void onLoadMore() {
        presenter.onLoadmoreHistoryOrder();
    }

    @Override
    public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int viewType, int position) {

    }
}
