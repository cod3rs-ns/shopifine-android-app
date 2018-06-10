package rs.cod3rs.shopifine.hateoas.bill_items;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import static rs.cod3rs.shopifine.hateoas.DataTypes.BILL_ITEMS_TYPE;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillItemRequestData {

    private String type = BILL_ITEMS_TYPE;

    private BillItemRequestAttributes attributes;

    private BillItemRequestRelationships relationships;

    public BillItemRequestData(final BillItemRequestAttributes attributes, final BillItemRequestRelationships relationships) {
        this.attributes = attributes;
        this.relationships = relationships;
    }

    public BillItemRequestData() {
        super();
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public BillItemRequestAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(final BillItemRequestAttributes attributes) {
        this.attributes = attributes;
    }

    public BillItemRequestRelationships getRelationships() {
        return relationships;
    }

    public void setRelationships(final BillItemRequestRelationships relationships) {
        this.relationships = relationships;
    }
}
