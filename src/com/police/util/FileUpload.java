package com.police.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

@TargetApi(Build.VERSION_CODES.KITKAT)
public class FileUpload {
	public int send(String url, String filePath) throws IOException {

        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            return -1;
        }
 
        /**
         * 第一部分
         */
        System.out.println(url);
        URL urlObj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
 
        /**
         * 设置关键值
         */
        con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false); // post方式不能使用缓存
 
        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
 
        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary="
                + BOUNDARY);
 
        // 请求正文信息
 
        // 第一部分：
        StringBuilder sb = new StringBuilder();
        sb.append("--"); // ////////必须多两道线
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\""
                + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");
 
        byte[] head = sb.toString().getBytes("utf-8");
 
        // 获得输出流
 
        OutputStream out = new DataOutputStream(con.getOutputStream());
        out.write(head);
 
        // 文件正文部分
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();
 
        // 结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
 
        out.write(foot);
 
        out.flush();
        out.close();
 
        /**
         * 读取服务器响应，必须读取,否则提交不成功
         */
 
       // return con.getResponseCode();
 
        /**
         * 下面的方式读取也是可以的
         */
        int result=0;
        try {
         //定义BufferedReader输入流来读取URL的响应
         BufferedReader reader = new BufferedReader(new InputStreamReader(
         con.getInputStream()));
        String line = null;
         while ((line = reader.readLine()) != null) {
        //System.out.println(line);
        	 JSONObject obj=new JSONObject(line);
        	 result=Integer.parseInt(obj.get("result").toString());
        }
        } catch (Exception e) {
        System.out.println("发送POST请求出现异常！" + e);
        e.printStackTrace();
        }
        return result;
    }
 
    public static void main(String[] args) throws IOException {
        FileUpload up = new FileUpload();
        System.out.println(up.send("http://localhost:88/oa/App/Attence!signIn.action?inAddress=cixi&inLng=10.1&inLat=191.1&userId=16&note=asd",
        "f:\\img.jpg"));
    }
    
    public Map<String,Object> sends(String url, String filePath) throws IOException {
    	Map<String,Object> map=new HashMap<String,Object>();
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            return null;
        }
 
        /**
         * 第一部分
         */
        System.out.println(url);
        URL urlObj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
 
        /**
         * 设置关键值
         */
        con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false); // post方式不能使用缓存
 
        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
 
        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary="
                + BOUNDARY);
 
        // 请求正文信息
 
        // 第一部分：
        StringBuilder sb = new StringBuilder();
        sb.append("--"); // ////////必须多两道线
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\""
                + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");
 
        byte[] head = sb.toString().getBytes("utf-8");
 
        // 获得输出流
 
        OutputStream out = new DataOutputStream(con.getOutputStream());
        out.write(head);
 
        // 文件正文部分
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();
 
        // 结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
 
        out.write(foot);
 
        out.flush();
        out.close();
 
        /**
         * 读取服务器响应，必须读取,否则提交不成功
         */
 
       // return con.getResponseCode();
 
        /**
         * 下面的方式读取也是可以的
         */
        try {
         //定义BufferedReader输入流来读取URL的响应
         BufferedReader reader = new BufferedReader(new InputStreamReader(
         con.getInputStream()));
        String line = null;
        StringBuilder sbread = new StringBuilder();
         while ((line = reader.readLine()) != null) {
        //System.out.println(line);
        	 sbread.append(line);
        }
    	 JSONObject obj=new JSONObject(sbread.toString());
    	 int result=Integer.parseInt(obj.get("result").toString());
    	 String path=obj.get("filepath").toString();
    	 String filename = obj.get("filename").toString();
    	 map.put("result", result);
    	 map.put("filePath", path);
    	 map.put("filename", filename);
        } catch (Exception e) {
        System.out.println("发送POST请求出现异常！" + e);
        e.printStackTrace();
        }
        return map;
    }
    
    public static String getDataColumn(Context context, Uri uri, String selection,  
            String[] selectionArgs) {  
      
        Cursor cursor = null;  
        final String column = "_data";  
        final String[] projection = {  
                column  
        };  
      
        try {  
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,  
                    null);  
            if (cursor != null && cursor.moveToFirst()) {  
                final int index = cursor.getColumnIndexOrThrow(column);  
                return cursor.getString(index);  
            }  
        } finally {  
            if (cursor != null)  
                cursor.close();  
        }  
        return null;  
    } 
    
    public static String getPath(Context context, Uri uri) {
    	final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;  
    	  
        // DocumentProvider  
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {  
            // ExternalStorageProvider  
            if (isExternalStorageDocument(uri)) {  
                final String docId = DocumentsContract.getDocumentId(uri);  
                final String[] split = docId.split(":");  
                final String type = split[0];  
      
                if ("primary".equalsIgnoreCase(type)) {  
                    return Environment.getExternalStorageDirectory() + "/" + split[1];  
                }  
      
                // TODO handle non-primary volumes  
            }  
            // DownloadsProvider  
            else if (isDownloadsDocument(uri)) {  
      
                final String id = DocumentsContract.getDocumentId(uri);  
                final Uri contentUri = ContentUris.withAppendedId(  
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));  
      
                return getDataColumn(context, contentUri, null, null);  
            }  
            // MediaProvider  
            else if (isMediaDocument(uri)) {  
                final String docId = DocumentsContract.getDocumentId(uri);  
                final String[] split = docId.split(":");  
                final String type = split[0];  
      
                Uri contentUri = null;  
                if ("image".equals(type)) {  
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;  
                } else if ("video".equals(type)) {  
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;  
                } else if ("audio".equals(type)) {  
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;  
                }  
      
                final String selection = "_id=?";  
                final String[] selectionArgs = new String[] {  
                        split[1]  
                };  
      
                return getDataColumn(context, contentUri, selection, selectionArgs);  
            }  
        }  
        // MediaStore (and general)  
        else if ("content".equalsIgnoreCase(uri.getScheme())) {  
      
            // Return the remote address  
            if (isGooglePhotosUri(uri))  
                return uri.getLastPathSegment();  
      
            return getDataColumn(context, uri, null, null);  
        }  
        // File  
        else if ("file".equalsIgnoreCase(uri.getScheme())) {  
            return uri.getPath();  
        }  
      
        return null;  
    }

    /** 
     * @param uri The Uri to check. 
     * @return Whether the Uri authority is ExternalStorageProvider. 
     */  
    public static boolean isExternalStorageDocument(Uri uri) {  
        return "com.android.externalstorage.documents".equals(uri.getAuthority());  
    }  
      
    /** 
     * @param uri The Uri to check. 
     * @return Whether the Uri authority is DownloadsProvider. 
     */  
    public static boolean isDownloadsDocument(Uri uri) {  
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());  
    }  
      
    /** 
     * @param uri The Uri to check. 
     * @return Whether the Uri authority is MediaProvider. 
     */  
    public static boolean isMediaDocument(Uri uri) {  
        return "com.android.providers.media.documents".equals(uri.getAuthority());  
    }  
      
    /** 
     * @param uri The Uri to check. 
     * @return Whether the Uri authority is Google Photos. 
     */  
    public static boolean isGooglePhotosUri(Uri uri) {  
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());  
    }  
    
	public int sendNotPic(String url) throws IOException{

 
        /**
         * 第一部分
         */
        System.out.println(url);
        URL urlObj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
 
        /**
         * 设置关键值
         */
        con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false); // post方式不能使用缓存
 
        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        /**
         * 下面的方式读取也是可以的
         */
        int result=0;
        try {
         //定义BufferedReader输入流来读取URL的响应
         BufferedReader reader = new BufferedReader(new InputStreamReader(
         con.getInputStream()));
        String line = null;
         while ((line = reader.readLine()) != null) {
        //System.out.println(line);
        	 JSONObject obj=new JSONObject(line);
        	 result=Integer.parseInt(obj.get("result").toString());
        }
        } catch (Exception e) {
        System.out.println("发送POST请求出现异常！" + e);
        e.printStackTrace();
        }
        return result;
	}
	
	public boolean SaveContentToFile(String filename,String content){
		  boolean flag = false;
		  FileOutputStream fileoutputstream = null;
		  
		  File file = new File(Environment.getExternalStorageDirectory(), filename);
		  
		  if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
		  {
			    try {
			    	fileoutputstream = new FileOutputStream(file);
			    	fileoutputstream.write(content.getBytes());
			    	flag=true;
				} catch (FileNotFoundException e) {
					// TODO: handle exception
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally{				 
						try {
							if(fileoutputstream!=null)
							fileoutputstream.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
		  }
		  
		  return flag;
	}
}
