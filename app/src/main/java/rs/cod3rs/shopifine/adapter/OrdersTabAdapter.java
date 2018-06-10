package rs.cod3rs.shopifine.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import rs.cod3rs.shopifine.domain.OrderState;
import rs.cod3rs.shopifine.fragment.OrdersFragmentTab_;

public class OrdersTabAdapter extends FragmentStatePagerAdapter {

    private int numOfTabs;

    public OrdersTabAdapter(final FragmentManager fm, final int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(final int position) {

        switch (position) {
            case 0:
                return OrdersFragmentTab_.builder().orderFragmentType(OrderState.ORDERED).build();
            case 1:
                return OrdersFragmentTab_.builder().orderFragmentType(OrderState.DISPATCHED).build();
            case 2:
                return OrdersFragmentTab_.builder().orderFragmentType(OrderState.SUCCESSFUL).build();
            case 3:
                return OrdersFragmentTab_.builder().orderFragmentType(OrderState.CANCELLED).build();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

}
