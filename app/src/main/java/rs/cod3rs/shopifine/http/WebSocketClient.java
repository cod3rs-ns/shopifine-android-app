package rs.cod3rs.shopifine.http;


import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import rs.cod3rs.shopifine.Credentials_;
import rs.cod3rs.shopifine.notification.NotificationListener;

import static rs.cod3rs.shopifine.Config.SERVICE_URL;

@EBean
public class WebSocketClient {

    @Bean
    NotificationListener listener;

    @Pref
    Credentials_ credentials;

    private WebSocket webSocketClient;

    private final OkHttpClient httpClient;

    public WebSocketClient() {
        this.httpClient = new OkHttpClient();
    }

    public void start() {
        final Request request = new Request.Builder().header("Authorization", buildToken(credentials.token().get())).url(String.format("%s/ws", SERVICE_URL)).build();

        webSocketClient = httpClient.newWebSocket(request, listener);
        httpClient.dispatcher().executorService().shutdown();
    }

    public void close() {
        webSocketClient.cancel();
    }

    private String buildToken(final String token) {
        return String.format("%s %s", AUTH_TOKEN_TYPE, token);
    }

    private static final String AUTH_TOKEN_TYPE = "Bearer";
}
