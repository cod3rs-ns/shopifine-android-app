package rs.cod3rs.shopifine.http;


import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import rs.cod3rs.shopifine.notification.NotificationListener;

import static rs.cod3rs.shopifine.Config.SERVICE_URL;

public class WebSocketClient {

    private final WebSocket webSocketClient;

    public WebSocketClient(final String token, final Context context) {
        final OkHttpClient httpClient = new OkHttpClient();

        final Request request = new Request.Builder().header("Authorization", buildToken(token)).url(String.format("%s/ws", SERVICE_URL)).build();
        final NotificationListener listener = new NotificationListener(context);

        webSocketClient = httpClient.newWebSocket(request, listener);
        httpClient.dispatcher().executorService().shutdown();
    }

    private String buildToken(final String token) {
        return String.format("%s %s", AUTH_TOKEN_TYPE, token);
    }

    private static final String AUTH_TOKEN_TYPE = "Bearer";

    public void close() {
        webSocketClient.cancel();
    }
}
