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
import com.ptit.store.models.Category;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAdapter extends RecyclerViewAdapter{

    public CategoryAdapter(Context context) {
        super(context, false);
    }

    @Override
    protected RecyclerView.ViewHolder initNormalViewHolder(ViewGroup parent) {
        View view = getInflater().inflate(R.layout.item_category_clothes, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    protected void bindNormalViewHolder(NormalViewHolder holder, int position) {
        CategoryViewHolder categoryViewHolder= (CategoryViewHolder) holder;
        Category category= getItem(position, Category.class);
        Glide.with(getContext()).load(category.getLogoUrl()).apply(new RequestOptions().placeholder(R.drawable.logo_clothes)).into(categoryViewHolder.imgCategory);
        categoryViewHolder.tvTitle.setText(category.getTitle());
    }
    class CategoryViewHolder extends NormalViewHolder {
        @BindView(R.id.img_category)
        ImageView imgCategory;
        @BindView(R.id.txt_title)
        TextView tvTitle;
        public CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
