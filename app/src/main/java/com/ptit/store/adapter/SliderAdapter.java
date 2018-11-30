package com.ptit.store.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ptit.store.R;
import com.ptit.store.models.Advertisement;

import java.util.List;

public class SliderAdapter extends PagerAdapter {

    private Context context;
    private List<Advertisement> advertisements;

    public SliderAdapter(Context context, List<Advertisement> advertisements) {
        this.context = context;
        this.advertisements = advertisements;
    }

    @Override
    public int getCount() {
        return advertisements.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=null;
        ViewHolderSlider viewHolder= new ViewHolderSlider();
        if(view==null) {
            view = inflater.inflate(R.layout.item_slider, null);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.img_logo);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolderSlider) view.getTag();
        }
        Glide.with(context).load(advertisements.get(position).getLogoUrl()).apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background)).into(viewHolder.imageView);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(advertisements.get(position).getLinkUrl()));
                context.startActivity(i);
            }
        });
        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);

        return view;
    }
    class ViewHolderSlider{
        public ImageView imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}