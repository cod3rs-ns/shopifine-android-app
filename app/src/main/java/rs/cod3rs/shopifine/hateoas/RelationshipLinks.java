package rs.cod3rs.shopifine.hateoas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RelationshipLinks {

    private String realted;

    public RelationshipLinks() {
        super();
    }

    public String getRealted() {
        return realted;
    }

    public void setRealted(String realted) {
        this.realted = realted;
    }
}
