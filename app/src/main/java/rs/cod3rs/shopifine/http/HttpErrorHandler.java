package rs.cod3rs.shopifine.http;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import rs.cod3rs.shopifine.hateoas.error.Error;
import rs.cod3rs.shopifine.hateoas.error.ErrorResponse;

public class HttpErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public boolean hasError(final ClientHttpResponse response) throws IOException {
        return super.hasError(response);
    }

    @Override
    public void handleError(final ClientHttpResponse response) throws IOException {
        final ErrorResponse res = fromJson(response.getBody());
        throw new RestClientException(extractErrorMessage(res));
    }

    private ErrorResponse fromJson(final InputStream body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(body, ErrorResponse.class);
    }

    private String extractErrorMessage(final ErrorResponse response) {
        final List<Error> errors = response.getErrors();
        if (errors.size() > 0) {
            return errors.get(0).getDetail();
        }

        return DEFAULT_ERROR_MESSAGE;
    }

    private static final String DEFAULT_ERROR_MESSAGE = "Something went wrong.";
}
