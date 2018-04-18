package rs.cod3rs.shopifine.view;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.domain.Product;

@EViewGroup(R.layout.item_product)
public class ProductItemView extends LinearLayout {

    @ViewById
    TextView productName;

    @ViewById
    TextView productCategory;

    @ViewById
    TextView productPrice;

    public ProductItemView(final Context context) {
        super(context);
    }

    public void bind(final Product product) {
        productName.setText(product.name);
        productCategory.setText(product.categoryId.toString());
        productPrice.setText(product.price.toString());
    }
}
