package com.shi.androidstudy.tool;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/***
 * 系统工具类  获取  包管理器信息，SDcard信息，网络状况信息
 * @author SHI
 * 2016-2-25 16:39:55
 */
public class SystemUtil {



    /**
     * 获取当前应用版本号
     **/
    public static int getCurrentAppVersionCode(Context mContext) {
        // 获得包管理器，注意，整个android手机，共用一个包管理器
        PackageManager packageManager = mContext.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo != null) {
            return packageInfo.versionCode;
        } else {
            return 0;
        }
    }

    /**
     * 获取当前是否安装某个app
     **/
    public static boolean isExistsPackageApp(Context mContext,String packageName) {
        final PackageManager packageManager = mContext.getPackageManager();
        // 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        // 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取当前应用版包名
     **/
    public static String getCurrentAppVersionName(Context mContext) {
        // 获得包管理器，注意，整个android手机，共用一个包管理器
        PackageManager packageManager = mContext.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo != null) {
            return packageInfo.versionName;
        } else {
            return "";
        }
    }

    /**
     * SD卡是否存在
     **/
    public static File whetherExistSDcard() {
        // 判断sd卡是否存在
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory();// 获取跟目录
        }
        return null;
    }

    /****
     * 返回系统默认Download文件夹下的File文件路径
     * @param fileName   文件名称
     * @return
     */
    public static String getDownloadFilePath(String fileName) {
        String downDirectory = null;
        String FolderName = "Download";
        File pathSDcard = whetherExistSDcard();
        if (pathSDcard != null) {
            //创建文件夹
            if (createFolder(FolderName)) {
                downDirectory = pathSDcard.toString() + File.separator + FolderName
                        + File.separator + fileName;
            }
        }
        return downDirectory;
    }

    /****
     * 返回系统默认Download文件夹路径
     * @return
     */
    public static String getDownloadFilePath() {
        String downDirectory = null;
        String FolderName = "Download";
        File pathSDcard = whetherExistSDcard();
        if (pathSDcard != null) {
            //创建文件夹
            if (createFolder(FolderName)) {
                downDirectory = pathSDcard.toString() + File.separator + FolderName;
            }
        }
        return downDirectory;
    }

    /*****
     * 创建文件夹
     * @param FolderName 文件夹名称
     * @return
     */
    public static boolean createFolder(String FolderName) {

        File pathSDcard = whetherExistSDcard();
        if (pathSDcard != null) {
            File file = new File(pathSDcard.toString() + File.separator + FolderName);

            if (!file.exists()) {// 目录不存在
                file.mkdirs();
            }
            return true;
        } else {
            return false;
        }
    }


    /****
     * 判断是否有网络连接
     * @param mContext 设备上下文
     * @return
     */
    public static boolean isNetworkConnected(Context mContext) {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /****
     * 判断WiFi网络是否可用
     * @param mContext
     * @return
     */
    public static boolean isWifiConnected(Context mContext) {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /****
     * 判断MOBILE网络是否可用
     * @param mContext
     * @return
     */
    public static boolean isMobileConnected(Context mContext) {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /****
     * 获取当前网络连接类型
     * @param mContext
     * @return
     *
     */
    public static int getConnectedType(Context mContext) {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }

    /***
     * 获取手机时间按照fromat格式返回
     * @param format
     * @return
     */
    public static String getCurrentTime(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        String currentTime = sdf.format(date);
        return currentTime;
    }

    /**
     * 判断指定名称的服务是否运行
     *
     * @param act  activity
     * @param name 服务的类的全名
     * @return
     */
    public static boolean isServiceRunning(Activity act, String name) {

        ActivityManager am = (ActivityManager) act.getSystemService(Context.ACTIVITY_SERVICE);
        // 返回正在运行的服务的信息列表
        List<RunningServiceInfo> infoList = am.getRunningServices(100); // 参数是指，最多返回多少个服务的信息
        for (RunningServiceInfo runningServiceInfo : infoList) {
            String className = runningServiceInfo.service.getClassName();
//			System.out.println("className::"+className);
            if (className.equals(name)) {
                return true;
            }
        }
        return false;
    }

    /***
     * 获取手机时间
     * @return
     */
    public static String getCurrentTime() {
        return getCurrentTime("yyyy-MM-dd  HH:mm:ss");
    }

    // IMEI码
    public static String getIMIEStatus(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String deviceId = tm.getDeviceId();
        return deviceId;
    }

    // Mac地址
    private static String getLocalMac(Context context) {
        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    // Android Id
    private static String getAndroidId(Context context) {
        String androidId = Settings.Secure.getString(
                context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidId;
    }
}













