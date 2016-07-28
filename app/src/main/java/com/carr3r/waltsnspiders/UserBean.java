package com.carr3r.waltsnspiders;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wneto on 06/11/2015.
 */
public class UserBean {

    public static String TAG = "UserBean";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    protected int position;
    protected String userId;
    protected String name;
    protected int level;
    protected int points;

    public UserBean(String json) {
        JSONObject bean;
        try {
            bean = new JSONObject(json);
            if (bean.has("l"))
                setLevel(bean.getInt("l"));
            if (bean.has("p"))
                setPoints(bean.getInt("p"));
            if (bean.has("n"))
                setName(bean.getString("n"));
            if (bean.has("u"))
                setUserId(bean.getString("u"));
            if (bean.has("ps"))
                setPosition(bean.getInt("ps"));
        } catch (JSONException e) {
            bean = null;
        }
    }

    public UserBean(JSONObject json) {
        try {
            if (json.has("l"))
                setLevel(json.getInt("l"));
            if (json.has("p"))
                setPoints(json.getInt("p"));
            if (json.has("n"))
                setName(json.getString("n"));
            if (json.has("u"))
                setUserId(json.getString("u"));
            if (json.has("ps"))
                setPosition(json.getInt("ps"));
        } catch (JSONException e) {
        }
    }

    public static List<UserBean> parseJSON(String json) {
        List<UserBean> out = new ArrayList<UserBean>();

        try {
            JSONArray list = new JSONArray(json);
            for (int i = 0; i < list.length(); i++) {
                JSONObject obj = list.getJSONObject(i);
                if (!obj.has("s"))
                    out.add(new UserBean(obj));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return out;
    }

    public String toString() {
        return "userId=" + getUserId() + ";name=" + getName() + ";level=" + getLevel() + ";points=" + getPoints() + ";position=" + getPosition();
    }

}
