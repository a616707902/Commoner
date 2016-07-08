package com.chenpan.commoner.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.chenpan.commoner.R;
import com.chenpan.commoner.base.MyApplication;
import com.chenpan.commoner.utils.ToastFactory;

import java.io.File;

/**
 * 下载完成广播接收器
 * Created by hzwangchenyan on 2015/12/30.
 */
public class DownloadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        if (id == 0) {
            DownloadManager dManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = dManager.getUriForDownloadedFile(id);
            File file = new File(uri.getPath());
            if (file.exists()) {
                Intent install = new Intent(Intent.ACTION_VIEW);
                install.setDataAndType(uri, "application/vnd.android.package-archive");
                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(install);
            }
        } else {
            String title = MyApplication.getInstance().getDownloadList().get(id);
            if (!TextUtils.isEmpty(title)) {
                ToastFactory.show(context.getString(R.string.download_success, title));
            }
        }
    }
}
