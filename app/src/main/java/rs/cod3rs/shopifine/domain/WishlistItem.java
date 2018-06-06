package rs.cod3rs.shopifine.domain;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.Serializable;

import rs.cod3rs.shopifine.Util;

public class WishlistItem implements Serializable {

    private Long id;

    private LocalDateTime createdAt;

    private Long productId;

    private Product product;

    public WishlistItem(final Long id, final String createdAt, final Long productId) {
        this.id = id;
        this.createdAt =
                LocalDateTime.parse(createdAt, DateTimeFormat.forPattern(Util.DATE_PATTERN));
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(final Product product) {
        this.product = product;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(final Long productId) {
        this.productId = productId;
    }
}
