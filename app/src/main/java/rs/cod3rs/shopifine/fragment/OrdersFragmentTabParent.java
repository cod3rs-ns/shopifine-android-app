package rs.cod3rs.shopifine.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.adapter.OrdersTabAdapter;

@EFragment(R.layout.fragment_orders_tab_parent)
public class OrdersFragmentTabParent extends Fragment {

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.tab_layout)
    TabLayout tabLayout;

    @ViewById(R.id.pager)
    ViewPager viewPager;

    @AfterViews
    public void after() {
        tabLayout.addTab(tabLayout.newTab().setText(R.string.first_order_tab));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.second_order_tab));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.third_order_tab));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.fourth_order_tab));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final OrdersTabAdapter adapter =
                new OrdersTabAdapter(
                        getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(
                new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {}

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {}
                });
    }
}
