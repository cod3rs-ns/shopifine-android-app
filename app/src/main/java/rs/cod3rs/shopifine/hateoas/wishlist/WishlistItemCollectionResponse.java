package rs.cod3rs.shopifine.hateoas.wishlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import rs.cod3rs.shopifine.hateoas.CollectionLinks;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WishlistItemCollectionResponse {

    private List<WishlistItemResponseData> data;

    private CollectionLinks links;

    public WishlistItemCollectionResponse() {
        super();
    }

    public List<WishlistItemResponseData> getData() {
        return data;
    }

    public void setData(final List<WishlistItemResponseData> data) {
        this.data = data;
    }

    public CollectionLinks getLinks() {
        return links;
    }

    public void setLinks(final CollectionLinks links) {
        this.links = links;
    }
}
