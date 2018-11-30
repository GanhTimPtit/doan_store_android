package com.ptit.store.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.ptit.store.R;
import com.ptit.store.models.response.ClothesSearchPreview;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClothesSearchAdapter extends RecyclerViewAdapter{

    public ClothesSearchAdapter(Context context) {
        super(context, false);
    }

    @Override
    protected RecyclerView.ViewHolder initNormalViewHolder(ViewGroup parent) {
        View view = getInflater().inflate(R.layout.item_search_clothes, parent, false);
        return new ClothesSearchViewHolder(view);
    }

    @Override
    protected void bindNormalViewHolder(NormalViewHolder holder, int position) {
        ClothesSearchViewHolder clothesSearchViewHolder = (ClothesSearchViewHolder) holder;
        ClothesSearchPreview clothesSearchPreview= getItem(position, ClothesSearchPreview.class);
        clothesSearchViewHolder.tvName.setText(clothesSearchPreview.getName());
    }
    class ClothesSearchViewHolder extends NormalViewHolder {
        @BindView(R.id.txt_name)
        TextView tvName;
        public ClothesSearchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
