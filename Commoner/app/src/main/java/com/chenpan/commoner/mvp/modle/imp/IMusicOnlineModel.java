package com.chenpan.commoner.mvp.modle.imp;

import com.chenpan.commoner.bean.SongListInfo;
import com.chenpan.commoner.mvp.modle.MusicModel;
import com.chenpan.commoner.service.PlayService;

import java.util.List;

/**
 * Created by Administrator on 2016/7/6.
 */
public class IMusicOnlineModel implements MusicModel {
    public List<SongListInfo> getAdapterOnline(PlayService service){
        List<SongListInfo> songListInfos=service.mSongLists;
        return songListInfos;
    }
}
