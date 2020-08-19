package mx.zublime.prediciclo.ui.notificaciones;

import android.app.Notification;
import android.app.NotificationChannel;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;

import java.util.Map;

import mx.zublime.prediciclo.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    public static final String NOTIFICATION_CHANNEL_ID = "10001";

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        displayNotification(remoteMessage.getData());
    }

    void guardarRealm(RemoteMessage remoteMessage) throws JSONException {

    }

    private void sendNewPromoBroadcast() {

    }

    private void displayNotification(Map<String, String> data) {
        NotificationHelper NH = new NotificationHelper(this);
        NH.createNotification("titulo", "descripcion");

    }

}
