package rs.cod3rs.shopifine.http;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Delete;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import rs.cod3rs.shopifine.Config;
import rs.cod3rs.shopifine.hateoas.discounts.DiscountCollectionResponse;
import rs.cod3rs.shopifine.hateoas.wishlist.WishlistItemCollectionResponse;
import rs.cod3rs.shopifine.hateoas.wishlist.WishlistItemRequest;
import rs.cod3rs.shopifine.hateoas.wishlist.WishlistItemResponse;

@Rest(
    rootUrl = Config.SERVICE_URL,
    converters = MappingJackson2HttpMessageConverter.class,
    interceptors = AuthInterceptor.class,
    responseErrorHandler = HttpErrorHandler.class
)
public interface Wishlists {

    @Get("api/users/{userId}/wishlist")
    WishlistItemCollectionResponse getWishlist(@Path final Integer userId);

    @Post("api/users/{userId}/wishlist")
    WishlistItemResponse addItem(
            @Path final Integer userId, @Body final WishlistItemRequest body);

    @Delete("api/users/{userId}/wishlist/{itemId}")
    DiscountCollectionResponse removeItem(@Path final Integer userId, @Path final Long itemId);
}
