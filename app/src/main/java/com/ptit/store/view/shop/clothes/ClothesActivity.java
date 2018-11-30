package com.ptit.store.view.shop.clothes;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import com.ptit.store.R;
import com.ptit.store.adapter.ClothesPreviewAdapter;
import com.ptit.store.adapter.EndlessLoadingRecyclerViewAdapter;
import com.ptit.store.adapter.RecyclerViewAdapter;
import com.ptit.store.common.Constants;
import com.ptit.store.models.Category;
import com.ptit.store.models.response.ClothesPreview;
import com.ptit.store.models.response.PageList;
import com.ptit.store.presenter.shop.clothes.GetPageClothesPresenter;
import com.ptit.store.presenter.shop.clothes.GetPageClothesPresenterImpl;
import com.ptit.store.view.shop.clothes_detail.ClothesDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClothesActivity extends AppCompatActivity implements ClothesActivityView,
        RecyclerViewAdapter.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener,
        EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener{
    @BindView(R.id.rc_posts)
    RecyclerView mRecycleView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    SearchView searchView;
    ClothesPreviewAdapter adapter;
    GetPageClothesPresenter presenter;
    List<ClothesPreview> clothesPreviews;
    private Category category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        toolbar.setNavigationOnClickListener(v->{
            finish();
        });
        category =(Category) getIntent().getSerializableExtra(Constants.KEY_CATEGORY_ID);
        presenter= new GetPageClothesPresenterImpl(this, this);
        if (category != null) {
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
                actionBar.setTitle(category.getTitle());
            }
        }
        initData();
    }
    private void initData() {
        adapter = new ClothesPreviewAdapter(this);
        adapter.addOnItemClickListener(this);
        adapter.setLoadingMoreListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(linearLayoutManager);
        mRecycleView.addItemDecoration(new DividerItemDecoration(this, linearLayoutManager.getOrientation()));
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setAdapter(adapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryLight, R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(this);

        presenter.refreshClothesPreviews(category.getId());
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
    public void addClothesPreviews(PageList<ClothesPreview> clothesPreviewPageList) {
        adapter.addModels(clothesPreviewPageList.getResults(), false);
    }

    @Override
    public void refreshClothesPreview(PageList<ClothesPreview> clothesPreviewPageList) {
        adapter.refresh(clothesPreviewPageList.getResults());
        this.clothesPreviews = clothesPreviewPageList.getResults();
    }

    @Override
    public void onRefresh() {
        presenter.refreshClothesPreviews(category.getId());
    }

    @Override
    public void onLoadMore() {
        presenter.loadMoreClothesPreviews(category.getId());
    }

    @Override
    public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int viewType, int position) {
        ClothesPreview clothesPreview = this.adapter.getItem(position, ClothesPreview.class);
        Intent intent = new Intent(ClothesActivity.this, ClothesDetailActivity.class);
        intent.putExtra(Constants.KEY_CLOTHES_ID, clothesPreview.getId());
        startActivity(intent);
    }
}
