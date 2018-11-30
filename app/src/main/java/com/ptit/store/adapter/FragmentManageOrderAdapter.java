package com.ptit.store.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ptit.store.R;
import com.ptit.store.view.manage_order.complete_order.CompleteOrderFragment;
import com.ptit.store.view.manage_order.delivery_order.DeliveryOrderFragment;
import com.ptit.store.view.manage_order.wait_order.WaitOrderFragment;

import java.util.ArrayList;
import java.util.List;


public class FragmentManageOrderAdapter extends FragmentPagerAdapter {
    List<Fragment> lsFragment;
    Context context;

    public FragmentManageOrderAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        lsFragment = new ArrayList<>();
        lsFragment.add(new WaitOrderFragment());
        lsFragment.add(new DeliveryOrderFragment());
        lsFragment.add(new CompleteOrderFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return lsFragment.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: {
                return context.getString(R.string.wait_confirm);
            }
            case 1: {
                return context.getString(R.string.delivery);
            }
            case 2: {
                return context.getString(R.string.completed);
            }
        }
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return lsFragment.size();
    }

}
