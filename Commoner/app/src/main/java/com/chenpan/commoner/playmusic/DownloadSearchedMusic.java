package com.chenpan.commoner.playmusic;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import com.chenpan.commoner.R;
import com.chenpan.commoner.base.MyApplication;
import com.chenpan.commoner.bean.Constants;
import com.chenpan.commoner.bean.JDownloadInfo;
import com.chenpan.commoner.bean.JLrc;
import com.chenpan.commoner.bean.JSearchMusic;
import com.chenpan.commoner.network.JsonCallback;
import com.chenpan.commoner.network.NetWorkUtil;
import com.chenpan.commoner.utils.FileUtils;
import com.chenpan.commoner.utils.Preferences;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import okhttp3.Call;

/**
 * 下载搜索的音乐
 * Created by hzwangchenyan on 2016/1/13.
 */
public abstract class DownloadSearchedMusic {
    private Context mContext;
    private JSearchMusic.JSong mJSong;

    public DownloadSearchedMusic(Context context, JSearchMusic.JSong jSong) {
        mContext = context;
        mJSong = jSong;
    }

    public void execute() {
        checkNetwork();
    }

    private void checkNetwork() {
        boolean mobileNetworkDownload = Preferences.enableMobileNetworkDownload();
        if (NetWorkUtil.isActiveNetworkMobile(mContext) && !mobileNetworkDownload) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle(R.string.tips);
            builder.setMessage(R.string.download_tips);
            builder.setPositiveButton(R.string.download_tips_sure, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    download();
                }
            });
            builder.setNegativeButton(R.string.cancel, null);
            Dialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } else {
            download();
        }
    }

    private void download() {
        onPrepare();

        final DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        // 获取歌曲下载链接
        OkHttpUtils.get().url(Constants.BASE_URL)
                .addParams(Constants.PARAM_METHOD, Constants.METHOD_DOWNLOAD_MUSIC)
                .addParams(Constants.PARAM_SONG_ID, mJSong.getSongid())
                .build()
                .execute(new JsonCallback<JDownloadInfo>(JDownloadInfo.class) {
                    @Override
                    public void onResponse(final JDownloadInfo response) {
                        if (response == null) {
                            onFail(null, null);
                            return;
                        }
                        Uri uri = Uri.parse(response.getBitrate().getFile_link());
                        DownloadManager.Request request = new DownloadManager.Request(uri);
                        String mp3FileName = FileUtils.getMp3FileName(mJSong.getArtistname(), mJSong.getSongname());
                        request.setDestinationInExternalPublicDir(FileUtils.getRelativeMusicDir(), mp3FileName);
                        request.setMimeType(MimeTypeMap.getFileExtensionFromUrl(response.getBitrate().getFile_link()));
                        request.allowScanningByMediaScanner();
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
                        request.setAllowedOverRoaming(false);// 不允许漫游
                        long id = downloadManager.enqueue(request);
                        MyApplication.getInstance().getDownloadList().put(id, mJSong.getSongname());
                        onSuccess();
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        onFail(call, e);
                    }
                });
        // 下载歌词
        String lrcFileName = FileUtils.getLrcFileName(mJSong.getArtistname(), mJSong.getSongname());
        File lrcFile = new File(FileUtils.getLrcDir() + lrcFileName);
        if (!lrcFile.exists()) {
            OkHttpUtils.get().url(Constants.BASE_URL)
                    .addParams(Constants.PARAM_METHOD, Constants.METHOD_LRC)
                    .addParams(Constants.PARAM_SONG_ID, mJSong.getSongid())
                    .build()
                    .execute(new JsonCallback<JLrc>(JLrc.class) {
                        @Override
                        public void onResponse(JLrc response) {
                            if (response == null || TextUtils.isEmpty(response.getLrcContent())) {
                                return;
                            }
                            String lrcPath = FileUtils.getLrcDir() + FileUtils.getLrcFileName(mJSong.getArtistname(), mJSong.getSongname());
                            saveLrcFile(lrcPath, response.getLrcContent());
                        }

                        @Override
                        public void onError(Call call, Exception e) {
                        }
                    });
        }
    }

    public static void saveLrcFile(String path, String content) {
        try {
            FileWriter writer = new FileWriter(path);
            writer.flush();
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void onPrepare();

    public abstract void onSuccess();

    public abstract void onFail(Call call, Exception e);
}
