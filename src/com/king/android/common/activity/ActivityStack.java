package com.king.android.common.activity;

import java.util.Stack;

import android.app.Activity;
/**
 * Activity����ջ
 * @author xiao_ming
 *
 */
public class ActivityStack {
	private static Stack<Activity> activityStack;
	private static ActivityStack instance;

	private ActivityStack() {
	}

	public static ActivityStack getActivityManager() {
		if (instance == null) {
			instance = new ActivityStack();
		}
		return instance;
	}

	/**
	 * �˳�ջ��Activity
	 * @param activity
	 */
	public void popActivity(Activity activity) {
		if (activity != null) {
			activity.finish();
			activityStack.remove(activity);
			activity = null;
		}
	}

	/**
	 * ��õ�ǰջ��Activity
	 * @return
	 */
	public Activity currentActivity() {
		if (!activityStack.isEmpty()){
			Activity activity = activityStack.lastElement();
			return activity;
		}
		return null;
	}

	/**
	 *  ����ǰActivityѹ��ջ��
	 * @param activity
	 */
	public void pushActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	/**
	 *  �˳�ջ������Activity
	 * @param cls
	 */
	public void popAllActivityExceptOne(Class<?> cls) {
		while (true) {
			Activity activity = currentActivity();
			if (activity == null) {
				break;
			}
			if (activity.getClass().equals(cls)) {
				break;
			}
			popActivity(activity);
		}
	}
}
