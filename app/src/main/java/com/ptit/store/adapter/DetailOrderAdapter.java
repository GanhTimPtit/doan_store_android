package com.ptit.store.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ptit.store.R;
import com.ptit.store.common.Utils;
import com.ptit.store.models.Item;
import com.ptit.store.models.response.ItemPreview;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by KingIT on 4/29/2018.
 */

public class DetailOrderAdapter extends RecyclerViewAdapter implements RecyclerViewAdapter.OnItemClickListener {


    public DetailOrderAdapter(Context context) {
        super(context, false);
    }

    @Override
    protected RecyclerView.ViewHolder initNormalViewHolder(ViewGroup parent) {
        View view = getInflater().inflate(R.layout.item_order_clothes, parent, false);
        return new CartAdapterHodel(view);
    }


    @Override
    protected void bindNormalViewHolder(NormalViewHolder holder, int position) {
        CartAdapterHodel cartAdapterHodel= (CartAdapterHodel) holder;
        ItemPreview item= getItem(position, ItemPreview.class);
        Glide.with(getContext()).load(item.getLogoUrl()).apply(new RequestOptions().placeholder(R.drawable.logo_clothes)).into(cartAdapterHodel.imgAvatar);
        cartAdapterHodel.tvName.setText(item.getName());
        cartAdapterHodel.tvSize.setText("Size: "+ item.getSize());
        switch (item.getColor()){
            case "Đỏ":{
                cartAdapterHodel.tvColorSelect.setBackgroundResource(R.drawable.ic_check_red);
                break;
            }
            case "Xanh":{
                cartAdapterHodel.tvColorSelect.setBackgroundResource(R.drawable.ic_check_green);
                break;
            }
            case "Cam":{
                cartAdapterHodel.tvColorSelect.setBackgroundResource(R.drawable.ic_check_ogen);
                break;
            }

        }
        cartAdapterHodel.tvCost.setText(Utils.formatNumberMoney(item.getPrice())+" đ");
        cartAdapterHodel.tvCount.setText(item.getAmount()+"");

    }
    @Override
    public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int viewType, int position) {
        if (isInSelectedMode() && viewType == VIEW_TYPE_NORMAL) {
            setSelectedItem(position, !isItemSelected(position));
        }
    }


    class CartAdapterHodel extends NormalViewHolder{
        @BindView(R.id.img_avatar)
        ImageView imgAvatar;
        @BindView(R.id.tv_full_name)
        TextView tvName;
        @BindView(R.id.tv_size)
        TextView tvSize;
        @BindView(R.id.tv_cost)
        TextView tvCost;
        @BindView(R.id.tv_color_select)
        TextView tvColorSelect;
        @BindView(R.id.tv_total_count)
        TextView tvCount;
        @BindView(R.id.img_delete_mark)
        ImageView imgDeleteMark;
        public CartAdapterHodel(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
