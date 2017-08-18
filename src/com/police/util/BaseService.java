package com.police.util;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class BaseService {
    
	//public final static String ROOTPath = "111.1.3.197:88";
	//public final static String ROOTPath = "police.ztoas.com:8080";
	public final static String ROOTPath = "192.168.1.103:8080";
	public final static String ServerPath = ROOTPath+"/police";
//	public final static String ServerPath = "192.168.1.110:8080/police";
//	public final static String ServerPath = "192.168.1.28";
//	public final static String ServerPath = "192.168.1.156:8080";
//	public final static String ServerPath = "26.ztoas.com:88";
	public final static String ApkVerUrl = "http://" + "192.168.1.103:8080"
			+ "/app/police.ver";
	public final static String ApkUrl = "http://" + ROOTPath
	+ "/app/police.apk";
	
	public final static String avatvarUrl = "http://" + ROOTPath
	+ "/photo/";
	
	public final static String OAPath = "http://" + ServerPath + "/oa/App";
	
	public final static String departPath = "http://" + ServerPath
			+ "/app/Index!departmentList.action";
	
	public final static String allpolicePath = "http://" + ServerPath
			+ "/app/Index!AllpoliceList.action";
	
	public final static String allpolicetypePath = "http://" + ServerPath
			+ "/app/Index!AllTypedpoliceList.action";
	
	public final static String submitMessagePath = "http://" + ServerPath
			+ "/app/Index!messageSubmit.action";
	
	
	public final static String departpolicePath = "http://" + ServerPath
			+ "/app/Index!policeList.action";
	public final static String logout="http://" + ServerPath
			+ "/app/Index!LoginOut.action";
	
	private static String imagePath=null;
	public static HashMap<String,String> account_map=new HashMap<String, String>();
	
	public static String allpoliceresult;
	public static String alldepartmentresult;
	public static HashMap<Integer,String> deptInfoMap= new HashMap<Integer, String>();
	
	public static String allpoliceJSON="[{\"deptName\": \"一科\",\"policeList\": [{\"id\": 12,\"name\": \"张三\",\"position\": \"民警\",\"phone\": \"13136369541\",\"image\": \"d\"},{\"id\": 12,\"name\": \"李四\",\"position\": \"民警\",\"phone\": \"13136369541\",\"image\": \"d\"},{\"id\": 12,\"name\": \"张三\",\"position\": \"民警\",\"phone\": \"13136369541\",\"image\": \"d\"},{\"id\": 12,\"name\": \"李四\",\"position\": \"民警\",\"phone\": \"13136369541\",\"image\": \"d\"},{\"id\": 12,\"name\": \"张三\",\"position\": \"民警\",\"phone\": \"13136369541\",\"image\": \"d\"},{\"id\": 12,\"name\": \"李四\",\"position\": \"民警\",\"phone\": \"13136369541\",\"image\": \"d\"},{\"id\": 12,\"name\": \"张三\",\"position\": \"民警\",\"phone\": \"13136369541\",\"image\": \"d\"},{\"id\": 12,\"name\": \"李四\",\"position\": \"民警\",\"phone\": \"13136369541\",\"image\": \"d\"}]}"
			+ ",{\"deptName\": \"二科\",\"policeList\": [{\"id\": 12,\"name\": \"张三\",\"position\": \"民警\",\"phone\": \"13136369541\",\"image\": \"d\"},{\"id\": 12,\"name\": \"张三\",\"position\": \"民警\",\"phone\": \"13136369541\",\"image\": \"d\"}]}"
			+ ",{\"deptName\": \"侦查科\",\"policeList\": [{\"id\": 12,\"name\": \"张三\",\"position\": \"民警\",\"phone\": \"13136369541\",\"image\": \"d\"},{\"id\": 12,\"name\": \"张三\",\"position\": \"民警\",\"phone\": \"13136369541\",\"image\": \"d\"}]}"
			+ ",{\"deptName\": \"资料室\",\"policeList\": [{\"id\": 12,\"name\": \"张三\",\"position\": \"民警\",\"phone\": \"13136369541\",\"image\": \"d\"},{\"id\": 12,\"name\": \"张三\",\"position\": \"民警\",\"phone\": \"13136369541\",\"image\": \"d\"}]}]";
	
	public static class UpdateInfo {
		public int code;
		public String name;

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}
	};

	public static UpdateInfo getUpdateInfo() {
		UpdateInfo info = new UpdateInfo();
		try {
			String result = NetworkTool.getContent(ApkVerUrl);
			JSONObject objMap = new JSONObject(result);
			info.code = objMap.getInt("code");
			info.name = objMap.getString("name");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return info;
	}

	public static int login(String user, String pwd,Context context,ServiceForAccount account) {
		try {
			String result = NetworkTool
					.getContent(OAPath + "/cgi!login.action?username=" + user
							+ "&password=" + pwd);
			Log.i("jaja", result);
			JSONObject objMap = new JSONObject(result);
			int status = objMap.getInt("result");
			if (status == 1) {
				JSONObject j_user=objMap.getJSONObject("user");
				Session.User sUser=new Session.User();
				sUser.setId(j_user.getInt("id"));
				sUser.setCode(j_user.getString("code"));
				sUser.setName(j_user.getString("name"));
				sUser.setUsername(j_user.getString("username"));
				sUser.setAttence(j_user.getInt("attence"));
				Session.user=sUser;
				JSONObject j_branch=j_user.getJSONObject("curBranch");
				Session.Branch branch=new Session.Branch();
				branch.setDeptId(j_branch.getInt("deptId"));
				branch.setDeptName(j_branch.getString("deptName"));
				branch.setRoleId(j_branch.getInt("roleId"));
				branch.setRoleName(j_branch.getString("roleName"));
				branch.setUnitId(j_branch.getInt("unitId"));
				branch.setUnitName(j_branch.getString("unitName"));
				branch.setRoleLevel(j_branch.getString("roleLevel"));
				branch.setAncestorId(j_branch.getInt("ancestorId"));
				Session.branch=branch;
				
				//赋值部门信息列表
				JSONArray j_branchlist = j_user.getJSONArray("branchList");
				Session.branchList =  new ArrayList<Session.Branch>();
				Session.itemGroupList = new ArrayList<Session.ItemGroup>();
				for(int i = 0;i<j_branchlist.length();i++)
				{
					Session.Branch branchlistMember=new Session.Branch();
					branchlistMember.setDeptId(j_branchlist.getJSONObject(i).getInt("deptId"));
					branchlistMember.setDeptName(j_branchlist.getJSONObject(i).getString("deptName"));
					branchlistMember.setRoleId(j_branchlist.getJSONObject(i).getInt("roleId"));
					branchlistMember.setRoleName(j_branchlist.getJSONObject(i).getString("roleName"));
					branchlistMember.setUnitId(j_branchlist.getJSONObject(i).getInt("unitId"));
					branchlistMember.setUnitName(j_branchlist.getJSONObject(i).getString("unitName"));
					branchlistMember.setRoleLevel(j_branchlist.getJSONObject(i).getString("roleLevel"));
					Session.branchList.add(branchlistMember);		
					
					Session.ItemGroup itemGroup = getItemGroup(j_branchlist.getJSONObject(i).getJSONArray("appList"),Session.user.getAttence());
					Session.itemGroupList.add(itemGroup);
				}
				
							
				Session.itemGroup=getItemGroup(j_branch.getJSONArray("appList"),Session.user.getAttence());
				Log.e("cc","groupId="+1+"size="+Session.itemGroup.getList()[1].size());
							
				//set push
				String tag=""+Session.user.getId();
				account.saveKeyValue(ServiceForAccount.KEY_PUSH_KEY, tag);
				BaseService.setJPushTag(tag, context);
			}
			return status;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	
	public static int loadpolicedata(String unitId) {
		try {
    		String url = allpolicetypePath+"?unitId="+unitId;
			String result =NetworkTool.getContent(url);
			allpoliceJSON = result;
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static int logout(String code) {
		try {
    		String url = logout+"?code="+code;
			String result =NetworkTool.getContent(url);
			Log.i("ceshi","result"+result);
			JSONObject j = new JSONObject(result);
			int result2 = j.getInt("result");
			return result2;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	
	private static Session.ItemGroup getItemGroup(JSONArray appList,int attence){
		Session.ItemGroup itemGroup=new Session.ItemGroup();
		for(int i=0;i<appList.length();i++){
			JSONObject j_item;
			int groupId=0;
			try {
				j_item = appList.getJSONObject(i);
				Session.Item item=new Session.Item();
				item.setId(j_item.getInt("id"));
				item.setImg(j_item.getString("img"));
				item.setName(j_item.getString("name"));
				item.setSortId(j_item.getInt("sort_id"));
				item.setType(j_item.getInt("type"));
				item.setUri(j_item.getString("uri"));
				item.setParentId(j_item.getInt("parent_id"));
				groupId=j_item.getInt("group_id");
				itemGroup.getList()[groupId].add(item);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if(attence==2)
		{
			Session.Item item=new Session.Item();
			item.setId(15);
			item.setImg("field_sign_out.png");
			item.setName("外勤打卡");
			item.setSortId(6);
			item.setType(0);
			item.setUri("attence");
			item.setParentId(0);
			itemGroup.getList()[3].add(item);
			
			Session.Item item2=new Session.Item();
			item2.setId(16);
			item2.setImg("field_record.png");
			item2.setName("外勤记录");
			item2.setSortId(7);
			item2.setType(2);
			item2.setUri("attenceOuter_list.html");
			item2.setParentId(0);
			itemGroup.getList()[3].add(item2);			
			
		}

		return itemGroup;
	}
	
	public static void logout(){
		Session.user=null;
	}
	
	public static String getImagePath(Context context) {
		if (imagePath == null) {
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				// 判断sd卡是否存在
				File sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
				File file = new File(sdDir.getAbsolutePath() + "/oa");
				if (file.isDirectory()) {
					imagePath = file.getAbsolutePath();
				} else {
					if (file.mkdirs()) {
						imagePath = file.getAbsolutePath();
					} else {
						imagePath = context.getFilesDir()
								.getAbsolutePath();
					}
				}
			} else {
				imagePath = context.getFilesDir().getAbsolutePath();
			}
		}
		return imagePath;
	}
	
	public static void setJPushTag(String tag, Context context) {
		Set<String> tagSet = new LinkedHashSet<String>();
		tagSet.add(tag);
		// 调用JPush API设置Tag
		JPushInterface.setTags(context, tagSet, new TagAliasCallback() {
			@Override
			public void gotResult(int code, String arg1, Set<String> arg2) {
				System.out.println("set Tag Result=" + code);
			}

		});
	}

	public static Map<String,Object> uploadPic(String picturePath,String actionName) {
		FileUpload up = new FileUpload();
		try {
				return up.sends(BaseService.OAPath+"/"+actionName+"!uploadImg.action",
						picturePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
    public static String getDepartmentInfo(int unitid){
    	try {
    		String url = departPath+"?unitid="+unitid;
			String result =NetworkTool.getContent(url);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    	
    }
    
    public static String getUnitAllPolice(int unitid){
    	try {
    		String url = allpolicePath+"?unitId="+unitid;
			String result =NetworkTool.getContent(url);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    	
    }
    
    public static String getDepartmentPolice(int depid){
    	try {
    		String url = departpolicePath+"?depId="+depid;
			String result =NetworkTool.getContent(url);

			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    	
    }
    
    public static int submitMessage(int minid,String name,String phone,String content){
    	try {
    		String url = submitMessagePath+"?id="+minid+"&name="+name+"&phone="+phone+"&content="+content;
			String result =NetworkTool.getContent(url);
			JSONObject objMap = new JSONObject(result);
			if(objMap.has("result")){
				return objMap.getInt("result");
			}
			return 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
    	
    }
	
	public static void saveGlobalInfo(String key,String value,Context context){
		ServiceForAccount account =new ServiceForAccount(context);
		account.saveKeyValue(key, value);
	}
	
	public static String readGlobalInfo(String key,Context context){
		ServiceForAccount account =new ServiceForAccount(context);
		return account.getValueByKey(key);
	}
	
	public static void saveNotGlobalInfo(String key,String value)
	{
		BaseService.account_map.put(key, value);
	}
	public static String readNotGlobalInfo(String key){
		return BaseService.account_map.get(key);
	}
	
}
