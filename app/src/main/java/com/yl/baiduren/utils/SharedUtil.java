package com.yl.baiduren.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import it.sauronsoftware.base64.Base64;


/**
 * Created by SharedPreferences on 2016/8/16.
 * SharedPreferences
 */
public class SharedUtil {

    private static SharedUtil sharedUtil = null;
    private static SharedPreferences shared = null;

    private SharedUtil() {
    }

    public static SharedUtil getSharedUtil() {
        if (sharedUtil == null) {
            sharedUtil = new SharedUtil();
        }
        return sharedUtil;
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        if (shared == null) {
            shared = context.getSharedPreferences("dateShared", Context.MODE_PRIVATE);
        }
        return shared;
    }


    /**
     * @return
     */

    public void putString(Context context, String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key, value).commit();
    }

    public void putBoolean(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(key, value).commit();
    }

    public void putFloat(Context context, String key, float value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putFloat(key, value).commit();
    }

    public void putInt(Context context, String key, int value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(key, value).commit();
    }

    public void putLong(Context context, String key, long value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putLong(key, value).commit();
    }

    public void putObject(Context context, String key, Serializable obj) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ObjectOutputStream os = null;
        byte[] bt = new byte[4096];
        try {
            os = new ObjectOutputStream(bs);
            os.writeObject(obj);
            bt = bs.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.flush();
                os.close();
                bs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String str = new String(Base64.encode(bt));
        editor.putString(key, str).commit();
    }

    public String getString(Context context, String key) {
        return getSharedPreferences(context).getString(key,null);
    }

    public boolean getBoolean(Context context, String key) {
        return getSharedPreferences(context).getBoolean(key, false);
    }

    public int getInt(Context context, String key) {
        return getSharedPreferences(context).getInt(key, 0);
    }

    public long getLong(Context context, String key) {
        return getSharedPreferences(context).getLong(key, 222);
    }

    public float getFloat(Context context, String key) {
        return getSharedPreferences(context).getFloat(key, 0);
    }

    /**
     * @param key
     * @param serializable
     * @return
     */
    public Object getObject(Context context, String key, Serializable serializable) {
        String res = getSharedPreferences(context).getString(key,"");
        byte[] bt = res.getBytes();
        byte[] b=Base64.decode(bt);
        ByteArrayInputStream bts = new ByteArrayInputStream(b);
        ObjectInputStream obj = null;
        try {
            obj = new ObjectInputStream(bts);
            return obj.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            LUtils.e("---io 异常----");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (obj != null) {
                try {
                    obj.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return serializable;
    }

    public void remove(Context context, String key) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(key).commit();
    }

}
