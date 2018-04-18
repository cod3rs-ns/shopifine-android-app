package rs.cod3rs.shopifine.hateoas;

import java.util.List;

public class ProductCollectionResponse {

    private final List<ProductResponseData> data;

    private final CollectionLinks links;

    public ProductCollectionResponse(final List<ProductResponseData> data, final CollectionLinks links) {
        this.data = data;
        this.links = links;
    }

    public List<ProductResponseData> getData() {
        return data;
    }

    public CollectionLinks getLinks() {
        return links;
    }
}
