package rs.cod3rs.shopifine.hateoas.bills;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import static rs.cod3rs.shopifine.hateoas.DataTypes.BILLS_TYPE;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillRequestData {

    private String type = BILLS_TYPE;

    private BillRequestAttributes attributes;

    private BillRequestRelationships relationships;

    public BillRequestData(final BillRequestAttributes attributes, final BillRequestRelationships relationships) {
        this.attributes = attributes;
        this.relationships = relationships;
    }

    public BillRequestData() {
        super();
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public BillRequestAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(final BillRequestAttributes attributes) {
        this.attributes = attributes;
    }

    public BillRequestRelationships getRelationships() {
        return relationships;
    }

    public void setRelationships(final BillRequestRelationships relationships) {
        this.relationships = relationships;
    }
}
