package com.shi.androidstudy.tool;



import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/***
 * activity管理工具类
 * @author SHI
 * 2016-3-3 17:04:01
 */
public class ActivityCollectorUtil {

	public static List<AppCompatActivity> activities = new ArrayList<AppCompatActivity>();
	public static void addActivity(AppCompatActivity activity) {
		activities.add(activity);
	}
	public static void removeActivity(AppCompatActivity activity) {
		activities.remove(activity);
	}
	public static void finishAll() {
		for (AppCompatActivity activity : activities) {
			if (!activity.isFinishing()) {
				activity.finish();
			}
		}
	}
}
