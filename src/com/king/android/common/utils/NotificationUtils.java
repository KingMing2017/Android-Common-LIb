package com.king.android.common.utils;

import com.king.android.common.model.Notice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class NotificationUtils {
	
	private static NotificationManager getNotificationManager(Context context){
		return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	/**
	 * ��ʾ֪ͨ��
	 * @param context
	 * @param notice
	 */
	public static void showNotify(Context context, Notice notice){
		
		NotificationManager notificationManager = getNotificationManager(context);
		
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
		builder.setContentTitle(notice.getTitle())
				.setContentText(notice.getContent())
				.setContentIntent(notice.getContentIntent())
				.setNumber(notice.getNumber())//��ʾ����
				.setTicker(notice.getTicker())//֪ͨ�״γ�����֪ͨ��������������Ч����
				.setWhen(System.currentTimeMillis())//֪ͨ������ʱ�䣬����֪ͨ��Ϣ����ʾ
				.setPriority(notice.getPriority())//���ø�֪ͨ���ȼ�
//				.setAutoCancel(true)//���������־���û��������Ϳ�����֪ͨ���Զ�ȡ��  
				.setOngoing(false)//ture��������Ϊһ�����ڽ��е�֪ͨ������ͨ����������ʾһ����̨����,�û���������(�粥������)����ĳ�ַ�ʽ���ڵȴ�,���ռ���豸(��һ���ļ�����,ͬ������,������������)
				.setDefaults(Notification.DEFAULT_VIBRATE)//��֪ͨ������������ƺ���Ч������򵥡���һ�µķ�ʽ��ʹ�õ�ǰ���û�Ĭ�����ã�ʹ��defaults���ԣ�������ϣ�
				//Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND ������� // requires VIBRATE permission
				.setSmallIcon(notice.getSmallIcon())
				.setStyle(notice.getStyle())
				;
		
		notificationManager.notify(notice.getId(), builder.build());
	}
	
	/** 
	 * �����ǰ������֪ͨ�� 
	 */
	public static void clearNotify(Context context, int notifyId){
		getNotificationManager(context).cancel(notifyId);//ɾ��һ���ض���֪ͨID��Ӧ��֪ͨ
	}
	
	/**
	 * �������֪ͨ��
	 * */
	public static void clearAllNotify(Context context) {
		getNotificationManager(context).cancelAll();// ɾ���㷢������֪ͨ
	}
	
	/**
	 * @��ȡĬ�ϵ�pendingIntent,Ϊ�˷�ֹ2.3�����°汾����
	 * @flags����:  
	 * �ڶ�����פ:Notification.FLAG_ONGOING_EVENT  
	 * ���ȥ���� Notification.FLAG_AUTO_CANCEL 
	 */
	public static PendingIntent getDefalutIntent(Context context, int flags){
		PendingIntent pendingIntent= PendingIntent.getActivity(context, 1, new Intent(), flags);
		return pendingIntent;
	}
}
