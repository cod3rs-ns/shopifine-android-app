package rs.cod3rs.shopifine.hateoas.bill_clauses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import rs.cod3rs.shopifine.domain.OrderClause;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillClauseResponseData {

    private String type;
    private Long id;
    private BillClauseResponseAttributes attributes;
    private BillClauseResponseRelationships relationships;

    public BillClauseResponseData() {
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

    public BillClauseResponseRelationships getRelationships() {
        return relationships;
    }

    public void setRelationships(final BillClauseResponseRelationships relationships) {
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

    public BillClauseResponseAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(final BillClauseResponseAttributes attributes) {
        this.attributes = attributes;
    }
}
