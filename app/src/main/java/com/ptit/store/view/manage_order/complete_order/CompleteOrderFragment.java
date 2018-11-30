package com.ptit.store.view.manage_order.complete_order;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ptit.store.R;
import com.ptit.store.adapter.EndlessLoadingRecyclerViewAdapter;
import com.ptit.store.adapter.OrderPreviewAdapter;
import com.ptit.store.adapter.RecyclerViewAdapter;
import com.ptit.store.common.Constants;
import com.ptit.store.custom.LoadingDialog;
import com.ptit.store.models.response.OrderPreview;
import com.ptit.store.presenter.manage_order.order.GetOrderPresenter;
import com.ptit.store.presenter.manage_order.order.GetOrderPresenterImpl;
import com.ptit.store.view.manage_order.ManageOrderFragmentView;
import com.ptit.store.view.manage_order.detail_order.DetailOrderActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompleteOrderFragment extends Fragment implements ManageOrderFragmentView,
        RecyclerViewAdapter.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener,
        EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener{

    @BindView(R.id.rc_order)
    RecyclerView rc_order;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rl_no_result)
    RelativeLayout rl_no_result;
    private LoadingDialog loadingDialog;
    private OrderPreviewAdapter adapter;
    private GetOrderPresenter presenter;

    public CompleteOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_complete_order, container, false);
        ButterKnife.bind(this,view);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        initData();
    }

    @Override
    public void onStart() {
        super.onStart();
    //    presenter.onRefreshOrder(-1);
    }

    void initData() {
        presenter = new GetOrderPresenterImpl(getContext(),this);
        loadingDialog = new LoadingDialog(getContext());
        adapter = new OrderPreviewAdapter(getContext(),null);
        adapter.addOnItemClickListener(this);
        adapter.setLoadingMoreListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rc_order.setLayoutManager(linearLayoutManager);
        rc_order.addItemDecoration(new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation()));
        rc_order.setHasFixedSize(true);
        rc_order.setAdapter(adapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryLight, R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(this);
        presenter.onRefreshOrder(-1);

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
    public void loadMoreOrderViewModel(List<OrderPreview> confirmOrderPreviews) {
        adapter.addModels(confirmOrderPreviews, false);
    }

    @Override
    public void refreshOrderViewModel(List<OrderPreview> confirmOrderPreviews) {
        adapter.refresh(confirmOrderPreviews);
    }

    @Override
    public void showNoResult() {
        rl_no_result.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoResult() {
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
    public void deleteOrderSuccess() {
    }

    @Override
    public void onRefresh() {
        presenter.onRefreshOrder(-1);
    }

    @Override
    public void onLoadMore() {
        presenter.onLoadmoreOrder(-1);
    }

    @Override
    public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int viewType, int position) {
        Intent intent = new Intent(getContext(), DetailOrderActivity.class);
        OrderPreview orderPreview = this.adapter.getItem(position, OrderPreview.class);
        intent.putExtra(Constants.KEY_ORDER_CLOTHES, orderPreview.getId());
        startActivity(intent);
    }

}
