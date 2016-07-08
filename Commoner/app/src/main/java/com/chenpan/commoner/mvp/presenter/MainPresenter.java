package com.chenpan.commoner.mvp.presenter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.IBinder;

import com.chenpan.commoner.base.pbase.BasePresenter;
import com.chenpan.commoner.bean.Music;
import com.chenpan.commoner.bean.User;
import com.chenpan.commoner.mvp.modle.MainMode;
import com.chenpan.commoner.mvp.modle.imp.IMainMode;
import com.chenpan.commoner.mvp.view.MainView;
import com.chenpan.commoner.receiver.RemoteControlReceiver;
import com.chenpan.commoner.service.OnPlayerEventListener;
import com.chenpan.commoner.service.PlayService;

import java.util.Map;

/**
 * Created by Administrator on 2016/6/1.
 */
public class MainPresenter extends BasePresenter<MainView> {
    MainMode mainMode = new IMainMode();
    private Context context;
    /**
     * 音乐播放后台服务
     */
    private PlayService mPlayService;

    /**
     * 音频管理器
     */
    private AudioManager mAudioManager;
    /**
     * 打开服wu
     */
    private ComponentName mRemoteReceiver;

    public MainPresenter(Context context) {
        this.context = context;
    }


    public void setPlayService(PlayService mPlayService) {
        this.mPlayService = mPlayService;
    }

    public void setAudioManger() {

        registerReceiver();
    }

    private void registerReceiver() {
        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mRemoteReceiver = new ComponentName(context.getPackageName(), RemoteControlReceiver.class.getName());
        mAudioManager.registerMediaButtonEventReceiver(mRemoteReceiver);
    }


    public void play() {
        mPlayService.playPause();
    }

    public void next() {
        mPlayService.next();
    }
}
