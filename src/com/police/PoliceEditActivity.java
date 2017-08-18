package com.police;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;























import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.police.util.AnimateFirstDisplayListener;
import com.police.util.BaseService;
import com.police.util.EditcontentDialog;
import com.police.util.NumKeyBoardPopuWindow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

@SuppressLint("NewApi")
public class PoliceEditActivity extends Activity implements OnClickListener,NumKeyBoardPopuWindow.OnConfirmListener{

	private EditText edit_num;
	private EditText edit_name;
	private EditText edit_cotent;
	private TextView text_name;
	private TextView text_num;
	private TextView text_cotent;
	private int flag = 0;
	private boolean focusflag=false;
	private OnEditorActionListener EditorListener1;
	private OnEditorActionListener EditorListener2;
	private OnClickListener clicklistener1;
	private OnClickListener clicklistener2;
	private OnClickListener clicklistener3;
	private EditcontentDialog editcontentdialog;
	private DialogInterface.OnClickListener confirmclickListener;
	private DialogInterface.OnClickListener resetclickListener;
	private Button btn_last;
	private Button btn_shuom;
	private Button btn_wancheng;
	private String avatar_url;
	private String name;
	private String position;
	private int userid;
	private ImageView imgview_avatar;
	private TextView textview_name;
	private TextView textview_position;
	
	private ImageLoader loader;
	private  DisplayImageOptions options;
	private  NumKeyBoardPopuWindow mNumKeyboard;
	private ProgressDialog progressDialog;
	
	private TextView tishi_1;
	private TextView tishi_2;
	private TextView tishi_3;
	private TextView tishi_4;
	
	private ImageView tishiimg_1;
	private ImageView tishiimg_2;
	private ImageView tishiimg_3;
	private ImageView tishiimg_4;
	private static int count = 0;
	private boolean touchflag=false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.ic_stub)			// 
		.showImageForEmptyUri(R.drawable.ic_empty)	// 
		.showImageOnFail(R.drawable.ic_empty)		// 
		.cacheInMemory(true)						// 
		.cacheOnDisc(true)							// 
		.displayer(new RoundedBitmapDisplayer(0))	// 
		.build();
		loader = ImageLoader.getInstance();
		setContentView(R.layout.policeedit);
		initView();

	}
	
	
	private void initView(){
		EditorListener1 = new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView arg0, int actionId, KeyEvent arg2) {
				// TODO Auto-generated method stub
				  boolean isOK = true;
			        switch (actionId) {
			            case EditorInfo.IME_ACTION_NEXT:
			            	clearTimer();
			            	hideSoftKeyboard();
			            	if(flag==0) gotoFillContent();
			            	else        gotoConfirmInfo();
			                break;
			            default:
			                isOK = false;
			                break;
			        }
				return isOK;
			}
		};
		EditorListener2 = new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView arg0, int actionId, KeyEvent arg2) {
				// TODO Auto-generated method stub
				  boolean isOK = true;
			        switch (actionId) {
			            case EditorInfo.IME_ACTION_NEXT:
			            	clearTimer();
			            	hideSoftKeyboard();
			            	gotoConfirmInfo();
			                break;
			            default:
			                isOK = false;
			                break;
			        }
				return isOK;
			}
		};
		
		clicklistener1 = new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				clearTimer();
				gotoFillPhone();
				if(editcontentdialog!=null) editcontentdialog.dismiss();
			}
		};
		
		clicklistener2 = new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				clearTimer();
				gotoFillName();
				if(editcontentdialog!=null) editcontentdialog.dismiss();
			}
		};
		
		clicklistener3 = new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				clearTimer();
				gotoFillContent();
				if(editcontentdialog!=null) editcontentdialog.dismiss();
			}
		};
		
		confirmclickListener = new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				clearTimer();
				if(editcontentdialog!=null) editcontentdialog.dismiss();
				String name = edit_name.getText().toString();
				String phone = edit_num.getText().toString();
				String content = edit_cotent.getText().toString();
				
				submitInfo(userid,name,phone,content);
			}
		};
		
		resetclickListener = new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				clearTimer();
				if(editcontentdialog!=null) editcontentdialog.dismiss();
				flag=0;
				edit_num.setText("");
				edit_name.setText("");
				edit_cotent.setText("");
				gotoFillPhone();
				resetlines();
			}
		};		
		avatar_url = this.getIntent().getStringExtra("url");
		name = this.getIntent().getStringExtra("name");
		position=this.getIntent().getStringExtra("position");
		userid = this.getIntent().getIntExtra("id", 0);
		btn_wancheng = (Button)findViewById(R.id.wancheng);
		imgview_avatar =(ImageView)findViewById(R.id.police_avatar);
		textview_name = (TextView)findViewById(R.id.po_name);
		textview_position = (TextView)findViewById(R.id.po_position);
		loader.displayImage(BaseService.avatvarUrl+avatar_url, imgview_avatar, options, new AnimateFirstDisplayListener());
		textview_name.setText("姓名:"+name);
		textview_position.setText("职位:"+position);
		edit_num = (EditText)findViewById(R.id.phone_num);
		edit_name = (EditText)findViewById(R.id.name);
		edit_cotent = (EditText)findViewById(R.id.content);
		text_name = (TextView)findViewById(R.id.txt_name);
		text_num = (TextView)findViewById(R.id.txt_phone_num);
		text_cotent = (TextView)findViewById(R.id.txt_content);
		btn_last  = (Button)findViewById(R.id.last);
		btn_shuom = (Button)findViewById(R.id.tishi);
		tishi_1 = (TextView)findViewById(R.id.tishi_1);
		tishi_2 = (TextView)findViewById(R.id.tishi_2);
		tishi_3 = (TextView)findViewById(R.id.tishi_3);
		tishi_4 = (TextView)findViewById(R.id.tishi_4);
		tishiimg_1 = (ImageView)findViewById(R.id.tishiimg_1);
		tishiimg_2 = (ImageView)findViewById(R.id.tishiimg_2);
		tishiimg_3 = (ImageView)findViewById(R.id.tishiimg_3);
		tishiimg_4 = (ImageView)findViewById(R.id.tishiimg_4);
		
		
		edit_num.setOnClickListener(this);
		btn_last.setOnClickListener(this);
		btn_shuom.setOnClickListener(this);
		btn_wancheng.setOnClickListener(this);
		edit_name.setOnEditorActionListener(EditorListener1);
		edit_cotent.setOnEditorActionListener(EditorListener2);
		text_name.setVisibility(View.GONE);
		edit_name.setVisibility(View.GONE);
		text_cotent.setVisibility(View.GONE);
		edit_cotent.setVisibility(View.GONE);
	
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
	 // TODO Auto-generated method stub
	 super.onWindowFocusChanged(hasFocus);
	 if(hasFocus&&!focusflag){
		 focusflag = true;
			mNumKeyboard = new NumKeyBoardPopuWindow(PoliceEditActivity.this,LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT,this);
			mNumKeyboard.setEditText(edit_num);
			mNumKeyboard.showAtLocation((View) edit_num.getParent(), Gravity.BOTTOM, 0, 0);
	 }
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0.getId()==R.id.phone_num){
			mNumKeyboard = new NumKeyBoardPopuWindow(PoliceEditActivity.this,LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT,this);
			mNumKeyboard.setEditText(edit_num);
			mNumKeyboard.showAtLocation((View) arg0.getParent(), Gravity.BOTTOM, 0, 0);
		}
		else if(arg0.getId()==R.id.tishi){
			hideSoftKeyboard();
			gototishiactivity();
		}
		else if(arg0.getId()==R.id.last){
			hideSoftKeyboard();
			finish();
		}
		else if(arg0.getId()==R.id.wancheng){
			gotoConfirmInfo();
		}
	}


	@Override
	public void confirm() {
		// TODO Auto-generated method stub
		clearTimer();
		if(flag==0) gotoFillName();
		else        gotoConfirmInfo();
	}
	
	
	private void gotoFillPhone(){
		enabletishi1();
		text_name.setVisibility(View.GONE);
		edit_name.setVisibility(View.GONE);
		text_num.setVisibility(View.VISIBLE);
		edit_num.setVisibility(View.VISIBLE);
		text_cotent.setVisibility(View.GONE);
		edit_cotent.setVisibility(View.GONE);
		btn_wancheng.setVisibility(View.GONE);
		mNumKeyboard = new NumKeyBoardPopuWindow(PoliceEditActivity.this,LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT,this);
		mNumKeyboard.setEditText(edit_num);
		mNumKeyboard.showAtLocation((View) edit_num.getParent(), Gravity.BOTTOM, 0, 0);
	}
	
	private void gotoFillName(){
		enabletishi2();
		text_name.setVisibility(View.VISIBLE);
		edit_name.setVisibility(View.VISIBLE);
		text_num.setVisibility(View.GONE);
		edit_num.setVisibility(View.GONE);
		text_cotent.setVisibility(View.GONE);
		edit_cotent.setVisibility(View.GONE);
		btn_wancheng.setVisibility(View.GONE);
		edit_name.requestFocus();
//		InputMethodManager inputManager =    
//                (InputMethodManager)edit_name.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);    
//    inputManager.showSoftInput(edit_name, 0);
		
		Timer timer = new Timer();    
        timer.schedule(new TimerTask()    
        {    
            public void run()     
            {    
                InputMethodManager inputManager =    
                    (InputMethodManager)edit_name.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);    
                inputManager.showSoftInput(edit_name, 0);    
            }    
        },      
            300);  
	}
	
	private void gotoFillContent(){
		enabletishi3();
		text_name.setVisibility(View.GONE);
		edit_name.setVisibility(View.GONE);
		text_num.setVisibility(View.GONE);
		edit_num.setVisibility(View.GONE);
		text_cotent.setVisibility(View.VISIBLE);
		edit_cotent.setVisibility(View.VISIBLE);
		btn_wancheng.setVisibility(View.VISIBLE);
		edit_cotent.requestFocus();
//		InputMethodManager inputManager =    
//                (InputMethodManager)edit_cotent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);    
//    inputManager.showSoftInput(edit_cotent, 0);
		Timer timer = new Timer();    
        timer.schedule(new TimerTask()    
        {    
            public void run()     
            {    
                InputMethodManager inputManager =    
                    (InputMethodManager)edit_cotent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);    
                inputManager.showSoftInput(edit_cotent, 0);    
            }    
        },      
           300); 
	}
	
	private void gotoConfirmInfo(){
		enabletishi4();
		flag=1;
		text_name.setVisibility(View.GONE);
		edit_name.setVisibility(View.GONE);
		text_num.setVisibility(View.GONE);
		edit_num.setVisibility(View.GONE);
		text_cotent.setVisibility(View.GONE);
		edit_cotent.setVisibility(View.GONE);
		btn_wancheng.setVisibility(View.GONE);
		EditcontentDialog.Builder buidler = new EditcontentDialog.Builder(this);
		editcontentdialog =  
		buidler.setTitle("确认您的信息")
		       .setName(edit_name.getText().toString())
		       .setPhone(edit_num.getText().toString())
		       .setContent(edit_cotent.getText().toString())
		       .setClickListeners(clicklistener1, clicklistener2, clicklistener3)
		       .setPositiveButton("发送", confirmclickListener)
		       .setNegativeButton("重新填写", resetclickListener)
		       .create();
		editcontentdialog.show();
	}
     
	
    /**
     * hide inputMethod
     */
    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager != null ) {
            View localView = this.getCurrentFocus();
            if(localView != null && localView.getWindowToken() != null ) {
                IBinder windowToken = localView.getWindowToken();
                inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
            }
        }
    }
    
	private void gototishiactivity(){
		clearTimer();
		String url = "file:///android_asset/html/note.html";
		Intent intent = new Intent(this, WebActivity.class);
		intent.putExtra("url", url);
		startActivity(intent);
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
	public void progress(String type, String text) {
		if ("Show".equals(type)) {
			progressDialog = ProgressDialog.show(this, "提示", "请稍后", true,
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
				Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				LinearLayout toastView = (LinearLayout) toast.getView();
				ImageView imageCodeProject = new ImageView(this);
				imageCodeProject.setImageResource(resId);
				toastView.addView(imageCodeProject, 0);
				toast.show();
			}
		}
	}
	
	
    private void submitInfo(final int uid,final String name,final String phone,final String content){
    	progress("Show","请稍后");
    	new Thread() {
			public void run() {
				

					if(BaseService.submitMessage(uid, name,phone,content)==1)
					{
    					handler.sendMessage(handler.obtainMessage(1));		
					}
					else
					{
    					handler.sendMessage(handler.obtainMessage(2));		
					}



				
			}
		}.start();   
    }
    
	private  Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
		    	progress("Success","发送成功");
		    	finish();
				break;
			case 2:
				progress("Error","发送失败");
				EditcontentDialog.Builder buidler = new EditcontentDialog.Builder(PoliceEditActivity.this);
				editcontentdialog =  
						buidler.setTitle("确认您的信息")
						       .setName(edit_name.getText().toString())
						       .setPhone(edit_num.getText().toString())
						       .setContent(edit_cotent.getText().toString())
						       .setClickListeners(clicklistener1, clicklistener2, clicklistener3)
						       .setPositiveButton("发送", confirmclickListener)
						       .setNegativeButton("重新填写", resetclickListener)
						       .create();
						editcontentdialog.show();
				break;
			case 3:
				hideSoftKeyboard();
				finish();
				break;
			}
		}
	};
    
	private void resettishi(){
		tishi_1.setTextColor(getResources().getColor(R.color.buzhou_disable));
		tishi_1.setBackground(getResources().getDrawable(R.drawable.huise));
		tishi_2.setTextColor(getResources().getColor(R.color.buzhou_disable));
		tishi_2.setBackground(getResources().getDrawable(R.drawable.huise));
		tishi_3.setTextColor(getResources().getColor(R.color.buzhou_disable));
		tishi_3.setBackground(getResources().getDrawable(R.drawable.huise));
		tishi_4.setTextColor(getResources().getColor(R.color.buzhou_disable));
		tishi_4.setBackground(getResources().getDrawable(R.drawable.huise));
	}
	
	
	private void enabletishi1(){
		resettishi();
		tishi_1.setTextColor(getResources().getColor(R.color.buzhou_enable));
		tishiimg_1.setBackground(getResources().getDrawable(R.drawable.lan_huanchong));
		tishi_1.setBackground(getResources().getDrawable(R.drawable.bai));
	}
	
	private void enabletishi2(){
		resettishi();
		tishi_2.setTextColor(getResources().getColor(R.color.buzhou_enable));
		tishiimg_2.setBackground(getResources().getDrawable(R.drawable.lan_huanchong));
		tishi_2.setBackground(getResources().getDrawable(R.drawable.bai));
	}
	
	private void enabletishi3(){
		resettishi();
		tishi_3.setTextColor(getResources().getColor(R.color.buzhou_enable));
		tishiimg_3.setBackground(getResources().getDrawable(R.drawable.lan_huanchong));
		tishi_3.setBackground(getResources().getDrawable(R.drawable.bai));
	}
	
	private void enabletishi4(){
		resettishi();
		tishi_4.setTextColor(getResources().getColor(R.color.buzhou_enable));
		tishiimg_4.setBackground(getResources().getDrawable(R.drawable.lan_huanchong));
		tishi_4.setBackground(getResources().getDrawable(R.drawable.bai));
	}
	
	private void resetlines(){
		tishiimg_1.setBackground(getResources().getDrawable(R.drawable.lan_huanchong));
		tishiimg_2.setBackground(getResources().getDrawable(R.drawable.hui_huanchong));
		tishiimg_3.setBackground(getResources().getDrawable(R.drawable.hui_huanchong));
		tishiimg_4.setBackground(getResources().getDrawable(R.drawable.hui_huanchong));
	}
	
	
	
    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		touchflag = true;
	}

    

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		startTimer(); //启动定时器
	}


	private void startTimer(){
		touchflag = false;
		count=0;
    	new Thread() {
			public void run() {
				//5分钟不操作点击事件就会退出界面
				while(!touchflag){
				       try {
							Thread.sleep(1000);
							count++;
								if(count>=300){
									count = 0;
									touchflag=true;
									handler.sendMessage(handler.obtainMessage(3));	
								}
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				}



				
			}
		}.start();   
    }
	private void clearTimer(){
		count=0;
	}
}
