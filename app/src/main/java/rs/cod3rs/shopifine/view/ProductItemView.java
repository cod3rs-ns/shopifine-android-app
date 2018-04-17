package rs.cod3rs.shopifine.view;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import de.hdodenhof.circleimageview.CircleImageView;
import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.domain.Product;

@EViewGroup(R.layout.item_product)
public class ProductItemView extends LinearLayout {

    @ViewById
    CircleImageView productImage;

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
        Picasso.get().load(product.imageUrl).into(productImage);
        productName.setText(product.name);
        productCategory.setText(product.categoryId.toString());
        productPrice.setText(product.price.toString());
    }
}
