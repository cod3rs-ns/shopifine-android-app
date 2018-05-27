package rs.cod3rs.shopifine.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.activity.MainActivity_;
import rs.cod3rs.shopifine.notification.messages.ActionDiscountCreated;
import rs.cod3rs.shopifine.notification.messages.OneProductLeft;
import rs.cod3rs.shopifine.notification.messages.OrderAddressChanged;
import rs.cod3rs.shopifine.notification.messages.OrderStateChanged;
import rs.cod3rs.shopifine.notification.messages.ProductPriceChanged;

public class NotificationBuilder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Notification buildNotification(final String message, final Context context) {
        try {
            final JsonNode notification = objectMapper.readValue(message, JsonNode.class);
            final NotificationType type = NotificationType.fromString(notification.get("type").asText());

            return parseNotificationContent(message, type, context);
        } catch (final IOException e) {
            Log.e(NotificationBuilder.class.getSimpleName(), e.getMessage());
        }
        return null;
    }

    private Notification parseNotificationContent(final String message, final NotificationType notificationType, final Context context) {
        try {
            switch (notificationType) {
                case ORDER_STATUS_CHANGED:
                    final OrderStateChanged stateChanged = objectMapper.readValue(message, OrderStateChanged.class);
                    return buildOrderStateChangedNotification(stateChanged, context);
                case ACTION_DISCOUNT_CREATED:
                    final ActionDiscountCreated actionDiscountCreated = objectMapper.readValue(message, ActionDiscountCreated.class);
                    return buildActionDiscountCreated(actionDiscountCreated, context);
                case ONE_PRODUCT_LEFT:
                    final OneProductLeft oneProductLeft = objectMapper.readValue(message, OneProductLeft.class);
                    return buildOneProductLeft(oneProductLeft, context);
                case ORDER_ADDRESS_CHANGED:
                    final OrderAddressChanged orderAddressChanged = objectMapper.readValue(message, OrderAddressChanged.class);
                    return buildOrderAddressChanged(orderAddressChanged, context);
                case PRODUCT_PRICE_CHANGED:
                    final ProductPriceChanged productPriceChanged = objectMapper.readValue(message, ProductPriceChanged.class);
                    return buildProductPriceChanged(productPriceChanged, context);
                default:
                    return null;
            }
        } catch (final Exception ex) {
            return null;
        }
    }

    private Notification buildOrderStateChangedNotification(final OrderStateChanged stateChanged, final Context context) {
        // TODO: change to appropriate intent
        final Intent intent = MainActivity_.intent(context).get();
        final String message = String.format("Order changed status to %s.", stateChanged.getState());

        return buildBasicNotificationView(context, intent, message);
    }

    private Notification buildActionDiscountCreated(final ActionDiscountCreated actionDiscountCreated, final Context context) {
        // TODO: change to appropriate intent
        final Intent intent = MainActivity_.intent(context).get();
        final String message = String.format("%s discount of %.0f%% is here.", actionDiscountCreated.getName(), actionDiscountCreated.getDiscount());

        return buildBasicNotificationView(context, intent, message);
    }

    private Notification buildOneProductLeft(final OneProductLeft oneProductLeft, final Context context) {
        // TODO: change to appropriate intent
        final Intent intent = MainActivity_.intent(context).get();
        final String message = String.format("Only one product %s left.", oneProductLeft.getName());

        return buildBasicNotificationView(context, intent, message);
    }

    private Notification buildOrderAddressChanged(final OrderAddressChanged orderAddressChanged, final Context context) {
        // TODO: change to appropriate intent
        final Intent intent = MainActivity_.intent(context).get();
        final String message = String.format("Order with id %s changed address to %s.", orderAddressChanged.getOrderId(), orderAddressChanged.getAddress());

        return buildBasicNotificationView(context, intent, message);
    }

    private Notification buildProductPriceChanged(final ProductPriceChanged productPriceChanged, final Context context) {
        // TODO: change to appropriate intent
        final Intent intent = MainActivity_.intent(context).get();
        final String message = String.format("Product %s changed it price to %s.", productPriceChanged.getName(), productPriceChanged.getPrice());

        return buildBasicNotificationView(context, intent, message);
    }

    private Notification buildBasicNotificationView(final Context context, final Intent intent, final String message) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        final PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        return new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_shopifine_bag)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setContentTitle("Shopifine")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).setAutoCancel(true)
                .setContentIntent(pendingIntent).build();

    }
}
