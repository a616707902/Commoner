package com.chenpan.commoner.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.chenpan.commoner.playmusic.MusicPlayMode;
import com.chenpan.commoner.service.PlayService;


/**
 * 来电/耳机拔出时暂停播放
 * Created by wcy on 2016/1/23.
 */
public class NoisyAudioStreamReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, PlayService.class);
        serviceIntent.setAction(MusicPlayMode.ACTION_MEDIA_PLAY_PAUSE);
        context.startService(serviceIntent);
    }
}
