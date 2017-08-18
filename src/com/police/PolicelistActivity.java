package com.police;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.police.util.AnimateFirstDisplayListener;
import com.police.util.BaseService;
import com.police.util.CustomDialog;
import com.police.util.JsOperation;
import com.police.util.PoliceBean;

























import com.police.util.BaseService.UpdateInfo;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PolicelistActivity extends Activity implements OnClickListener{
	private final int Max_list = 7;
	private ViewPager pager;
	private ListView deparmentlistview;
	private departmentAdapter departmentadapter;
	private ViewPagerAdapter pagerAdapter;
	private List<GridView> gridviewlist  = new ArrayList<GridView>();
	private List<PoliceBean> policebeanlist = new ArrayList<PoliceBean>();
	private int currentpage_index = 0;
	private int page_num = 0;
	private List<Map<String,Object>> departmentlist=new ArrayList<Map<String,Object>>();
	private int list_num = 0;
    private int currentlist_index=0;
    private Button btn_lastlist;
    private Button btn_nextlist;
    private Button btn_tishi;
    private Button btn_logout;
    private TextView txt_pageinfo;
	private ImageLoader loader;
	private  DisplayImageOptions options;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.policelist);
		initView();
		try {
			initdata();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setGridViewList(0);
		setListViewList(0);
		txt_pageinfo.setText((currentpage_index+1)+"/"+page_num);
	}
	
	private void initdata() throws JSONException{
        JSONArray ar = new JSONArray(BaseService.allpoliceJSON);
        
		departmentlist.removeAll(departmentlist);
		for(int i=0;i<ar.length();i++){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("dep", ar.getJSONObject(i).getString("deptName"));
			List<PoliceBean> list = new ArrayList<PoliceBean>();
			for(int j=0;j<ar.getJSONObject(i).getJSONArray("policeList").length();j++)
			{
				int id =ar.getJSONObject(i).getJSONArray("policeList").getJSONObject(j).getInt("id");
				String name = ar.getJSONObject(i).getJSONArray("policeList").getJSONObject(j).getString("name");
				String position =ar.getJSONObject(i).getJSONArray("policeList").getJSONObject(j).getString("position");
				String phone = ar.getJSONObject(i).getJSONArray("policeList").getJSONObject(j).getString("phone");
				String avatarurl =ar.getJSONObject(i).getJSONArray("policeList").getJSONObject(j).getString("image");
				PoliceBean bean = new PoliceBean(id);
				bean.setAvatarurl(avatarurl);
				bean.setPhone(phone);
				bean.setPosition(position);
				bean.setUserName(name);
				list.add(bean);
			}
			
			map.put("policelist",list);
			
			if(i==0) map.put("current", true);
			else     map.put("current", false);
			departmentlist.add(map);
		}		
		list_num = departmentlist.size()<Max_list?1:(departmentlist.size()-Max_list+1);
		
	}
	
	
	private void updatepolicelist(int index){
		policebeanlist.removeAll(policebeanlist);
		
		if(index!=1)
		{
			@SuppressWarnings("unchecked")
			ArrayList<PoliceBean> list = (ArrayList<PoliceBean>)(departmentlist.get(index).get("policelist"));
			for(int i=0;i<list.size();i++)
			{
				PoliceBean bean = new PoliceBean(list.get(i).getUserId());
				bean.setUserName(list.get(i).getUserName());
				bean.setPosition(list.get(i).getPosition());
				bean.setAvatarurl(list.get(i).getAvatarurl());
				bean.setPhone(list.get(i).getPhone());
				policebeanlist.add(bean);
			}
		}
		else 
		{
						
			@SuppressWarnings("unchecked")
			ArrayList<PoliceBean> list = (ArrayList<PoliceBean>)(departmentlist.get(index).get("policelist"));
			for(int i=0;i<list.size();i++)
			{
				PoliceBean bean2 = new PoliceBean(list.get(i).getUserId());
				bean2.setUserName(list.get(i).getUserName());
				bean2.setPosition(list.get(i).getPosition());
				bean2.setAvatarurl(list.get(i).getAvatarurl());
				bean2.setPhone(list.get(i).getPhone());
				policebeanlist.add(bean2);
			}
			
			PoliceBean bean = new PoliceBean(1);
			bean.setUserName("@position");
			bean.setPosition("@position");
			bean.setAvatarurl("@position");
			bean.setPhone("@position");
			policebeanlist.add(0,bean);
			policebeanlist.add(3, bean);
			
		}
	}
	
	
	private void setViewPager(){
		pagerAdapter = new ViewPagerAdapter(gridviewlist);
		
		pager.setAdapter(pagerAdapter);
		pager.setCurrentItem(0);
	}
    // 设置部门点击事件
    private final AdapterView.OnItemClickListener ondepartmentItemClickListener
            = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {

        	for(int i=0;i<departmentlist.size();i++)
        	{
	        	departmentlist.get(i).put("current", false);
        	}

            Map<String,Object> contacts = departmentadapter.getItem(position);
            contacts.put("current", true);
            setListViewList(currentlist_index);
            
            setGridViewList(position+currentlist_index);
			txt_pageinfo.setText((currentpage_index+1)+"/"+page_num);
        }
    };
	
	

    
	private void initView(){
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.ic_stub)			// 
		.showImageForEmptyUri(R.drawable.ic_empty)	// 
		.showImageOnFail(R.drawable.ic_empty)		// 
		.cacheInMemory(true)						// 
		.cacheOnDisc(true)							// 
		.displayer(new RoundedBitmapDisplayer(0))	// 
		.build();
		loader = ImageLoader.getInstance();
		
		pager = (ViewPager)findViewById(R.id.vPager);
		
        pager.setOnPageChangeListener(new MyOnPageChangeListener());
		deparmentlistview =(ListView)findViewById(R.id.department);
		txt_pageinfo = (TextView)findViewById(R.id.pageinfo);
		btn_lastlist =(Button)findViewById(R.id.last_list);
		btn_nextlist =(Button)findViewById(R.id.next_list);
		btn_tishi =(Button)findViewById(R.id.tishi);
		btn_logout =(Button)findViewById(R.id.logout);
		btn_tishi.setOnClickListener(this);
		btn_logout.setOnClickListener(this);
		btn_lastlist.setOnClickListener(this);
		btn_nextlist.setOnClickListener(this);
		departmentadapter = new departmentAdapter(this);
		deparmentlistview.setAdapter(departmentadapter);
		deparmentlistview.setOnItemClickListener(ondepartmentItemClickListener);
	}
	
	
	private void setListViewList(int index){
		if(departmentlist.size()>Max_list)
		{

			ArrayList<Map<String, Object>> da = new ArrayList<Map<String,Object>>();
			for(int i=0;i<Max_list;i++)
			{
				da.add(departmentlist.get(i+index));
			}
			departmentadapter.setData(da);
			if(index==0) 
			{
				btn_lastlist.setVisibility(View.GONE);
			btn_nextlist.setVisibility(View.VISIBLE);
			}
			else if(index==list_num-1){
				btn_lastlist.setVisibility(View.VISIBLE);
				btn_nextlist.setVisibility(View.GONE);
			}
		}
		else
		{
			list_num = 1;
			departmentadapter.setData(departmentlist);
			btn_lastlist.setVisibility(View.GONE);
			btn_nextlist.setVisibility(View.GONE);
		}
	}
	
	private void setGridViewList(int index){
		gridviewlist.removeAll(gridviewlist);
		updatepolicelist(index);
		page_num = (policebeanlist.size()%8==0)?(policebeanlist.size()/8):(policebeanlist.size()/8+1);
		currentpage_index = 0;
		  for(int i=0;i<page_num;i++)
		  {   
			  int n = 8;
			  GridView GV = new GridView(this);
			  GV.setNumColumns(4);
			  GV.setSelector(new ColorDrawable(Color.TRANSPARENT));
              if(i<page_num-1)
              {
            	  n =8;
              }
              else
              {
            	  
            	  n = policebeanlist.size()%8==0?8:policebeanlist.size()%8;
              }
              List<PoliceBean> newList=new ArrayList<PoliceBean>();
              for(int j=0;j<n;j++)
              {
            	  newList.add(policebeanlist.get(i*8+j));  
              }
			  final PoliceAdapter adapter = new PoliceAdapter(this);
              adapter.setData(newList);
              GV.setAdapter(adapter);
              GV.setHorizontalSpacing(100);
              GV.setPadding(0, 0, 0, 100);
              GV.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					PoliceBean policebean = adapter.getItem(position);
					if(!policebean.getUserName().equals("@position"))
					{
						gotoeditactivity(policebean);
					}
					
					
				}
            	  
            	  
			});
              gridviewlist.add(GV);
		  }

			setViewPager();
		  
	}
	
	
	
	
	  public class ViewPagerAdapter extends PagerAdapter {  
	      
	        List<GridView> viewLists;  
	          
	        public ViewPagerAdapter(List<GridView> lists)  
	        {  
	            viewLists = lists;  
	        }  
	      
	        //获得size  
	        @Override  
	        public int getCount() {   
	            return viewLists.size();  
	        }  
	      
	        
	       
	        
	        

			@Override  
	        public boolean isViewFromObject(View arg0, Object arg1) {                           
	            return arg0 == arg1;  
	        }  
	          
	        //销毁Item  
	        @Override  
	        public void destroyItem(View view, int position, Object object)  
	        {  
	        	if(position<viewLists.size()-1)
	            ((ViewPager) view).removeView(viewLists.get(position));  
	            
	  
	        }  
	          
	        //实例化Item  
	        @Override  
	        public Object instantiateItem(View view, int position)  
	        {  
	        	((ViewPager)view).removeView(viewLists.get(position));
	            ((ViewPager)view).addView(viewLists.get(position));
	            //给每个item的view 就是刚才views存放着的view
	            return viewLists.get(position);
	        }  
	    } 
	
	    class PoliceAdapter extends ArrayAdapter<PoliceBean> {
	        Context mContext;

	        public PoliceAdapter(Context context) {
	            super(context, 0);
	            mContext = context;
	        }


	        public void setData(List<PoliceBean> data) {
	            clear();
	            setNotifyOnChange(true);
	            if (data != null) {
	                for (PoliceBean appEntry : data) {
	                    add(appEntry);
	                }
	            }
	        }

	        @Override
	        public View getView(int position, View convertView, ViewGroup parent) {

	            View view ;
	            ViewHolder mViewHolder;
	            if(convertView == null || convertView.getTag() == null) {
	                view = View.inflate(mContext, R.layout.police_item, null);

	                mViewHolder = new ViewHolder();
	                mViewHolder.mAvatar = (ImageView) view.findViewById(R.id.police_avatar);
	                mViewHolder.nametxt = (TextView) view.findViewById(R.id.police_name);
	                mViewHolder.positiontxt = (TextView)view.findViewById(R.id.police_position);
	                view.setTag(mViewHolder);
	            } else {
	                view = convertView;
	                mViewHolder = (ViewHolder) view.getTag();
	            }

	           final  PoliceBean contacts = getItem(position);
	            if(contacts != null) {
	            	

	            	mViewHolder.nametxt.setText("姓名:"+contacts.getUserName());
	            	mViewHolder.positiontxt.setText("职位:"+contacts.getPosition());
					loader.displayImage(BaseService.avatvarUrl+contacts.getAvatarurl(), mViewHolder.mAvatar, options, new AnimateFirstDisplayListener());
					
	            	if(contacts.getUserName().equals("@position"))
	            	{
	            		mViewHolder.nametxt.setVisibility(View.INVISIBLE);
	            		mViewHolder.positiontxt.setVisibility(View.INVISIBLE);
	            		mViewHolder.mAvatar.setVisibility(View.INVISIBLE);
	            	}
					

	            }

	            return view;
	        }




	        class ViewHolder {
	            /**头像*/
	            ImageView mAvatar;
	            /**名字：*/
	            TextView nametxt;
	            /**职位*/
	            TextView positiontxt;
	        }


	    }
	    

	    
	    
	    class departmentAdapter extends ArrayAdapter<Map<String,Object>> {
	        Context mContext;

	        public departmentAdapter(Context context) {
	            super(context, 0);
	            mContext = context;
	        }


	        public void setData(List<Map<String,Object>> data) {
	            clear();
	            setNotifyOnChange(true);
	            if (data != null) {
	                for (Map<String,Object> appEntry : data) {
	                    add(appEntry);
	                }
	            }
	        }

	        @Override
	        public View getView(int position, View convertView, ViewGroup parent) {

	            View view ;
	            ViewHolder mViewHolder;
	            if(convertView == null || convertView.getTag() == null) {
	                view = View.inflate(mContext, R.layout.deparment_item, null);

	                mViewHolder = new ViewHolder();
	                mViewHolder.department = (Button) view.findViewById(R.id.department_item);
	                view.setTag(mViewHolder);
	            } else {
	                view = convertView;
	                mViewHolder = (ViewHolder) view.getTag();
	            }

	           final  Map<String,Object> contacts = getItem(position);
	            if(contacts != null) {
	            	mViewHolder.department.setText(contacts.get("dep")+"");
	                boolean flag = Boolean.parseBoolean(contacts.get("current")+"");
	                if(flag) mViewHolder.department.setTextColor(getResources().getColor(R.color.red));
	                else     mViewHolder.department.setTextColor(getResources().getColor(R.color.black));

	            }

	            return view;
	        }




	        class ViewHolder {
	            /**部门名称*/
	            Button department;
	        }


	    }

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(arg0.getId()==R.id.next_list)
			{
				nextlist();
			}
			else if(arg0.getId()==R.id.last_list)
			{
				lastlist();
			}
			else if(arg0.getId()==R.id.tishi){
				gototishiactivity();
			}
			else if(arg0.getId()==R.id.logout){
				showDialog();
			}
		}
		
		private void nextlist(){
			currentlist_index++;
			setListViewList(currentlist_index);
		}
		
		private void lastlist(){
			currentlist_index--;
			setListViewList(currentlist_index);
		}
		
		private void gotoeditactivity(PoliceBean bean){
			//String url = "file:///android_asset/html/policeedit.html?";
			//String url_where ="id="+bean.getUserId()+"&image="+bean.getAvatarurl()+"&phone="+bean.getPhone()+"&name="+bean.getUserName()+"&position="+bean.getPosition();
			Intent intent = new Intent(this, PoliceEditActivity.class);
			intent.putExtra("url", bean.getAvatarurl());
			intent.putExtra("name", bean.getUserName());
			intent.putExtra("position", bean.getPosition());
			intent.putExtra("id", bean.getUserId());
			intent.putExtra("phone", bean.getPhone());
			startActivity(intent);
		}
		
		private void gototishiactivity(){
			String url = "file:///android_asset/html/note.html";
			Intent intent = new Intent(this, WebActivity.class);
			intent.putExtra("url", url);
			startActivity(intent);
		}
		
		
		/**
		 * 
		 * @param type
		 * 打开dialog接口，
		 * dialog类型:1.nickname;
		 *           2.sex;
		 *           3.age;
		 */
		
		public void showDialog(){
			final CustomDialog.Builder builder = new CustomDialog.Builder(this);
			builder.setTitle("提示");
	
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					
					if(builder.getNickMessage()==null || builder.getNickMessage().equals(""))
					{
						Toast.makeText(PolicelistActivity.this, "请输入内容", Toast.LENGTH_LONG).show();
					} 
					else{
						dialog.dismiss();
						logout(builder.getNickMessage());
					} 
			
				}
			});
			
			builder.setNegativeButton("取消",
					new android.content.DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});

			builder.create(1).show();
		}
		
		
		private void logout(final String code) {
			new Thread() {
				public void run() {
                         int result = BaseService.logout(code);
                         if(result==1)
						 handler.sendMessage(handler.obtainMessage(1));
                         else
                         handler.sendMessage(handler.obtainMessage(2));

				}
			}.start();
		}
		
		private Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					Toast.makeText(PolicelistActivity.this, "退出成功", Toast.LENGTH_LONG).show();
					finish();
					break;
				case 2:
					Toast.makeText(PolicelistActivity.this, "退出失败", Toast.LENGTH_LONG).show();
					break;
				}
			}
		};
		
		
		/**
		 * 为选项卡绑定监听器
		 */
		public class MyOnPageChangeListener implements OnPageChangeListener {

	        
			
			
			public void onPageScrollStateChanged(int index) {
			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			public void onPageSelected(int index) {
				currentpage_index = index;
				txt_pageinfo.setText((currentpage_index+1)+"/"+page_num);
				
			}
		}

}
