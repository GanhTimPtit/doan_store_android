package com.ptit.store.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ptit.store.MainActivity;
import com.ptit.store.R;
import com.ptit.store.common.Utils;
import com.ptit.store.models.response.OrderPreview;
import com.ptit.store.view.order.OrderActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class OrderPreviewAdapter extends EndlessLoadingRecyclerViewAdapter {
    private OnButtonDeleteClick onButtonDeleteClick;
    public OrderPreviewAdapter(Context context, OnButtonDeleteClick onButtonDeleteClick) {
        super(context, false);
        this.onButtonDeleteClick= onButtonDeleteClick;
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
        View view = getInflater().inflate(R.layout.item_order_preview, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    protected void bindNormalViewHolder(NormalViewHolder holder, int position) {
        OrderViewHolder viewHolder = (OrderViewHolder) holder;
        OrderPreview orderPreview = getItem(position,OrderPreview.class);
        viewHolder.tvName.setText("Người Nhận: "+orderPreview.getNameCustomer());
        viewHolder.tvPhone.setText(orderPreview.getPhone());
        viewHolder.tvLocation.setText(orderPreview.getLocation());
        viewHolder.tvLocation.setSelected(true);
        viewHolder.tvprice.setText(Utils.formatNumberMoney(orderPreview.getTotalCost())+" đ");
        viewHolder.tvOrderDate.setText(Utils.getDateFromMilliseconds(orderPreview.getCreatedDate()));
        viewHolder.tvPayment.setText("Hình thức: "+orderPreview.getPayments());
        switch (orderPreview.getStatus()){
            case 0:{
                viewHolder.imgStatus.setImageResource(R.drawable.ic_pending);
                viewHolder.imgClose.setVisibility(View.VISIBLE);
                break;
            }
            case 1:{
                viewHolder.imgStatus.setImageResource(R.drawable.ic_complete_order);
                viewHolder.imgDeleteState.setVisibility(View.GONE);
                break;
            }
            case 2:{
                viewHolder.imgStatus.setImageResource(R.drawable.ic_pending);
                viewHolder.imgDeleteState.setVisibility(View.VISIBLE);
                break;
            }
            case 3:{
                viewHolder.imgStatus.setImageResource(R.drawable.ic_delivery_order);
                viewHolder.imgDeleteState.setVisibility(View.VISIBLE);
                break;
            }
            case 4:{
                viewHolder.imgStatus.setImageResource(R.drawable.ic_delivery_order);
                break;
            }
        }
    }

    class OrderViewHolder extends NormalViewHolder {
        @BindView(R.id.txt_title)
        TextView tvName;
        @BindView(R.id.img_state)
        ImageView imgStatus;
        @BindView(R.id.txt_phone)
        TextView tvPhone;
        @BindView(R.id.txt_price)
        TextView tvprice;
        @BindView(R.id.txt_location)
        TextView tvLocation;
        @BindView(R.id.txt_order_date)
        TextView tvOrderDate;
        @BindView(R.id.txt_payment)
        TextView tvPayment;
        @BindView(R.id.img_close)
        ImageView imgClose;
        @BindView(R.id.img_delete_state)
        ImageView imgDeleteState;

        public OrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            imgClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new android.app.AlertDialog.Builder(getContext())
                            .setTitle(R.string.delete_order)
                            .setMessage("Bạn có chắc chắn muốn hủy bỏ đơn đặt hàng này?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", (dialogInterface, i) -> {
                                onButtonDeleteClick.onClick(getAdapterPosition());
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            });
        }
    }
    public interface OnButtonDeleteClick{
        void onClick(int pos);
    }
}
