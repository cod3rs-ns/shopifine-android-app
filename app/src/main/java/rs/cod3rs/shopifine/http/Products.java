package rs.cod3rs.shopifine.http;

import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import rs.cod3rs.shopifine.Config;
import rs.cod3rs.shopifine.hateoas.products.ProductCollectionResponse;
import rs.cod3rs.shopifine.hateoas.products.ProductResponse;

@Rest(
    rootUrl = Config.SERVICE_URL,
    converters = MappingJackson2HttpMessageConverter.class,
    interceptors = AuthInterceptor.class,
    responseErrorHandler = HttpErrorHandler.class
)
public interface Products {

    @Get("api/products?filter[active]=ACTIVE&page[offset]={offset}&page[limit]={limit}")
    ProductCollectionResponse retrieveAll(@Path final Integer offset, @Path final Integer limit);

    @Get("api/products/{productId}")
    ProductResponse retrieveOne(@Path final Long productId);

    @Get("api/products?filter[category]={categoryId}")
    ProductCollectionResponse retrieveFromCategory(@Path final Long categoryId);

}
