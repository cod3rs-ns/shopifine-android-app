package rs.cod3rs.shopifine.hateoas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseRelationshipCollection {

    private RelationshipLinks links;

    public ResponseRelationshipCollection(final RelationshipLinks links) {
        this.links = links;
    }

    public ResponseRelationshipCollection() {
        super();
    }

    public RelationshipLinks getLinks() {
        return links;
    }

    public void setLinks(final RelationshipLinks links) {
        this.links = links;
    }
}
