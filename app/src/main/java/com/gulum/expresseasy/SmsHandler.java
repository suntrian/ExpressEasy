package com.gulum.expresseasy;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;

import java.util.List;

/**
 * Created by Administrator on 2016/1/31.
 */
public class SmsHandler
{
    Context mContext;
    BroadcastReceiver sentReceiver;
    BroadcastReceiver deliveredReceiver;

    final String SENT = "sms_sent";
    final String DELIVERED = "sms_delivered";
    PendingIntent sentPI;
    PendingIntent deliveredPI;

    public SmsHandler(final Context context){
        this.mContext = context;
        sentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:

                    case SmsManager.RESULT_ERROR_NO_SERVICE:

                    case SmsManager.RESULT_ERROR_RADIO_OFF:

                    case SmsManager.RESULT_ERROR_NULL_PDU:

                    default:

                        break;
                }
            }
        };
        deliveredReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                    default:
                        break;
                }
            }
        };



        sentPI = PendingIntent.getActivity(mContext,0,new Intent(SENT),0);
        deliveredPI = PendingIntent.getActivity(mContext,0,new Intent(DELIVERED),0);

        mContext.registerReceiver(sentReceiver,new IntentFilter(SENT));
        mContext.registerReceiver(deliveredReceiver,new IntentFilter(DELIVERED));

    }
    public void unregisterReceiver(){
        mContext.unregisterReceiver(sentReceiver);
        mContext.unregisterReceiver(deliveredReceiver);
    }

    public void sendSms(String number, String message){


        SmsManager smsManager = SmsManager.getDefault();
        List<String> dividedMessage = smsManager.divideMessage(message);
        for (String msg: dividedMessage){
            smsManager.sendTextMessage(number,null,msg,sentPI,deliveredPI);
        }
    }
}
