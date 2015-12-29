package call_voice_changer.gizmogadgetsappz.com.callvoicechanger;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Created by johndoe on 29.12.2015.
 */
public class PhoneService extends Service {
    private final String TAG = "PhoneService";
    private final String STOP_ACTION = "com.gga.PhoneServiceStop";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //we register the receiver
        IntentFilter filter = new IntentFilter();
        filter.addAction(STOP_ACTION);

        registerReceiver(usb_broadcast_receiver, filter);

        //show the notification
        final int ONGOING_NOTIFICATION_ID = 1001;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(STOP_ACTION), 0);

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_notification);
        contentView.setTextViewText(R.id.title, "Call Voice Changer Notification");

        Notification notification = new Notification();
        notification.contentIntent = pendingIntent;
        notification.contentView = contentView;
        notification.tickerText = "";
        notification.icon = R.mipmap.ic_launcher;

        startForeground(ONGOING_NOTIFICATION_ID, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //process the audio



        return START_NOT_STICKY;
    }


    private final BroadcastReceiver usb_broadcast_receiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if((null == intent) || intent.getAction() == null)
            {
                return;
            }

            String action = intent.getAction();

            if(STOP_ACTION.equalsIgnoreCase(action))
            {
                //stop the service and remove the notification

                Log.e(TAG, "stopForeground");
                stopForeground(true);
            }
        }
    };
}
