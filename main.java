package com.example.hp.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by hp on 3/30/2016.
 */
public class SmsReceiver extends BroadcastReceiver {

    private static MediaPlayer siren;
    public double lat;
    public double lang;
    public String gps_msg;
    //LocationManager locationManager;
    //String mprovider;

    public SmsReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            String senderNumber = null;
            String num = "+92333-----15";
            String msg = "Ring";
            String msg1 = "Gps";
            String message = null;


            for (int i = 0; i < pdus.length; i++) {
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdus[i]);
                senderNumber = sms.getOriginatingAddress();

                message = sms.getDisplayMessageBody();
                Toast.makeText(context, "From" + senderNumber + "Message: " + message , Toast.LENGTH_LONG).show();


            }

            if (senderNumber.equals(num)) {
                if (message.equals(msg)) {

                    siren = MediaPlayer.create(context, R.raw.siren);

                    AudioManager audioManager = (AudioManager)

                            context.getSystemService(Context.AUDIO_SERVICE);


                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,

                            audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),

                            AudioManager.FLAG_SHOW_UI

                                    | AudioManager.FLAG_PLAY_SOUND
                    );

                    SmsManager smsManger = SmsManager.getDefault();
                    smsManger.sendTextMessage(senderNumber, null, "Your Phone's profile is change to General and ring is started. Go! Find Your Device ", null, null);
                    siren.start();

                    //smsManger.sendTextMessage(senderNumber, null, "Your Phone's profile is change to General and ring is started. Go find Your Device", null, null);


                }

                else if(message.equals(msg1)) {

                        SmsManager smsManger = SmsManager.getDefault();
                    smsManger.sendTextMessage(senderNumber, null, "GPS", null, null);

                    }

                }


            }

        }
    }

