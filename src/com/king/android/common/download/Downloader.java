package com.king.android.common.download;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.NumberFormat;

import com.king.android.common.AppLog;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.webkit.MimeTypeMap;

/**
 * �ļ�����
 * Api level 11
 * @author xiao_ming
 *
 */
public class Downloader {

	private Handler mHandler = null;

	private DownloadManager mDownloadManager = null;  
	private long mLastDownloadId = 0;

	public Downloader(Context context, Handler handler){
		//		this.mContext = context;
		this.mHandler = handler;
		this.mDownloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
	}

	public long enqueue(DownloadRequestArgs args){
		if (args == null){
			AppLog.e(getClass(), "Downloader.enqueue(args): args is null.");
			return 0;
		}

		Uri uri = Uri.parse(encodeGB(args.getFileUrl()));
		Request request = new Request(uri);

		request.setAllowedNetworkTypes(Request.NETWORK_MOBILE | Request.NETWORK_WIFI);   
		request.setAllowedOverRoaming(false); // �Ƿ���������
		// ��������·�����ļ���
		request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, args.getFileName());
		request.setTitle(args.getTitle());
		request.setDescription(args.getDesc());
		//�����ļ�����  
		MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();  
		String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(args.getFileUrl()));  
		request.setMimeType(mimeString);  
		//��֪ͨ������ʾ   
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);  
		// ����Ϊ�ɱ�ý��ɨ�����ҵ�
		// request.allowScanningByMediaScanner();
		// ����Ϊ�ɼ��Ϳɹ���
		request.setVisibleInDownloadsUi(true);

		long refernece = mDownloadManager.enqueue(request);

		mHandler.post(mDownloadRunnable);

		mLastDownloadId = refernece;

		return refernece;
	}

	public void stop(){
		mDownloadManager.remove(mLastDownloadId);
	}

	private int queryDownloadStatus() { 
		int ret = 0;
		DownloadManager.Query query = new DownloadManager.Query();   
		query.setFilterById(mLastDownloadId);   
		Cursor c = mDownloadManager.query(query); 
		try {
			if(c.moveToFirst()) {   
				int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));  
//				String title = c.getString(c.getColumnIndex(DownloadManager.COLUMN_TITLE));    
				int fileSize = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));    
				int bytesDL = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));    

				switch(status) {   
				case DownloadManager.STATUS_PAUSED:
					AppLog.d(getClass(), "DOWNLOAD STATUS_PAUSED");  
				case DownloadManager.STATUS_PENDING:   
					AppLog.d(getClass(), "DOWNLOAD STATUS_PENDING");  
				case DownloadManager.STATUS_RUNNING:   
					//�������أ������κ�����  
					AppLog.d(getClass(), "DOWNLOAD STATUS_RUNNING "+ bytesDL + "/" + fileSize + " = " + getPercent(bytesDL,fileSize));
					break;   
				case DownloadManager.STATUS_SUCCESSFUL:   
					//���  
					AppLog.d(getClass(), "DOWNLOAD COMPLETED");
					//					mDownloadManager.remove(mLastDownloadId);
					mHandler.removeCallbacks(mDownloadRunnable);
					break;   
				case DownloadManager.STATUS_FAILED:  
					//��������ص����ݣ���������  
					AppLog.d(getClass(), "DOWNLOAD STATUS_FAILED"); 
					mDownloadManager.remove(mLastDownloadId);
					mHandler.removeCallbacks(mDownloadRunnable);
					break;   
				}
				ret = status;
				Message msg = new Message();
				msg.what = status;
				msg.obj = getPercent(bytesDL,fileSize);
				msg.arg1 = (int) mLastDownloadId;
				mHandler.sendMessage(msg);
			}  
		} finally {
			if (c != null){
				c.close();
			}
		}
		return ret;
	}  

	/** 
	 * �����������֧������·�����������Ҫת��url�ı��롣 
	 * @param urlstr 
	 * @return 
	 */  
	private String encodeGB(String urlstr) {  
		//ת�����ı���  
		String split[] = urlstr.split("/");  
		for (int i = 1; i < split.length; i++) {  
			try {  
				split[i] = URLEncoder.encode(split[i], "GB2312");  
			} catch (UnsupportedEncodingException e) {  
				e.printStackTrace();  
			}  
			split[0] = split[0]+"/"+split[i];  
		}  
		split[0] = split[0].replaceAll("\\+", "%20");//����ո�  
		return split[0];  
	}  
	/**
	 * ����ٷֱ�
	 * @param x
	 * @param y
	 * @return
	 */
	private String getPercent(int x,int y){  

		NumberFormat numberFormat = NumberFormat.getInstance();  
		// ���þ�ȷ��С�����2λ  
		numberFormat.setMaximumFractionDigits(1);  
		String result = numberFormat.format((float) x / (float) y * 100);

		return result + "%";  
	}

	private Runnable mDownloadRunnable = new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			int ret = queryDownloadStatus();
			if (ret != DownloadManager.STATUS_SUCCESSFUL){
				mHandler.postDelayed(mDownloadRunnable, 1000);
			} else {
				mHandler.removeCallbacks(this);
			}
		}

	};

}

