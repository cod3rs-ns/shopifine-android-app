package rs.cod3rs.shopifine.http;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import rs.cod3rs.shopifine.Credentials_;

@EBean
public class AuthInterceptor implements ClientHttpRequestInterceptor {

    @Pref
    Credentials_ credentials;

    @Override
    public ClientHttpResponse intercept(final HttpRequest request, final byte[] body, final ClientHttpRequestExecution execution) throws IOException {
        final String token = credentials.token().get();

        if (token != null) {
            final HttpHeaders headers = request.getHeaders();
            headers.set("Authorization", buildToken(token));
        }

        return execution.execute(request, body);
    }

    private String buildToken(final String token) {
        return String.format("%s %s", AUTH_TOKEN_TYPE, token);
    }

    private static final String AUTH_TOKEN_TYPE = "Bearer";
}
