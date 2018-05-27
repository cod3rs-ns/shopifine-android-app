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

    @Get("api/products")
    ProductCollectionResponse retrieveAll();

    @Get("api/products/{productId}")
    ProductResponse retrieveOne(@Path Long productId);
}
