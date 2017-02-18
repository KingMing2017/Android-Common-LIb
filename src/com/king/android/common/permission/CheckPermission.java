package com.king.android.common.permission;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * ���Ȩ�޵Ĺ�����
 * @author xiao_ming
 *
 */
public class CheckPermission {

	private final Context context;

	//������
	public CheckPermission(Context context) {
		this.context = context.getApplicationContext();
	}

	//���Ȩ��ʱ���ж�ϵͳ��Ȩ�޼���
	public boolean permissionSet(String... permissions) {
		for (String permission : permissions) {
			if (isLackPermission(permission)) {//�Ƿ������ȫ��Ȩ�޼���
				return true;
			}
		}
		return false;
	}

	//���ϵͳȨ���ǣ��жϵ�ǰ�Ƿ�ȱ��Ȩ��(PERMISSION_DENIED:Ȩ���Ƿ��㹻)
	private boolean isLackPermission(String permission) {
		return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED;
	}

}
