package com.customme.fullhdvideo.helperClassesGlory;

import java.text.DecimalFormat;

public class FileDataHelper {
    public static String getFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        int digitGroups = (int) (Math.log10((double) size) / Math.log10(1024.0d));
        return new DecimalFormat("#,##0.#")
                .format(((double) size) / Math.pow(1024.0d, (double) digitGroups))
                + " " + new String[]{"B", "KB", "MB", "GB", "TB"}[digitGroups];
    }
}
