package rs.cod3rs.shopifine.notification;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

@EBean
public class NotificationListener extends WebSocketListener {

    private static final int NORMAL_CLOSURE_STATUS = 1000;
    private final AtomicInteger idGenerator = new AtomicInteger(0);

    @Bean
    NotificationBuilder notificationBuilder;

    private final Context context;

    public NotificationListener(final Context context) {
        this.context = context;
    }

    @Override
    public void onMessage(final WebSocket webSocket, final String text) {
        final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this.context);

        final Notification notification = notificationBuilder.buildNotification(text);
        if (Objects.nonNull(notification)) {
            notificationManager.notify(getID(), notification);
        }
    }

    @Override
    public void onClosing(final WebSocket webSocket, final int code, final String reason) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
        Log.e(this.getClass().getSimpleName(), String.format("Closing socket : %s / %s", code, reason));
    }

    @Override
    public void onFailure(final WebSocket webSocket, final Throwable t, final Response response) {
        Log.e(this.getClass().getSimpleName(), "Connection unexpectedly closed.");
    }

    private int getID() {
        return idGenerator.incrementAndGet();
    }
}
