package rs.cod3rs.shopifine.http;

import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.RestClientErrorHandling;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import rs.cod3rs.shopifine.Config;
import rs.cod3rs.shopifine.hateoas.action_discounts.ActionDiscountCollectionResponse;
import rs.cod3rs.shopifine.hateoas.buyer_categories.BuyerCategoryResponse;

@Rest(
        rootUrl = Config.SERVICE_URL,
        converters = MappingJackson2HttpMessageConverter.class,
        interceptors = AuthInterceptor.class,
        responseErrorHandler = HttpErrorHandler.class
)
public interface ActionDiscounts extends RestClientErrorHandling {

    @Get("api/product-categories/{id}/action-discounts?filter[date]={date}")
    ActionDiscountCollectionResponse getDiscountsForProductCategory(@Path final Long id, @Path final String date);

}
