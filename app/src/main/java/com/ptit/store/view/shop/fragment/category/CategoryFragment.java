package com.ptit.store.view.shop.fragment.category;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ptit.store.R;
import com.ptit.store.adapter.CategoryAdapter;
import com.ptit.store.adapter.RecyclerViewAdapter;
import com.ptit.store.adapter.SliderAdapter;
import com.ptit.store.common.Constants;
import com.ptit.store.custom.LoadingDialog;
import com.ptit.store.models.Category;
import com.ptit.store.models.Advertisement;
import com.ptit.store.presenter.shop.category.CategoryPresenter;
import com.ptit.store.presenter.shop.category.CategoryPresenterImpl;
import com.ptit.store.view.shop.clothes.ClothesActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment implements CategoryFragmentView{
    @BindView(R.id.viewpager_slider)
    ViewPager viewPagersSlider;
    @BindView(R.id.indicator)
    TabLayout indicator;
    @BindView(R.id.rc_category_boy)
    RecyclerView rcViewBoy;
    @BindView(R.id.rc_category_girl)
    RecyclerView rcViewGirl;

    @BindView(R.id.tv_title_special1)
    TextView tvSpecial1;
    @BindView(R.id.ll_category_sp1)
    LinearLayout llSpecial1;
    @BindView(R.id.img_category_sp1)
    ImageView imgSpecial1;
    @BindView(R.id.tv_title_special2)
    TextView tvSpecial2;
    @BindView(R.id.ll_category_sp2)
    LinearLayout llSpecial2;
    @BindView(R.id.img_category_sp2)
    ImageView imgSpecial2;


    List<Advertisement> advertisements;

    private CategoryAdapter categoryAdapterBoy;
    private CategoryAdapter categoryAdapterGirl;
    private SliderAdapter sliderAdapter;
    private LoadingDialog loadingDialog;
    private CategoryPresenter categoryPresenter;
    Timer timer;
    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        initData();
    }


    @Override
    public void onStop() {
        super.onStop();
        timer.cancel();
    }

    private void initData(){
        loadingDialog= new LoadingDialog(getContext());
        categoryPresenter= new CategoryPresenterImpl(getContext(),this);
        advertisements= new ArrayList<>();
        categoryAdapterBoy= new CategoryAdapter(getContext());
        categoryAdapterGirl= new CategoryAdapter(getContext());
        sliderAdapter= new SliderAdapter(getContext(), advertisements);
        viewPagersSlider.setAdapter(sliderAdapter);
        indicator.setupWithViewPager(viewPagersSlider, true);
        timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 4000, 6000);
        categoryPresenter.fetchListAdvertisement();
        categoryPresenter.fetchListCategory();
        rcViewBoy.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        rcViewBoy.setAdapter(categoryAdapterBoy);
        rcViewGirl.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        rcViewGirl.setAdapter(categoryAdapterGirl);
        categoryAdapterBoy.addOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int viewType, int position) {
                Category category = categoryAdapterBoy.getItem(position, Category.class);
                Intent intent = new Intent(getContext(), ClothesActivity.class);
                intent.putExtra(Constants.KEY_CATEGORY_ID, category);
                startActivity(intent);
            }
        });
        categoryAdapterGirl.addOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int viewType, int position) {
                Category category = categoryAdapterGirl.getItem(position, Category.class);
                Intent intent = new Intent(getContext(), ClothesActivity.class);
                intent.putExtra(Constants.KEY_CATEGORY_ID, category);
                startActivity(intent);
            }
        });

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
    public void showCategoryList(List<Category> categoryList) {
        List<Category> categoriesBoy= new ArrayList<>();
        List<Category> categoriesGirl=new ArrayList<>();
        List<Category> categoriesSpecial=new ArrayList<>();
        for (Category category: categoryList) {
            if(category.getGender()==0){
                categoriesGirl.add(category);
            }else if(category.getGender()==1){
                categoriesBoy.add(category);
            }else{
                categoriesSpecial.add(category);
            }
        }
        inItDataSpecial(categoriesSpecial);
        categoryAdapterBoy.addModels(categoriesBoy, false);
        categoryAdapterGirl.addModels(categoriesGirl, false);
    }
    public void inItDataSpecial(List<Category> categoryList){
        tvSpecial1.setText(categoryList.get(0).getTitle());
        Glide.with(getContext()).load(categoryList.get(0).getLogoUrl()).apply(new RequestOptions().placeholder(R.drawable.logo_clothes)).into(imgSpecial1);
        llSpecial1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ClothesActivity.class);
                intent.putExtra(Constants.KEY_CATEGORY_ID, categoryList.get(0));
                startActivity(intent);
            }
        });
        tvSpecial2.setText(categoryList.get(1).getTitle());
        Glide.with(getContext()).load(categoryList.get(1).getLogoUrl()).apply(new RequestOptions().placeholder(R.drawable.logo_clothes)).into(imgSpecial2);
        llSpecial2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ClothesActivity.class);
                intent.putExtra(Constants.KEY_CATEGORY_ID, categoryList.get(1));
                startActivity(intent);
            }
        });

    }

    @Override
    public void showAdvertisementList(List<Advertisement> advertisementList) {
        advertisements.addAll(advertisementList);
        sliderAdapter.notifyDataSetChanged();
    }


    @Override
    public void showServerErrorDialog() {

    }



    @Override
    public void showNoInternetConnectionDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.no_internet_connection)
                .setMessage(R.string.please_make_sure_the_intenet_connection_is_enable)
                .setCancelable(false)
                .setPositiveButton(R.string.retry, (dialogInterface, i) -> {
                    categoryPresenter.fetchListCategory();
                    categoryPresenter.fetchListAdvertisement();
                }).show();
    }

    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPagersSlider.getCurrentItem() < advertisements.size() - 1) {
                        viewPagersSlider.setCurrentItem(viewPagersSlider.getCurrentItem() + 1);
                    } else {
                        viewPagersSlider.setCurrentItem(0);
                    }
                }
            });
        }
    }

}
