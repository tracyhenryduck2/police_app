package com.police;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import cn.jpush.android.api.JPushInterface;

import com.police.R;
import com.police.util.AndroidHenryUtl;
import com.police.util.AttenceService;
import com.police.util.BaseService;
import com.police.util.Config;
import com.police.util.FileUpload;
import com.police.util.JsOperation;
import com.police.util.ServiceForAccount;
import com.police.util.BaseService.UpdateInfo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class WebActivity extends Activity {

	private WebView webView = null;
	private JsOperation client=null;
	private static WebActivity act=null;
	private final String IMAGE_TYPE = "image/*";
	private final int IMAGE_CODE = 0;   //这里的IMAGE_CODE是自己任意定义的
	private final int CHOOSE_INFO=1;  //回调人员选择
	private  ServiceForAccount account=null;
	private int result;
	private String actionName=null;
	String picturePath;
	String picPath;
	String filename;
	private ProgressDialog pBar;
	public static String packageName = null;
	private int progress = 0;
	private final static int DOWN_UPDATE = 411;

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		act=this;
		setContentView(R.layout.web);
		webView = (WebView) findViewById(R.id.toutput);
		client=new JsOperation(this,webView);
		init();
		String url = getIntent().getStringExtra("url");
		if(url==null)
		url = "file:///android_asset/html/departmentlist2.html";
		open(url);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		JPushInterface.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
	}
	
	private void open(String url){
		int index=0;
		if((index=url.indexOf('?'))>0){
			client.setParm(url.substring(index));
			webView.loadUrl(url.substring(0,index));
		}else{
			webView.loadUrl(url);
		}
	}
	
	public static void refresh(){
		try{
			act.webView.reload();
		}catch(Exception e){
			
		}
	}

	private void init() {
		initWebView();
		account= new ServiceForAccount(this);
	}


	private void initWebView() {
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSaveFormData(false);
		webSettings.setSavePassword(false);
		webSettings.setSupportZoom(false);
		webSettings.setAppCacheEnabled(true);
		webView.addJavascriptInterface(client, "client");
		/* 获取标题 */
		webView.setWebChromeClient(new WebChromeClient() {
			
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
				//titleView.setText(title);
			}
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				super.onProgressChanged(view, newProgress);
			}
			
		});
		
		
		
		/** 设置在同一webview加载 */
		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) { // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
 
		         if(url.indexOf(".jpg")!=-1 || url.indexOf(".jpeg")!=-1 || url.indexOf(".png")!=-1 || url.indexOf(".bmp")!=-1 || url.indexOf(".gif")!=-1)
		         {
		              Uri uri = Uri.parse(url); //url为你要链接的地址  
		    		  
		              Intent intent =new Intent(Intent.ACTION_VIEW, uri);  
		              intent.setDataAndType(uri, "image/*");
		              startActivity(intent);   
		         }
		         else  view.loadUrl(url);
 
				return true;
			}

		});
		/** 取消选择文字功能 */
		webView.setOnLongClickListener(new WebView.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				return true;
			}
		});
		webView.setDownloadListener(new DownloadListener(){
			@Override
			public void onDownloadStart(String url, String userAgent, String contentDisposition,
					String mimetype, long contentLength) {
				Uri uri = Uri.parse(url);  
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);  
				startActivity(intent);
			}
		});
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			goBack();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	public void goBack() {
		//先判断本页面后退
		if("1".equals(client.getParm())){
			webView.loadUrl("javascript:goBack();");
		}else{
			if (webView.canGoBack()) {
				webView.goBack(); // 后退
				//webView.reload();
			} else {
				finish();
				//overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {        //此处的 RESULT_OK 是系统自定义得一个常量
	        return;
	    }
	    //此处的用于判断接收的Activity是不是你想要的那个
	    if (requestCode == IMAGE_CODE) {
	    	   Uri selectedImage = data.getData();
//	    	   String[] filePathColumns={MediaStore.Images.Media.DATA};
//	    	   Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null,null, null);
//	    	   c.moveToFirst();
//	    	   int columnIndex = c.getColumnIndex(filePathColumns[0]);
//	    	   picturePath= c.getString(columnIndex);
//	    	   c.close();
	    	   
	    	   picturePath = FileUpload.getPath(this, selectedImage);
	    	   	    	   
	    	   uploadImg();
	    }
	    
	    if(requestCode==CHOOSE_INFO)
	    {
	        Bundle buddle = data.getExtras();   
	        String info = buddle.getString("info");   
	        webView.loadUrl("javascript:refreshInfo('"+info+"')");
	    }
	}

	private void uploadImg() {
		new Thread(){
			public void run(){
				System.out.println("小亨上传:"+picturePath);
				Map<String,Object> map = new HashMap<String, Object>();
				if((map=BaseService.uploadPic(picturePath,actionName))!=null)
				{
					result=(Integer)map.get("result");
					picPath=map.get("filePath").toString();
					filename = map.get("filename").toString();
				}
				handler.sendMessage(handler.obtainMessage(0));
			}
		}.start();
	}
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			
	         switch(msg.what){
				case 403:
					doNewVersionUpdate((UpdateInfo) msg.obj);
					break;
				case DOWN_UPDATE:
					pBar.setProgress(progress);
					break;
	         }

		}
	};
	private void OnRefresh(){
		webView.loadUrl("javascript:RefreshImage('"+ 
                picPath +"','"+filename+ "')");
	}
	private void OnResult(){
		if(result==0){
			showMsg("提交失败，请稍后重试...");
		}else if(result==1){
			showMsg("提交成功");
		}else{
			showMsg("重新提交成功");
		}
	}
	public void showMsg(String str) {
		Toast.makeText(this,str, Toast.LENGTH_SHORT).show();
	}
	
	public void showPhotoSheet(String actName) {
		actionName = actName;
		Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE); 
	    intent.setType(IMAGE_TYPE);
        //Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);  
	    startActivityForResult(intent, IMAGE_CODE);
	}
    
	public void chooseInfo(String url){
		Intent intent = new Intent();
		intent.setClass(this, WebActivity.class);
		intent.putExtra("url", "file:///android_asset/html/"+url);
		startActivityForResult(intent, CHOOSE_INFO);
	}
	
   public void refreshInfo(String info_json){
       Intent intent = new Intent(this, WebActivity.class);   
       intent.putExtra("info", info_json);
       setResult(RESULT_OK, intent);  
       this.finish(); 
   }
   
	public void saveGlobalInfo(String key,String value){
		account.saveKeyValue(key, value);
	}
	
	public String readGlobalInfo(String key){
		
		return account.getValueByKey(key);
	}
	
	public void saveNotGlobalInfo(String key,String value)
	{
		BaseService.account_map.put(key, value);
	}
	public String readNotGlobalInfo(String key){
		return BaseService.account_map.get(key);
	}
	
	private void initUpdate() {
		packageName = this.getClass().getPackage().getName();
		new Thread() {
			public void run() {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (checkUpdate()) {
					handler.sendMessage(handler.obtainMessage(402));
					
				}
			}
		}.start();
	}

	private boolean checkUpdate() {
		UpdateInfo info = BaseService.getUpdateInfo();
		if(info==null) return true;
		if (Config.getVerCode(this, packageName) < info.code) {
			handler.sendMessage(handler.obtainMessage(403, info));
			return false;
		} else {
			return true;
		}
	}

	private void doNewVersionUpdate(UpdateInfo info) {

		int verCode = Config.getVerCode(this, packageName);
		String verName = Config.getVerName(this, packageName);
		StringBuffer sb = new StringBuffer();
		sb.append("当前版本:");
		sb.append(verName);
		sb.append(" Code:");
		sb.append(verCode);
		sb.append(", 发现新版本:");
		sb.append(info.name);
		sb.append(" Code:");
		sb.append(info.code);
		sb.append(", 是否更新?");
		Dialog dialog = new AlertDialog.Builder(WebActivity.this)
				.setTitle("软件更新")
				.setMessage(sb.toString())
				// 设置内容
				.setPositiveButton("更新",// 设置确定按钮
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								pBar = new ProgressDialog(WebActivity.this);
								pBar.setCanceledOnTouchOutside(false);
								pBar.setTitle("正在下载");
								pBar.setMessage("请稍候...");
								pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
								downFile(BaseService.ApkUrl);
							}
						})
				.setNegativeButton("暂不更新", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// 点击"取消"按钮之后退出程序
						//finish();
						//loginResult();
					}
				}).create();// 创建
		// 显示对话框
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}

	private void downFile(final String url) {
		pBar.show();
		new Thread() {
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(url);
				HttpResponse response;
				try {
					response = client.execute(get);
					HttpEntity entity = response.getEntity();
					long length = entity.getContentLength();
					InputStream is = entity.getContent();
					FileOutputStream fileOutputStream = null;
					if (is != null) {
						System.out
								.println("Environment.getExternalStorageDirectory()==="
										+ Environment
												.getExternalStorageDirectory());
						File file = new File(
								Environment.getExternalStorageDirectory(),
								Config.UPDATE_APKNAME);
						fileOutputStream = new FileOutputStream(file);

						byte[] buf = new byte[1024];
						int ch = -1;
						int count = 0;
						while ((ch = is.read(buf)) != -1) {
							fileOutputStream.write(buf, 0, ch);
							count += ch;
							if (length > 0) {
								progress = (int) (((float) count / length) * 100);
								handler.sendEmptyMessage(DOWN_UPDATE);
							}
						}

					}
					fileOutputStream.flush();
					if (fileOutputStream != null) {
						fileOutputStream.close();
					}
					down();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}.start();

	}

	private void down() {
		handler.post(new Runnable() {
			public void run() {
				pBar.cancel();
				update();
			}
		});

	}

	private void update() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), Config.UPDATE_APKNAME)),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}

	private void startAct(Class<?> c) {
		Intent intent = new Intent();
		intent.setClass(WebActivity.this, c);
		startActivity(intent);
		this.finish();
		overridePendingTransition(R.anim.right_in, R.anim.left_out);
	}

	
}
