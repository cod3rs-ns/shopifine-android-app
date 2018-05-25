package rs.cod3rs.shopifine.http;

import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.RestClientErrorHandling;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import rs.cod3rs.shopifine.Config;
import rs.cod3rs.shopifine.hateoas.bills.BillCollectionResponse;

@Rest(
    rootUrl = Config.SERVICE_URL,
    converters = MappingJackson2HttpMessageConverter.class,
    interceptors = AuthInterceptor.class,
    responseErrorHandler = HttpErrorHandler.class
)
public interface Orders extends RestClientErrorHandling {

    @Get("api/users/{userId}/bills?filter[status]={orderStatus}")
    BillCollectionResponse getBills(@Path final Integer userId, @Path final String orderStatus);
}
