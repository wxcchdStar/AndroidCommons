package wxc.android.commons.lib.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;

public class MediaUtils {

    public static String getAudioPath(Context context, Uri uri) {
        if (uri != null) {
            String scheme = uri.getScheme();
            if ("file".equals(scheme)) {
                return new File(uri.toString()).getPath();
            } else if ("content".equals(scheme)) {
                Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
                try {
                    if (cursor != null && cursor.moveToNext()) {
                        int pathIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
                        if (pathIndex > -1) {
                            return cursor.getString(pathIndex);
                        }
                    }
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
        }
        return null;
    }

    /**
     * 将指定文件添加到多媒体数据库中
     */
    public static void scanFile(Context context, String filePath) {
        if (filePath == null) return;

        Uri uri = Uri.fromFile(new File(filePath));
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }
}
