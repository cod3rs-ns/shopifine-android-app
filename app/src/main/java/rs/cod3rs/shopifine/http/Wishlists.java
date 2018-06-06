package rs.cod3rs.shopifine.http;

import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import rs.cod3rs.shopifine.Config;

@Rest(
        rootUrl = Config.SERVICE_URL,
        converters = MappingJackson2HttpMessageConverter.class,
        interceptors = AuthInterceptor.class,
        responseErrorHandler = HttpErrorHandler.class
)
public interface Wishlists {

}
