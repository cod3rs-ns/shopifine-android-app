package rs.cod3rs.shopifine.hateoas.bill_items;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import rs.cod3rs.shopifine.domain.OrderClause;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillItemResponseData {

    private String type;
    private Long id;
    private BillItemResponseAttributes attributes;
    private BillItemResponseRelationships relationships;

    public BillItemResponseData() {
        super();
    }

    public OrderClause toDomain() {
        return new OrderClause(
                id,
                attributes.getOrdinal(),
                attributes.getQuantity(),
                attributes.getPrice(),
                attributes.getAmount(),
                attributes.getDiscount(),
                attributes.getDiscountAmount(),
                relationships.getProduct().getData().getId());
    }

    public BillItemResponseRelationships getRelationships() {
        return relationships;
    }

    public void setRelationships(final BillItemResponseRelationships relationships) {
        this.relationships = relationships;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public BillItemResponseAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(final BillItemResponseAttributes attributes) {
        this.attributes = attributes;
    }
}
