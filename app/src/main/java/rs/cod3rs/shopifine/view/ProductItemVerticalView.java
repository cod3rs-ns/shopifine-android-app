package rs.cod3rs.shopifine.view;

import android.content.Context;
import android.widget.LinearLayout;

import org.androidannotations.annotations.EViewGroup;

import rs.cod3rs.shopifine.R;

@EViewGroup(R.layout.item_product_vertical)
public class ProductItemVerticalView extends LinearLayout {

    public ProductItemVerticalView(final Context context) {
        super(context);
    }

}
