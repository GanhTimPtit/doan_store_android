package com.ptit.store.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ptit.store.R;
import com.ptit.store.common.Utils;
import com.ptit.store.models.response.ClothesPreview;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by KingIT on 4/26/2018.
 */

public class SimilarClothesAdapter  extends EndlessLoadingRecyclerViewAdapter{
    public SimilarClothesAdapter(Context context) {
        super(context, false);
    }

    @Override
    protected RecyclerView.ViewHolder initLoadingViewHolder(ViewGroup parent) {
        View loadingView = getInflater().inflate(R.layout.item_load_more, parent, false);
        return new LoadingViewHolder(loadingView);
    }

    @Override
    protected void bindLoadingViewHolder(LoadingViewHolder loadingViewHolder, int position) {

    }

    @Override
    protected RecyclerView.ViewHolder initNormalViewHolder(ViewGroup parent) {
        View view = getInflater().inflate(R.layout.item_similar_clothes, parent, false);
        return new ClothesPreviewHodel(view);
    }

    @Override
    protected void bindNormalViewHolder(NormalViewHolder holder, int position) {
        ClothesPreviewHodel clothesPreviewHodel= (ClothesPreviewHodel) holder;
        ClothesPreview clothesPreview= getItem(position, ClothesPreview.class);
        Glide.with(getContext()).load(clothesPreview.getLogoUrl()).apply(new RequestOptions().placeholder(R.drawable.logo_clothes)).into(clothesPreviewHodel.imgAvatar);
        clothesPreviewHodel.tvAcountSave.setText("("+clothesPreview.getNumberSave()+")");
        clothesPreviewHodel.tvName.setText(clothesPreview.getName());
        clothesPreviewHodel.tvType.setText(clothesPreview.getCategory());
//        DecimalFormat decimalFormat= new DecimalFormat("###.###.###");
        clothesPreviewHodel.tvCost.setText(Utils.formatNumberMoney(clothesPreview.getPrice())+" đ");
        clothesPreviewHodel.ratingClothes.setRating(clothesPreview.getAvarageOfRate());
        clothesPreviewHodel.tvAcountRate.setText("(" + clothesPreview.getNumberAvageOfRate() + ")");

    }
    class ClothesPreviewHodel extends NormalViewHolder{
        @BindView(R.id.img_avatar)
        ImageView imgAvatar;
        @BindView(R.id.tv_acount_save)
        TextView tvAcountSave;
        @BindView(R.id.tv_full_name)
        TextView tvName;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_cost)
        TextView tvCost;
        @BindView(R.id.rating_product)
        MaterialRatingBar ratingClothes;
        @BindView(R.id.tv_acount_rate)
        TextView tvAcountRate;
        public ClothesPreviewHodel(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
