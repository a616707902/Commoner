package com.chenpan.commoner.mvp.presenter;

import android.content.Context;

import com.chenpan.commoner.R;
import com.chenpan.commoner.base.pbase.BasePresenter;
import com.chenpan.commoner.bean.SongListInfo;
import com.chenpan.commoner.mvp.modle.MusicModel;
import com.chenpan.commoner.mvp.modle.imp.IMusicOnlineModel;
import com.chenpan.commoner.mvp.view.IMusicOnlineView;
import com.chenpan.commoner.service.PlayService;

import java.util.List;

/**
 * Created by Administrator on 2016/7/6.
 */
public class MusicOnlinePresenter extends BasePresenter<IMusicOnlineView> {

    IMusicOnlineModel musicModel = new IMusicOnlineModel();

    public void getAdapterOnline(Context context, PlayService service) {
        if (!getWeakView().checkNet()) {
            getWeakView().showNoNet();
            return;
        }

        List<SongListInfo> mSongLists = null;
        if (service!=null)
            mSongLists=musicModel.getAdapterOnline(service);
        if (mSongLists.isEmpty()) {
            String[] titles = context.getResources().getStringArray(R.array.online_music_list_title);
            String[] types = context.getResources().getStringArray(R.array.online_music_list_type);
            for (int i = 0; i < titles.length; i++) {
                SongListInfo info = new SongListInfo();
                info.setTitle(titles[i]);
                info.setType(types[i]);
                mSongLists.add(info);
            }


        }
        if (mSongLists!=null){
            getWeakView().showSuccess();
            getWeakView().setAdapter(mSongLists);
        }else{
            getWeakView().showFaild();
        }
    }
}
