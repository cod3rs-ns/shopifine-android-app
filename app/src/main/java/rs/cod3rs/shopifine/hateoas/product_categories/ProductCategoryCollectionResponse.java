package rs.cod3rs.shopifine.hateoas.product_categories;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import rs.cod3rs.shopifine.hateoas.CollectionLinks;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductCategoryCollectionResponse {

    private List<ProductCategoryResponseData> data;

    private CollectionLinks links;

    public ProductCategoryCollectionResponse() {
        super();
    }

    public List<ProductCategoryResponseData> getData() {
        return data;
    }

    public void setData(final List<ProductCategoryResponseData> data) {
        this.data = data;
    }

    public CollectionLinks getLinks() {
        return links;
    }

    public void setLinks(final CollectionLinks links) {
        this.links = links;
    }
}
