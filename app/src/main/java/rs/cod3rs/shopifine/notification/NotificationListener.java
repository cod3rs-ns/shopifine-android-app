package rs.cod3rs.shopifine.notification;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class NotificationListener extends WebSocketListener {

    private static final int NORMAL_CLOSURE_STATUS = 1000;

    private final AtomicInteger idGenerator = new AtomicInteger(0);

    private final Context context;

    private final NotificationBuilder notificationBuilder;

    public NotificationListener(final Context context) {
        this.context = context;
        this.notificationBuilder = new NotificationBuilder();
    }

    @Override
    public void onMessage(final WebSocket webSocket, final String text) {
        final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this.context);

        final Notification notification = notificationBuilder.buildNotification(text, context);
        if (notification != null) {
            notificationManager.notify(getID(), notification);
        }
    }

    @Override
    public void onClosing(final WebSocket webSocket, final int code, final String reason) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null);

        Log.e(NotificationListener.class.getSimpleName(), String.format("Closing socket : %s / %s", code, reason));
    }

    @Override
    public void onFailure(final WebSocket webSocket, final Throwable t, final Response response) {
        Log.e(NotificationListener.class.getSimpleName(), t.getMessage());
    }

    private int getID() {
        return idGenerator.incrementAndGet();
    }
}
