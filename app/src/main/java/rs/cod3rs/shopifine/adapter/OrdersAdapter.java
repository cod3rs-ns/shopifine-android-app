package rs.cod3rs.shopifine.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import rs.cod3rs.shopifine.fragment.OrdersFragmentTab;

public class OrdersAdapter extends FragmentStatePagerAdapter {
    private int numOfTabs;

    public OrdersAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new OrdersFragmentTab();
            case 1:
                return new OrdersFragmentTab();
            case 2:
                return new OrdersFragmentTab();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

}