package call_voice_changer.gizmogadgetsappz.com.callvoicechanger;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by johndoe on 29.12.2015.
 */
public class PhoneReceiver extends BroadcastReceiver {
    private final String TAG = "PhoneReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        if(Intent.ACTION_NEW_OUTGOING_CALL.equals(intent.getAction())){
            String phonenumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Log.e(TAG, "NEW_OUTGOING_CALL: " + phonenumber);

            Intent serviceIntent = new Intent(context, PhoneService.class);
            context.startService(serviceIntent);
        }
        else {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);

            switch (tm.getCallState()) {
                case TelephonyManager.CALL_STATE_RINGING: {
                    String incoming_number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

                    Log.e(TAG, "RINGING: " + incoming_number);

                    Intent serviceIntent = new Intent(context, PhoneService.class);
                    context.startService(serviceIntent);

                    break;
                }
                case TelephonyManager.CALL_STATE_OFFHOOK: {

                    break;
                }
                case TelephonyManager.CALL_STATE_IDLE: {
                    break;
                }
            }
        }
    }
}
