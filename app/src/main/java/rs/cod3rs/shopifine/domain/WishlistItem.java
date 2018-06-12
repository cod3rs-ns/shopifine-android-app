package rs.cod3rs.shopifine.domain;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.Serializable;

import rs.cod3rs.shopifine.Util;

public class WishlistItem implements Serializable {

    public Long id;

    public LocalDateTime createdAt;

    public Long productId;

    public Product product;

    public WishlistItem(final Long id, final String createdAt, final Long productId) {
        this.id = id;
        this.createdAt =
                LocalDateTime.parse(createdAt, DateTimeFormat.forPattern(Util.DATE_PATTERN));
        this.productId = productId;
    }
}
