package com.chenpan.commoner.fragment;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.chenpan.commoner.MainActivity;
import com.chenpan.commoner.R;
import com.chenpan.commoner.adapter.LocalMusicAdapter;
import com.chenpan.commoner.base.BaseFragment;
import com.chenpan.commoner.base.MyApplication;
import com.chenpan.commoner.bean.Music;
import com.chenpan.commoner.mvp.presenter.MusicLocalPresenter;
import com.chenpan.commoner.mvp.view.IMusicLocalView;
import com.chenpan.commoner.service.PlayService;
import com.chenpan.commoner.utils.FileUtils;
import com.chenpan.commoner.utils.OnMoreClickListener;
import com.chenpan.commoner.utils.SystemUtils;
import com.chenpan.commoner.utils.ToastFactory;

import java.io.File;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/7/6.
 * 本地音乐
 */
public class MusicLocalFragment extends BaseFragment<IMusicLocalView, MusicLocalPresenter> implements IMusicLocalView {
    @Bind(R.id.lv_local_music)
    ListView lvLocalMusic;
    @Bind(R.id.tv_empty)
    TextView tvEmpty;

    private PlayService mPlayService;


    protected Handler mHandler;

    private LocalMusicAdapter mAdapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mHandler=new Handler();
        if (activity instanceof MainActivity) {
            mPlayService = ((MainActivity) activity).getPlayService();
        }
    }

    @Override
    public MusicLocalPresenter createPresenter() {
        return new MusicLocalPresenter();
    }

    @Override
    public void bindViewAndAction(Bundle savedInstanceState) {
        mAdapter = new LocalMusicAdapter();
        mAdapter.setOnMoreClickListener(new OnMoreClickListener() {
            @Override
            public void onMoreClick(int position) {
                final Music music = PlayService.getMusicList().get(position);
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle(music.getTitle());
                int itemsId = position == mPlayService.getPlayingPosition() ? R.array.local_music_dialog_without_delete : R.array.local_music_dialog;
                dialog.setItems(itemsId, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {

                            case 0:// 设为铃声
                                setRingtone(music);
                                break;
                            case 1:// 查看歌曲信息
                                musicInfo(music);
                                break;
                            case 2:// 删除
                                deleteMusic(music);
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });
        lvLocalMusic.setAdapter(mAdapter);
        if (mPlayService.getPlayingMusic() != null && mPlayService.getPlayingMusic().getType() == Music.Type.LOCAL) {
            lvLocalMusic.setSelection(mPlayService.getPlayingPosition());
        }
        lvLocalMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getPlayService().play(position);
                mAdapter.updatePlayingPosition(getPlayService());
                mAdapter.notifyDataSetChanged();
            }
        });

        updateView();
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        getActivity().registerReceiver(mDownloadReceiver, filter);

    }



    @Override
    public int getContentLayout() {
        return R.layout.fragment_local_music;
    }

    private void updateView() {
        if (PlayService.getMusicList().isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            tvEmpty.setVisibility(View.GONE);
        }
        mAdapter.updatePlayingPosition(mPlayService);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {

    }


    private BroadcastReceiver mDownloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            String title = MyApplication.getInstance().getDownloadList().get(id);
            if (TextUtils.isEmpty(title)) {
                return;
            }
            // 由于系统扫描音乐是异步执行，因此延迟刷新音乐列表
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!isAdded()) {
                        return;
                    }
                    mPlayService.updateMusicList();
                    updateView();
                }
            }, 500);
        }
    };
    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(mDownloadReceiver);
        super.onDestroy();
    }
    /**
     * 设置铃声
     */
    private void setRingtone(Music music) {
        Uri uri = MediaStore.Audio.Media.getContentUriForPath(music.getUri());
        // 查询音乐文件在媒体库是否存在
        Cursor cursor = getActivity().getContentResolver().query(uri, null,
                MediaStore.MediaColumns.DATA + "=?", new String[]{music.getUri()}, null);
        if (cursor == null) {
            return;
        }
        if (cursor.moveToFirst() && cursor.getCount() > 0) {
            String _id = cursor.getString(0);
            ContentValues values = new ContentValues();
            values.put(MediaStore.Audio.Media.IS_MUSIC, true);
            values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
            values.put(MediaStore.Audio.Media.IS_ALARM, false);
            values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
            values.put(MediaStore.Audio.Media.IS_PODCAST, false);

            getActivity().getContentResolver().update(uri, values, MediaStore.MediaColumns.DATA +
                    "=?", new String[]{music.getUri()});
            Uri newUri = ContentUris.withAppendedId(uri, Long.valueOf(_id));
            RingtoneManager.setActualDefaultRingtoneUri(getActivity(),
                    RingtoneManager.TYPE_RINGTONE, newUri);
            ToastFactory.show("设置成功");
        }
        cursor.close();
    }

    /**
     * 查看歌曲信息
     * @param music
     */
    private void musicInfo(Music music) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(music.getTitle());
        StringBuilder sb = new StringBuilder();
        sb.append("艺术家：")
                .append(music.getArtist())
                .append("\n\n")
                .append("专辑：")
                .append(music.getAlbum())
                .append("\n\n")
                .append("播放时长：")
                .append(SystemUtils.formatTime("mm:ss", music.getDuration()))
                .append("\n\n")
                .append("文件名称：")
                .append(music.getFileName())
                .append("\n\n")
                .append("文件大小：")
                .append(FileUtils.b2mb((int) music.getFileSize()))
                .append("MB")
                .append("\n\n")
                .append("文件路径：")
                .append(new File(music.getUri()).getParent());
        dialog.setMessage(sb.toString());
        dialog.show();
    }
    /**
     * 删除音乐
     */
    private void deleteMusic(final Music music) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        String title = music.getTitle();
        String msg = getString(R.string.delete_music, title);
        dialog.setMessage(msg);
        dialog.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PlayService.getMusicList().remove(music);
                File file = new File(music.getUri());
                if (file.delete()) {
                    getPlayService().updatePlayingPosition();
                    updateView();
                    // 刷新媒体库
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + music.getUri()));
                    getActivity().sendBroadcast(intent);
                }
            }
        });
        dialog.setNegativeButton(R.string.cancel, null);
        dialog.show();
    }

}
