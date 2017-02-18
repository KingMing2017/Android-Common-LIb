package com.king.android.common.utils;

import java.util.ArrayList;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Androidϵͳ�Դ�������
 * @author xiao_ming
 *
 */
public class AndroidShare {
	/**
	 * ��������
	 */
	public final static int TO_OTHER = 0; 
	/**
	 * ����QQ
	 */
	public final static int TO_QQ = 1; 
	/**
	 *  ����QQ�ռ�
	 */
	public final static int TO_QZONE = 2;
	/**
	 * ����΢��
	 */
	public final static int TO_WECHAT = 3;
	/**
	 * ��������Ȧ
	 */
	public final static int TO_WECHAT_TIMELINE = 4;
	/**
	 * ������Ѷ΢��
	 */
	public final static int TO_TENCENT_WEIBO = 5;
	/**
	 * ��������΢��
	 */
	public final static int TO_SINA_WEIBO = 6;

	private final static String SHARE_CONTENT_TYPE_TEXT = "text/plain";
	private final static String SHARE_CONTENT_TYPE_IMAGE = "image/*";
	private final static String[][] toArray = {
		{"", ""},
		{"com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity"},
		{"com.qzone", "com.qzone.ui.operation.QZonePublishMoodActivity"},
		{"com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI"},
		{"com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI"},
		{"com.tencent.WBlog", "com.tencent.WBlog.intentproxy.TencentWeiboIntent"},
		{"com.sina.weibo", "com.sina.weibo.EditActivity"},
	};

	/**
	 * ��������ı�
	 * @param context
	 * @param content
	 * @param to
	 */
	public static void shareText(Context context, String content, int to){
		Intent intent = null;
		switch(to){
		case TO_QQ:
		case TO_QZONE:
		case TO_WECHAT:
		case TO_WECHAT_TIMELINE:
		case TO_TENCENT_WEIBO:
		case TO_SINA_WEIBO:
			intent = makeIntent(context, content, toArray[to][0], toArray[to][1]);
			context.startActivity(intent);
			break;
		case TO_OTHER:
		default:
			intent = makeIntent(context, content);
			context.startActivity(Intent.createChooser(intent, "����"));
			break;
		}
	}

	/**
	 * �������ͼƬ
	 * @param context
	 * @param uriList
	 * @param to
	 */
	public static void shareImages(Context context, ArrayList<Uri> uriList, int to) {
		Intent intent = null;
		switch(to){
		case TO_QQ:
		case TO_QZONE:
		case TO_WECHAT:
		case TO_WECHAT_TIMELINE:
		case TO_TENCENT_WEIBO:
		case TO_SINA_WEIBO:
			intent = makeIntent(context, uriList, toArray[to][0], toArray[to][1]);
			context.startActivity(intent);
			break;
		case TO_OTHER:
		default:
			intent = makeIntent(context, uriList);
			context.startActivity(Intent.createChooser(intent, "����"));
			break;
		}
	}

	/**
	 * �����ı�
	 * @param context
	 * @param content
	 */
	public static void shareText(Context context, String content) {
		shareText(context, content, TO_OTHER);
	}

	/**
	 * �������ͼƬ
	 * @param context
	 * @param uriList
	 */
	public static void shareImages(Context context, ArrayList<Uri> uriList) {

		shareImages(context, uriList, TO_OTHER);
	}

	private static Intent makeIntent(Context context, String content){
		return makeIntent(context, content, null, null);
	}
	
	private static Intent makeIntent(Context context, ArrayList<Uri> uriList){
		return makeIntent(context, uriList, null, null);
	}
	
	private static Intent makeIntent(Context context, String content, String packageName, String activityName){
		
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.setType(SHARE_CONTENT_TYPE_TEXT);
		shareIntent.putExtra(Intent.EXTRA_TEXT, content);
		
		if (packageName == null || activityName == null){
			return shareIntent;
		}
		
		shareIntent.setComponent(new ComponentName(packageName, activityName));

		return shareIntent;
	}

	private static Intent makeIntent(Context context, ArrayList<Uri> uriList, String packageName, String activityName){
		
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.setType(SHARE_CONTENT_TYPE_IMAGE);
		shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
		
		if (packageName == null || activityName == null){
			return null;
		}
		
		shareIntent.setComponent(new ComponentName(packageName, activityName));

		return shareIntent;
	}

}
