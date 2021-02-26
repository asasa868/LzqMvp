package com.shuyi.lzqmvp.Utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Stack;


public class ActivityManagerUtil {
	private static Stack<Activity> activityStack = null;
	private static ActivityManagerUtil instance = null;

	private ActivityManagerUtil() {
	}

	/**
	 * 
	 * @Title: getInstance
	 * @Description: 单一实例
	 * @return
	 * @return ActivityManagerUtil
	 */
	public static ActivityManagerUtil getInstance() {
		if (instance == null) {
			instance = new ActivityManagerUtil();
		}
		return instance;
	}

	/**
	 * 
	 * @Title: addActivity
	 * @Description: 添加Activity到堆栈
	 * @param activity
	 * @return void
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	/**
	 * 
	 * @Title: currentActivity
	 * @Description: 获取当前Activity（堆栈中最后一个压入的）
	 * @return
	 * @return SearchMsgActivity
	 */
	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	public int getSize() {
		return activityStack == null ? 0 : activityStack.size();
	}

	/**
	 * 
	 * @Title: finishActivity
	 * @Description: 结束当前Activity（堆栈中最后一个压入的）
	 * @return void
	 */
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * 
	 * @Title: finishActivity
	 * @Description: 结束指定的Activity
	 * @param activity
	 * @return void
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 
	 * @Title: finishActivity
	 * @Description: 结束指定类名的Activity
	 * @param cls
	 * @return void
	 */
	public void finishActivity(Class<?> cls) {
		if (activityStack != null) {
			for (int i = 0, size = activityStack.size(); i < size; i++) {
				Activity activity = activityStack.get(i);
				if (activity != null && activity.getClass().equals(cls)) {
					activity.finish();
					activity = null;
				}
			}
		}
	}
	
	/**
	 * 
	 * @Title: finishActivity
	 * @Description: 结束指定类名的Activity
	 * @param cls
	 * @return void
	 */
	public void finishOtherActivity(Class<?> cls) {
		ArrayList<Activity> list = null;
		int activityNum = 0;
		if (activityStack != null) {
			list = new ArrayList<Activity>();
			for (int i = 0, size = activityStack.size(); i < size; i++) {
				Activity activity = activityStack.get(i);
				if (activity != null && activity.getClass().equals(cls)) {
					list.add(activity);
				}
			}
			activityNum = list.size();
			if(activityNum>1){
				for(int j=0;j<activityNum-1;j++){
					Activity activity = list.get(j);
					activity.finish();
					activity = null;
				}
			}
		}
	}
	

	/**
	 * 
	 * @Title: remainActivity
	 * @Description: 保留唯一的Activity
	 * @return void
	 */
	public void remainActivity(Class<?> clazz) {
		if (activityStack != null) {
			for (int i = 0, size = activityStack.size(); i < size; i++) {
				if (null != activityStack.get(i)) {
					if (!activityStack.get(i).getClass().equals(clazz)) {
					    activityStack.get(i).finish();
					}
				}
			}
			activityStack.clear();
		}
	}

	public void remainActivity(String clazzName) {
		if (activityStack != null) {
			for (int i = 0, size = activityStack.size(); i < size; i++) {
				if (null != activityStack.get(i)) {
					if (!activityStack.get(i).getClass().getName().equals(clazzName)) {
						activityStack.get(i).finish();
					}
				}
			}
			activityStack.clear();
		}
	}
	

	/**
	 * 
	 * @Title: finishAllActivity
	 * @Description: 结束所有Activity
	 * @return void
	 */
	public void finishAllActivity() {
		if (activityStack != null) {
			for (int i = 0, size = activityStack.size(); i < size; i++) {
				if (null != activityStack.get(i)) {
					activityStack.get(i).finish();
				}
			}
			activityStack.clear();
		}
	}

}
