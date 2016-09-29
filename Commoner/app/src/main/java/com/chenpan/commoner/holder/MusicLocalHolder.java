package com.chenpan.commoner.holder;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenpan.commoner.R;
import com.chenpan.commoner.bean.Music;
import com.chenpan.commoner.service.PlayService;
import com.chenpan.commoner.utils.CoverLoader;
import com.chenpan.commoner.utils.FileUtils;
import com.chenpan.commoner.utils.OnMoreClickListener;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/9/28.
 */
public class MusicLocalHolder extends BaseHolder<Music> {
    @Bind(R.id.v_playing)
    View vPlaying;
    @Bind(R.id.iv_cover)
    ImageView ivCover;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_artist)
    TextView tvArtist;
    @Bind(R.id.iv_more)
    ImageView ivMore;
    @Bind(R.id.v_divider)
    View vDivider;

    private OnMoreClickListener mListener;
    private int mPlayingPosition;
    public MusicLocalHolder(View view) {
        super(view);
    }

    @Override
    public void init() {
        super.init();
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void setData(Music music, final int position) {
        super.setData(music,position);
        if (position == mPlayingPosition) {
            vPlaying.setVisibility(View.VISIBLE);
        } else {
           vPlaying.setVisibility(View.INVISIBLE);
        }
        Bitmap cover = CoverLoader.getInstance().loadThumbnail(music.getCoverUri());
        ivCover.setImageBitmap(cover);
        tvTitle.setText(music.getTitle());
        String artist = FileUtils.getArtistAndAlbum(music.getArtist(), music.getAlbum());
        tvArtist.setText(artist);
        ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onMoreClick(position);
                }
            }
        });
        vDivider.setVisibility(isShowDivider(position) ? View.VISIBLE : View.GONE);
    }

    public void updatePlayingPosition(PlayService playService) {
        if (playService.getPlayingMusic() != null && playService.getPlayingMusic().getType() == Music.Type.LOCAL) {
            mPlayingPosition = playService.getPlayingPosition();
        } else {
            mPlayingPosition = -1;
        }
    }
    private boolean isShowDivider(int position) {
        return position != PlayService.getMusicList().size() - 1;
    }
    public void setOnMoreClickListener(OnMoreClickListener listener) {
        mListener = listener;
    }
}
