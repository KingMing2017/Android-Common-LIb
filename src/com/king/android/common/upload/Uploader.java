package com.king.android.common.upload;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Uploader {

	/**
	 * �ϴ��ļ�
	 * @param filePath �����ļ�·��
	 * @param fileName �ļ�����
	 * @param targetUrl �ϴ�Ŀ���ַ
	 */
	public void uploadFile(String filePath, String fileName, String targetUrl) {

		String end = "/r/n";
		String Hyphens = "--";
		String boundary = "*****";

		try {

			URL url = new URL(targetUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			/* ����Input��Output����ʹ��Cache */
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);

			/* �趨���͵�method=POST */
			con.setRequestMethod("POST");

			/* setRequestProperty */
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

			/* �趨DataOutputStream */
			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
			ds.writeBytes(Hyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data; "
					+ "name=/'file1/';filename=/'" + fileName + "/'" + end);
			ds.writeBytes(end);

			/* ȡ���ļ���FileInputStream */
			FileInputStream fStream = new FileInputStream(filePath);

			/* �趨ÿ��д��1024bytes */
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			int length = -1;

			/* ���ļ���ȡ���ݵ������� */
			while ((length = fStream.read(buffer)) != -1) {
				/* ������д��DataOutputStream�� */
				ds.write(buffer, 0, length);
			}
			ds.writeBytes(end);
			ds.writeBytes(Hyphens + boundary + Hyphens + end);
			fStream.close();
			ds.flush();

			/* ȡ��Response���� */
			int ch = 0;
			InputStream is = con.getInputStream();
			StringBuffer b = new StringBuffer();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			}

			System.out.println("�ϴ��ɹ���"+b);

			ds.close();

		} catch (Exception e) {

			System.out.println("�ϴ�ʧ��" + e.getMessage());
		}
	}

}
