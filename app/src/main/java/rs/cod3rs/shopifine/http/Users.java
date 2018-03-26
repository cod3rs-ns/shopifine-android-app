package rs.cod3rs.shopifine.http;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.RestClientErrorHandling;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import rs.cod3rs.shopifine.Config;
import rs.cod3rs.shopifine.hateoas.UserAuthRequest;
import rs.cod3rs.shopifine.hateoas.UserAuthResponse;

@Rest(
        rootUrl = Config.SERVICE_URL,
        converters = MappingJackson2HttpMessageConverter.class,
        interceptors = AuthInterceptor.class,
        responseErrorHandler = ErrorHandler.class
)
public interface Users extends RestClientErrorHandling {

    @Post("api/users/auth")
    UserAuthResponse auth(@Body UserAuthRequest req);

}
