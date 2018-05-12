package rs.cod3rs.shopifine.hateoas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseRelationship {

    private RelationshipLinks links;
    private RelationshipData data;

    public ResponseRelationship() {
        super();
    }

    public RelationshipLinks getLinks() {
        return links;
    }

    public void setLinks(final RelationshipLinks links) {
        this.links = links;
    }

    public RelationshipData getData() {
        return data;
    }

    public void setData(final RelationshipData data) {
        this.data = data;
    }
}
