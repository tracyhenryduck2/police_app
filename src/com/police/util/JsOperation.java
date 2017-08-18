package com.police.util;

import java.util.List;
import com.police.InitActivity;
import com.police.R;
import com.police.WebActivity;
import com.police.util.Session.Item;


import cn.jpush.android.api.JPushInterface;





import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.animation.Animation;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class JsOperation {
	private Context context = null;
	private WebView webView = null;
	private String parm = null;
	private ProgressDialog progressDialog = null;
	private String ds =null;

	public JsOperation(Context context, WebView webView) {
		this.context = context;
		this.webView = webView;
	}

	public void setParm(String parm) {
		this.parm = parm;
	}

	public String getParm() {
		return parm;
	}

	public void showMsg(String str) {
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 
	 * @param type
	 *            ;Show,Dismiss,Success,Error
	 * @param msg
	 *            :提示信息
	 * @param method
	 *            :回调函数
	 */
	public void progress(String type, String text, String method) {
		if ("Show".equals(type)) {
			progressDialog = ProgressDialog.show(context, "提示", "请稍后", true,
					true);
			progressDialog.setCanceledOnTouchOutside(false);
		} else {
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
			if (!"Dismiss".equals(type)) {
				int resId = R.drawable.success;
				if ("Error".equals(type)) {
					resId = R.drawable.error;
				}
				Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				LinearLayout toastView = (LinearLayout) toast.getView();
				ImageView imageCodeProject = new ImageView(context);
				imageCodeProject.setImageResource(resId);
				toastView.addView(imageCodeProject, 0);
				toast.show();
			}
		}
		if (webView != null && method != null && method.length() > 0) {
			webView.loadUrl("javascript:" + method);
		}
	}

	public void confirm(String title, String text, final String method) {
		Dialog dialog = new AlertDialog.Builder(context)
				.setTitle(title)
				.setMessage(text)
				// 设置内容
				.setPositiveButton("确定",// 设置确定按钮
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (webView != null) {
									webView.loadUrl("javascript:" + method);
								}
							}
						})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// 点击"取消"按钮之后退出程序
					}
				}).create();// 创建
		// 显示对话框
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}

	public String getIpPort() {
		String result = BaseService.ServerPath;
		return result;
	}

	public String getUserJson() {
		String result = Session.getUserJson();
//		showMsg(result);
		return result;
	}

	public String getItemList(int groupId) {
		String result = Session.getItemList(groupId);
		System.out.println(result);
		return result;
	}





	public void open(String url, int blank) {
		url = "file:///android_asset/html/" + url;
		if (blank == 0) {
			if (context instanceof WebActivity) {
				openUrl(url);
			}
		} else {
			Intent intent = new Intent(context, WebActivity.class);
			intent.putExtra("url", url);
			startActivity(intent);
		}
	}

	private void openUrl(String url) {
		int index = 0;
		if ((index = url.indexOf('?')) > 0) {
			setParm(url.substring(index));
			webView.loadUrl(url.substring(0, index));
		} else {
			webView.loadUrl(url);
		}
	}

	public void logout() {
		if (context instanceof Activity) {
			Activity a = (Activity) context;
			a.finish();
		}
	}

	public void call(String uri) {
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));
		startActivity(intent);
	}
	
	   public void sendSMS(String phoneNumber){
           Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(phoneNumber));                
           startActivity(intent);
   }
	
	public void goBack() {
		if (context instanceof WebActivity) {
			((WebActivity) context).goBack();
		}
	}

	
	private void startActivity(Intent intent) {
		if (context instanceof Activity) {
			Activity a = (Activity) context;
			a.startActivity(intent);
			a.overridePendingTransition(Animation.INFINITE, Animation.INFINITE);
		} else {
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}
	}

	public String getPushStatus() {
		ServiceForAccount account = new ServiceForAccount(context);
		return account.getValueByKey(ServiceForAccount.KEY_PUSH);
	}

	public void setPushStatus(String status) {
		ServiceForAccount account = new ServiceForAccount(context);
		account.saveKeyValue(ServiceForAccount.KEY_PUSH, status);
		if ("0".equals(status)) {
			JPushInterface.stopPush(context);
		} else {
			JPushInterface.resumePush(context);
		}
	}

	public String getVerName() {
		return Config.getVerName(context, InitActivity.packageName);
	}

	

	
	public void saveGlobalInfo(String key,String value){

			BaseService.saveGlobalInfo(key,value,context);

	}
	public String readGlobalInfo(String key){
		
		String value = null;

			value=BaseService.readGlobalInfo(key,context);	
		return value;
	}
	
	public void saveNotGlobalInfo(String key,String value){

    	   BaseService.saveNotGlobalInfo(key,value);

	}
	
	public String readNotGlobalInfo(String key){
		String value = null;
			value=BaseService.readNotGlobalInfo(key);
		return value;
	}
	
	/**
	 * 
	 * @param type
	 * 打开dialog接口，
	 * dialog类型:1.nickname;
	 *           2.sex;
	 *           3.age;
	 */
	
	public void showDialog(final int type,String title,String message,final String method){
		final CustomDialog.Builder builder = new CustomDialog.Builder(this.context);
		builder.setTitle(title);
		switch(type)
		{
		case 1:
			builder.setNickMessage(message);
			break;
		case 2:
			builder.setSex(Integer.parseInt(message));
			break;
		case 3:
			builder.setAgeMessage(message);
			break;
		case 4:
			builder.setTakePhoto(new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int arg1) {
					// TODO Auto-generated method stub
		     		if (context instanceof WebActivity) {
		     			dialog.dismiss();
		    		}
				}
			}).setGetImage(new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int arg1) {
					// TODO Auto-generated method stub
		     		if (context instanceof WebActivity) {
		     			dialog.dismiss();
		    		}
				}
			});
			break;
		case 5:
			break;
		}	
		
		if(type!=4)
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				
				if(type==1&&(builder.getNickMessage()==null || builder.getNickMessage().equals("")))
				{
					showMsg("请输入内容");
				} else if(type==3&&(builder.getAgeMessage()==null ||builder.getAgeMessage().equals("")))
				{
					showMsg("请输入内容");
				}else if(type==3&&!builder.getAgeMessage().matches("[0-9]+"))
				{
					showMsg("年龄必须为数字");
				}
				else{
					dialog.dismiss();
					if (webView != null && method != null && method.length() > 0) {

						switch(type)
						{
						case 1:
							ds = builder.getNickMessage();
							break;
						case 2:
							ds = String.valueOf(builder.getSex());
							break;
						case 3:
							ds = builder.getAgeMessage();
							break;
						}
	           	           webView.post(new Runnable() {
	          	             @Override
	          	             public void run() {
	          	   			webView.loadUrl("javascript:" + method+"("+1+",'"+ds+"')");
	          	             }
	          	         });
			
					}
				} 
		
			}
		});
		
		if(type!=4)
		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builder.create(type).show();
	}
	
	public String getDepartmentInfo(int depid){
		if(!BaseService.deptInfoMap.isEmpty())
		  return	BaseService.deptInfoMap.get(depid);
		else
		  return   null;
		
		
	}
	
	public String getAllDepartment(){
		if(BaseService.alldepartmentresult!=null)
		  return	BaseService.alldepartmentresult;
		else
		  return   null;
		
		
	}
	
	public String getAllPolice(){
		if(BaseService.allpoliceresult!=null)
		  return	BaseService.allpoliceresult;
		else
		  return   null;
		
		
	}

	
	public void LogTofile(String filename,String content){
		FileUpload fs = new FileUpload();
		fs.SaveContentToFile(filename, content);

	}
}
