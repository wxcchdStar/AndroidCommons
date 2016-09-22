package wxc.android.commons.lib.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class FileUtils {

    public static boolean isStorageEnough(String dirPath, long neededSize) {
        if (dirPath == null) return false;

        StatFs statFs = new StatFs(dirPath);
        long blockSize;
        long availableBlocks;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = statFs.getBlockSize();
            availableBlocks = statFs.getAvailableBlocks();
        } else {
            blockSize = statFs.getBlockSizeLong();
            availableBlocks = statFs.getAvailableBlocksLong();
        }
        return availableBlocks * blockSize > neededSize;
    }

    public static boolean isSdCardExist() {
        // Check if media is mounted or storage is built-in
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable();
    }

    /**
     * @param type 存储目录类型
     *
     * {@link Environment#DIRECTORY_MUSIC},
     * {@link Environment#DIRECTORY_PODCASTS},
     * {@link Environment#DIRECTORY_RINGTONES},
     * {@link Environment#DIRECTORY_ALARMS},
     * {@link Environment#DIRECTORY_NOTIFICATIONS},
     * {@link Environment#DIRECTORY_PICTURES},
     * {@link Environment#DIRECTORY_MOVIES}.
     */
    public static String getStorageDir(Context context, String type) {
        String path;
        if (isSdCardExist()) {
            path = getStorageDir(context, type);
        } else {
            path = getInternalStorageDir(context, type);
        }
        appendSeparator(path);
        return path;
    }

    public static String getExternalStorageDir(Context context, String type) {
        String path;
        File file = context.getExternalFilesDir(type);
        if (file == null) {
            path = "/Android/data/" + context.getPackageName() + "/files/";
        } else {
            path = file.getPath();
        }
        mkdirs(path);
        return path;
    }

    private static String getInternalStorageDir(Context context, String type) {
        String path = context.getFilesDir().getPath();
        path = appendSeparator(path);
        path += type;
        mkdirs(path);
        return path;
    }

    public static String appendSeparator(String path) {
        if (path.charAt(path.length() - 1) != File.separatorChar) {
            path += File.separator;
        }
        return path;
    }

    public static void mkdirs(String dirPath) {
        if (dirPath == null) return;

        File storageDir = new File(dirPath);
        if (storageDir.isDirectory() && !storageDir.exists()) {
            storageDir.mkdirs();
        }
    }

    public static String getFileExtensionByName(String fileName) {
        int index = fileName.lastIndexOf(".");
        String extension = null;
        if (index > 0) {
            extension = fileName.substring(index + 1);
        }
        return extension;
    }
}
