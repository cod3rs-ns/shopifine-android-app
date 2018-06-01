package rs.cod3rs.shopifine.hateoas.discounts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import rs.cod3rs.shopifine.hateoas.CollectionLinks;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscountCollectionResponse {
    private List<DiscountResponseData> data;

    private CollectionLinks links;

    public DiscountCollectionResponse() {
        super();
    }

    public List<DiscountResponseData> getData() {
        return data;
    }

    public void setData(final List<DiscountResponseData> data) {
        this.data = data;
    }

    public CollectionLinks getLinks() {
        return links;
    }

    public void setLinks(final CollectionLinks links) {
        this.links = links;
    }
}
