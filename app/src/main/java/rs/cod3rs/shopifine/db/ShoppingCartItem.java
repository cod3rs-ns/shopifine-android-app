package rs.cod3rs.shopifine.db;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Objects;

import rs.cod3rs.shopifine.domain.Product;

@DatabaseTable(tableName = "shopping_cart_items")
public class ShoppingCartItem {

    @DatabaseField(columnName = "id", generatedId = true)
    public Integer id;

    @DatabaseField(columnName = "user_id")
    public Integer userId;

    @DatabaseField(columnName = "product_id")
    public Long productId;

    @DatabaseField(columnName = "product", dataType = DataType.SERIALIZABLE)
    public Product product;

    @DatabaseField(columnName = "quantity")
    public Integer quantity;

    public ShoppingCartItem() {
        super();
    }

    public ShoppingCartItem(final Integer userId, final Product product) {
        this.userId = userId;
        this.productId = product.id;
        this.product = product;
        this.quantity = 1;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ShoppingCartItem that = (ShoppingCartItem) o;

        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId);
    }
}
