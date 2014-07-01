package com.jit.lib.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * 
 * FileName: Json2Bean.java
 * Description：使用Gson相互转化json与bean
 * Created by 曹玉斌 on 2014-5-6
 * Copyright (c) 2014年 JIT. All rights reserved.
 *
 */
public class Json2Bean {

	/**
	 * JsonArray----->List
	 * @param array
	 * @param cls
	 * @return
	 */
	public static <T> List<T> getList(String array, Class<T> cls) {
//		Gson gson = new Gson();
//		List<T> list = gson.fromJson(array.toString(),new TypeToken<List<T>>() {}.getType());
		List<T> list = new ArrayList<T>();
		try {
			JSONArray jsonArray = new JSONArray(array);
			for (int i = 0; i < jsonArray.length(); i++) {
				T t = getT(jsonArray.get(i).toString(), cls);
				list.add(t);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * JsonObject----->Bean
	 * @param bean
	 * @param cls
	 * @return
	 */
	public static <T> T getT(String bean, Class<T> cls) {
		Gson gson = new Gson();
		return gson.fromJson(bean, cls);
	}
	
	/**
	 * Bean------>String
	 * @param t
	 * @return
	 */
	public static <T> String toJsonFromBean(T t) {
		Gson gson = new Gson();
		return gson.toJson(t);
	}
	
	/**
	 * InputStream----->Bean
	 * @param stream
	 * @param cls
	 * @return
	 */
	public static <T> T getT(InputStream stream, Class<T> cls) {
		Gson gson = new Gson();
		return gson.fromJson(new InputStreamReader(stream), cls);
	}
	
	/**
	 * InputStream----->List
	 * @param stream
	 * @param cls
	 * @return
	 */
	public static <T> List<T> getList(InputStream stream, Class<T> cls) {
		Gson gson = new Gson();
		List<T> list = gson.fromJson(new InputStreamReader(stream),new TypeToken<List<T>>() {}.getType());
		return list;
	}
	
}
