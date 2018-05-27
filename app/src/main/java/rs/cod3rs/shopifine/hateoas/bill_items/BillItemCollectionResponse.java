package rs.cod3rs.shopifine.hateoas.bill_items;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import rs.cod3rs.shopifine.hateoas.CollectionLinks;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillItemCollectionResponse {

    private List<BillItemResponseData> data;

    private CollectionLinks links;

    public BillItemCollectionResponse() {
        super();
    }

    public List<BillItemResponseData> getData() {
        return data;
    }

    public void setData(final List<BillItemResponseData> data) {
        this.data = data;
    }

    public CollectionLinks getLinks() {
        return links;
    }

    public void setLinks(final CollectionLinks links) {
        this.links = links;
    }
}
