package com.ptit.store.view.shop.fragment.search;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ptit.store.R;
import com.ptit.store.adapter.ClothesSearchAdapter;
import com.ptit.store.adapter.RecyclerViewAdapter;
import com.ptit.store.common.Constants;
import com.ptit.store.common.Utils;
import com.ptit.store.custom.LoadingDialog;
import com.ptit.store.models.response.ClothesSearchPreview;
import com.ptit.store.presenter.search.SearchClothesPresenter;
import com.ptit.store.presenter.search.SearchClothesPresenterImpl;
import com.ptit.store.view.shop.clothes_detail.ClothesDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements SearchFragmentView,
        RecyclerViewAdapter.OnItemClickListener {

    @BindView(R.id.rc_search_clothes)
    RecyclerView rcSearchClothes;

    SearchView searchView;
    private ClothesSearchAdapter adapter;
    private SearchClothesPresenter presenter;
    private List<ClothesSearchPreview> clothesSearchPreviews;
    private LoadingDialog loadingDialog;
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        initData();
    }

    private void initData() {
        Context context = getActivity();
        presenter = new SearchClothesPresenterImpl(getContext(), this);
        loadingDialog= new LoadingDialog(context);
        adapter = new ClothesSearchAdapter(context);
        adapter.addOnItemClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        rcSearchClothes.setLayoutManager(linearLayoutManager);
        rcSearchClothes.addItemDecoration(new DividerItemDecoration(context, linearLayoutManager.getOrientation()));
        rcSearchClothes.setHasFixedSize(true);
        rcSearchClothes.setAdapter(adapter);
        presenter.fetchListClothesSearchPreview();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                if (adapter.getItemCount() != 0) {
                    rcSearchClothes.setVisibility(View.VISIBLE);
                }
            } else {
                rcSearchClothes.setVisibility(View.INVISIBLE);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(clothesSearchPreviews==null) presenter.fetchListClothesSearchPreview();
                if(newText.isEmpty()){
                    adapter.clear();
                    adapter.addModels(clothesSearchPreviews,false);
                }else{
                    loadUiSearch(newText);
                }
                return false;
            }
        });
        searchView.setQueryHint(getString(R.string.search));
        super.onCreateOptionsMenu(menu, inflater);
    }
    private void loadUiSearch(String text) {
        List<ClothesSearchPreview> ls = new ArrayList<>();
        for (int i = 0; i < clothesSearchPreviews.size(); i++) {
            String aux_name = Utils.deAccent(clothesSearchPreviews.get(i).getName());
            String aux_text = Utils.deAccent(text);
            boolean found = aux_name.replaceAll(" ", "").toUpperCase().contains(aux_text.replaceAll(" ", "").toUpperCase());
            if (found) {
                ls.add(clothesSearchPreviews.get(i));
            }
        }
        adapter.clear();
        adapter.addModels(ls,false);
    }

    @Override
    public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int viewType, int position) {
        ClothesSearchPreview clothesPreview = this.adapter.getItem(position, ClothesSearchPreview.class);
        Intent intent = new Intent(getContext(), ClothesDetailActivity.class);
        intent.putExtra(Constants.KEY_CLOTHES_ID, clothesPreview.getId());
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
    public void showClothesSearchPreviews(List<ClothesSearchPreview> clothesSearchPreviews) {
        this.clothesSearchPreviews= clothesSearchPreviews;
    }

}
