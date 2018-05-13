package rs.cod3rs.shopifine.http;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.RestClientErrorHandling;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import rs.cod3rs.shopifine.Config;
import rs.cod3rs.shopifine.hateoas.users.GoogleAuthRequest;
import rs.cod3rs.shopifine.hateoas.users.UserAuthRequest;
import rs.cod3rs.shopifine.hateoas.users.UserAuthResponse;
import rs.cod3rs.shopifine.hateoas.users.UserRequest;
import rs.cod3rs.shopifine.hateoas.users.UserResponse;

@Rest(
        rootUrl = Config.SERVICE_URL,
        converters = MappingJackson2HttpMessageConverter.class,
        interceptors = AuthInterceptor.class,
        responseErrorHandler = HttpErrorHandler.class
)
public interface Users extends RestClientErrorHandling {

    @Post("api/users/auth")
    UserAuthResponse auth(@Body final UserAuthRequest req);

    @Post("api/auth/google")
    UserAuthResponse googleAuth(@Body final GoogleAuthRequest req);

    @Get("api/users/{id}")
    UserResponse getUser(@Path final Integer id);

    @Post("api/users")
    UserResponse register(@Body final UserRequest req);
}
