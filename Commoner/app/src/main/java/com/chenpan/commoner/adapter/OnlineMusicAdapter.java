package com.chenpan.commoner.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenpan.commoner.R;
import com.chenpan.commoner.bean.JOnlineMusic;
import com.chenpan.commoner.utils.FileUtils;
import com.chenpan.commoner.utils.ImageUtils;
import com.chenpan.commoner.utils.OnMoreClickListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 在线音乐列表适配器
 * Created by wcy on 2015/12/22.
 */
public class OnlineMusicAdapter extends BaseAdapter {
    private List<JOnlineMusic> mData;
    private OnMoreClickListener mListener;

    public OnlineMusicAdapter(List<JOnlineMusic> data) {
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_music, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        JOnlineMusic jOnlineMusic = mData.get(position);
        ImageLoader.getInstance().displayImage(jOnlineMusic.getPic_small(), holder.ivCover, ImageUtils.getCoverDisplayOptions());
        holder.tvTitle.setText(jOnlineMusic.getTitle());
        String artist = FileUtils.getArtistAndAlbum(jOnlineMusic.getArtist_name(), jOnlineMusic.getAlbum_title());
        holder.tvArtist.setText(artist);
        holder.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onMoreClick(position);
            }
        });
        holder.vDivider.setVisibility(isShowDivider(position) ? View.VISIBLE : View.GONE);
        return convertView;
    }

    private boolean isShowDivider(int position) {
        return position != mData.size() - 1;
    }

    public void setOnMoreClickListener(OnMoreClickListener listener) {
        mListener = listener;
    }

    class ViewHolder {
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

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
