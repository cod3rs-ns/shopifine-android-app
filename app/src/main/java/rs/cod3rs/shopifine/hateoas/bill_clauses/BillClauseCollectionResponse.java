package rs.cod3rs.shopifine.hateoas.bill_clauses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import rs.cod3rs.shopifine.hateoas.CollectionLinks;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillClauseCollectionResponse {

    private List<BillClauseResponseData> data;

    private CollectionLinks links;

    public BillClauseCollectionResponse() {
        super();
    }

    public List<BillClauseResponseData> getData() {
        return data;
    }

    public void setData(final List<BillClauseResponseData> data) {
        this.data = data;
    }

    public CollectionLinks getLinks() {
        return links;
    }

    public void setLinks(final CollectionLinks links) {
        this.links = links;
    }
}
