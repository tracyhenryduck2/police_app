package com.police.util;

import android.os.Parcel;
import android.os.Parcelable;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jorstin on 2015/3/18.
 */
public class PoliceBean implements Parcelable {

    public static final Parcelable.Creator<PoliceBean> CREATOR = new Parcelable.Creator<PoliceBean>() {
        public PoliceBean createFromParcel(Parcel in) {
            return new PoliceBean(in);
        }

        public PoliceBean[] newArray(int size) {
            return new PoliceBean[size];
        }
    };

    /**用户注册V账号*/
    private int id;
    /**用户昵称*/
    private String userName = "";
	private String avatarurl;
	private String phone;
	private String position;

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getAvatarurl() {
		return avatarurl;
	}

	public void setAvatarurl(String avatarurl) {
		this.avatarurl = avatarurl;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


    public int getUserId() {
        return id;
    }

    public void setUserId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }





    public PoliceBean(int id) {
        this.id = id;
    }

    private PoliceBean(Parcel in) {
        this.id = in.readInt();
        this.userName = in.readString();
        this.avatarurl=in.readString();
        this.phone=in.readString();
        this.position=in.readString();
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id" , id);
            jsonObject.put("userName" , userName);
            jsonObject.put("avatarurl" , avatarurl);
            jsonObject.put("phone" ,phone);
            jsonObject.put("position" ,position);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "ClientUser{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", avatarurl='" + avatarurl + '\'' +
                ", phone='" + phone + '\'' +
                ", position='" + position + '\'' +
                '}';
    }

    public PoliceBean from(String input) {
        JSONObject object = null;
        try {
            object = new JSONObject(input);
            if(object.has("id")) {
                this.id = object.getInt("id");
            }
            if(object.has("userName")) {
                this.userName = object.getString("userName");
            }

            if(object.has("avatarurl")) {
            	this.avatarurl = object.getString("avatarurl");
            }

            if(object.has("phone")) {
            	this.phone = object.getString("phone");
            }
            if(object.has("position")) {
            	this.position = object.getString("position");
            }
            
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.userName);
        dest.writeString(this.avatarurl);
        dest.writeString(this.phone);
        dest.writeString(this.position);
    }
}
