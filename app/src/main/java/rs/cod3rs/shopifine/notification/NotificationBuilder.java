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

import org.androidannotations.annotations.EBean;

import java.io.IOException;

import rs.cod3rs.shopifine.R;
import rs.cod3rs.shopifine.activity.MainActivity_;
import rs.cod3rs.shopifine.notification.messages.ActionDiscountCreated;
import rs.cod3rs.shopifine.notification.messages.OneProductLeft;
import rs.cod3rs.shopifine.notification.messages.OrderAddressChanged;
import rs.cod3rs.shopifine.notification.messages.OrderStateChanged;
import rs.cod3rs.shopifine.notification.messages.ProductPriceChanged;

@EBean
public class NotificationBuilder {

    private static final String TYPE_FIELD = "type";
    private static final String NOTIFICATION_NAME = "Shopifine";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Context context;

    public NotificationBuilder(final Context context) {
        this.context = context;
    }

    public Notification buildNotification(final String message) {
        try {
            final JsonNode notification = objectMapper.readValue(message, JsonNode.class);
            final NotificationType type = NotificationType.fromString(notification.get(TYPE_FIELD).asText());

            return parseNotificationContent(message, type);
        } catch (final IOException e) {
            Log.e(NotificationBuilder.class.getSimpleName(), e.getMessage());
        }
        return null;
    }

    private Notification parseNotificationContent(final String message, final NotificationType notificationType) {
        try {
            switch (notificationType) {
                case ORDER_STATUS_CHANGED:
                    final OrderStateChanged stateChanged = objectMapper.readValue(message, OrderStateChanged.class);
                    return buildOrderStateChangedNotification(stateChanged);
                case ACTION_DISCOUNT_CREATED:
                    final ActionDiscountCreated actionDiscountCreated = objectMapper.readValue(message, ActionDiscountCreated.class);
                    return buildActionDiscountCreated(actionDiscountCreated);
                case ONE_PRODUCT_LEFT:
                    final OneProductLeft oneProductLeft = objectMapper.readValue(message, OneProductLeft.class);
                    return buildOneProductLeft(oneProductLeft);
                case ORDER_ADDRESS_CHANGED:
                    final OrderAddressChanged orderAddressChanged = objectMapper.readValue(message, OrderAddressChanged.class);
                    return buildOrderAddressChanged(orderAddressChanged);
                case PRODUCT_PRICE_CHANGED:
                    final ProductPriceChanged productPriceChanged = objectMapper.readValue(message, ProductPriceChanged.class);
                    return buildProductPriceChanged(productPriceChanged);
                default:
                    return null;
            }
        } catch (final Exception ex) {
            return null;
        }
    }

    private Notification buildOrderStateChangedNotification(final OrderStateChanged stateChanged) {
        // TODO: change to appropriate intent
        final Intent intent = MainActivity_.intent(context).get();
        final String message = context.getResources().getString(R.string.order_state_changed, stateChanged.getState());

        return buildBasicNotificationView(intent, message);
    }

    private Notification buildActionDiscountCreated(final ActionDiscountCreated actionDiscountCreated) {
        // TODO: change to appropriate intent
        final Intent intent = MainActivity_.intent(context).get();
        final String message = context.getResources().getString(R.string.action_discount_created, actionDiscountCreated.getName(), actionDiscountCreated.getDiscount());

        return buildBasicNotificationView(intent, message);
    }

    private Notification buildOneProductLeft(final OneProductLeft oneProductLeft) {
        // TODO: change to appropriate intent
        final Intent intent = MainActivity_.intent(context).get();
        final String message = context.getResources().getString(R.string.one_product_left, oneProductLeft.getName());

        return buildBasicNotificationView(intent, message);
    }

    private Notification buildOrderAddressChanged(final OrderAddressChanged orderAddressChanged) {
        // TODO: change to appropriate intent
        final Intent intent = MainActivity_.intent(context).get();
        final String message = context.getResources().getString(R.string.order_address_changed, orderAddressChanged.getOrderId(), orderAddressChanged.getAddress());

        return buildBasicNotificationView(intent, message);
    }

    private Notification buildProductPriceChanged(final ProductPriceChanged productPriceChanged) {
        // TODO: change to appropriate intent
        final Intent intent = MainActivity_.intent(context).get();
        final String message = context.getResources().getString(R.string.product_price_changed, productPriceChanged.getName(), productPriceChanged.getPrice());

        return buildBasicNotificationView(intent, message);
    }

    private Notification buildBasicNotificationView(final Intent intent, final String message) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        final PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        return new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_shopifine_bag)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setContentTitle(NOTIFICATION_NAME)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).setAutoCancel(true)
                .setContentIntent(pendingIntent).build();

    }
}
