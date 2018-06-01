package rs.cod3rs.shopifine.hateoas.bills;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import rs.cod3rs.shopifine.hateoas.CollectionLinks;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillCollectionResponse {

    private List<BillResponseData> data;

    private CollectionLinks links;

    public BillCollectionResponse() {
        super();
    }

    public List<BillResponseData> getData() {
        return data;
    }

    public void setData(final List<BillResponseData> data) {
        this.data = data;
    }

    public CollectionLinks getLinks() {
        return links;
    }

    public void setLinks(final CollectionLinks links) {
        this.links = links;
    }
}
