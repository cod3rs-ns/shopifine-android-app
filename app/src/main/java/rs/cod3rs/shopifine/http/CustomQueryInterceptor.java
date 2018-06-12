package rs.cod3rs.shopifine.http;

import android.util.Log;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class CustomQueryInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(final HttpRequest request, final byte[] body, final ClientHttpRequestExecution execution) throws IOException {
        return execution.execute(new QueryMultiParamsHttpRequest(request), body);
    }

    private class QueryMultiParamsHttpRequest implements HttpRequest {
        private HttpRequest request;

        QueryMultiParamsHttpRequest(final HttpRequest request) {
            this.request = request;
        }

        @Override
        public HttpMethod getMethod() {
            return request.getMethod();
        }

        @Override
        public URI getURI() {
            final URI url = request.getURI();
            final String query = url.getQuery() != null ? url.getQuery().replace("%3D", "=").replace("%26", "&") : null;

            try {
                return new URI(url.getScheme(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), query, url.getFragment());
            } catch (final URISyntaxException e) {
                Log.e(getClass().getSimpleName(), e.getMessage());
                return null;
            }
        }

        @Override
        public HttpHeaders getHeaders() {
            return request.getHeaders();
        }
    }
}
