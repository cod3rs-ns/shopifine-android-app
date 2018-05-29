package rs.cod3rs.shopifine.hateoas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestRelationship {

    private RelationshipData data;

    public RequestRelationship() {
        super();
    }

    public RequestRelationship(final RelationshipData data) {
        this.data = data;
    }

    public RelationshipData getData() {
        return data;
    }

    public void setData(final RelationshipData data) {
        this.data = data;
    }
}
