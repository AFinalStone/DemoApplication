package com.shi.androidstudy.tool;

import java.io.File;

/**
 * Created by Administrator on 2017/7/12.
 * 邮箱：602392033@qq.com
 */

public  class FileUtil   {

    /****
     * 删除文件夹或者文件
     * @param file
     * @return
     */
    public static boolean deleteDirectory(File file) {
        if (file.isDirectory()) {
            File[] filelist = file.listFiles();
            for (int i = 0; i < filelist.length; i++) {
                deleteDirectory(filelist[i]);
            }
            if (!file.delete()) {
                return false;
            }
        } else {
            if (!file.delete()) {
                return false;
            }
        }
        return true;
    }

}
