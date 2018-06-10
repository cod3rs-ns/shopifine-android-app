package rs.cod3rs.shopifine.hateoas.bill_items;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import rs.cod3rs.shopifine.db.ShoppingCartItem;
import rs.cod3rs.shopifine.hateoas.RelationshipData;
import rs.cod3rs.shopifine.hateoas.RequestRelationship;

import static rs.cod3rs.shopifine.hateoas.DataTypes.BILLS_TYPE;
import static rs.cod3rs.shopifine.hateoas.DataTypes.PRODUCTS_TYPE;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillItemRequest {

    private BillItemRequestData data;

    public BillItemRequest(final BillItemRequestData data) {
        this.data = data;
    }

    public BillItemRequest() {
        super();
    }

    public BillItemRequest(final Long billId, final Integer ordinal, final ShoppingCartItem item) {
        final BillItemRequestAttributes attributes = new BillItemRequestAttributes(item.product.price, item.quantity, item.discountAmount, ordinal);
        final BillItemRequestRelationships relationships = new BillItemRequestRelationships(
                new RequestRelationship(new RelationshipData(PRODUCTS_TYPE, item.product.id)),
                new RequestRelationship(new RelationshipData(BILLS_TYPE, billId)));

        this.data = new BillItemRequestData(attributes, relationships);
    }

    public BillItemRequestData getData() {
        return data;
    }

    public void setData(final BillItemRequestData data) {
        this.data = data;
    }
}
