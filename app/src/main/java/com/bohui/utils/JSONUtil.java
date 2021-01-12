package com.bohui.utils;

import com.bohui.http.ResponseBeanUtils;
import com.bohui.http.ResponseListBeanUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * Json工具�?
 * 
 * @author tony
 * 
 */
public class JSONUtil {
	private JSONUtil() {

	}

	private static JSONUtil instance = null;

	public static JSONUtil getInstance() {
		if (instance == null) {
			synchronized (JSONUtil.class) {
				if (instance == null) {
					instance = new JSONUtil();
				}
			}
		}
		return instance;
	}

	/**
	 * 生成对象
	 * 
	 * @param
	 * @return
	 */
	public Object getObject(String jsonStr, Class<?> classes) {
		return new Gson().fromJson(jsonStr, classes);
	}

	/**
	 * 将Json字符串转换成为自定义泛型�?
	 * 
	 * @param jsonStr
	 * @param
	 * @return
	 */
	public <T> ResponseBeanUtils<T> getGeneric(String jsonStr) {
		Gson gson = new Gson();
		ResponseBeanUtils<T> list = gson.fromJson(jsonStr,
				new TypeToken<ResponseBeanUtils<T>>() {
				}.getType());
		return list;
	}
	/**
	 * 将json字符串转换成为泛型集�?
	 * @param jsonStr
	 * @param
	 * @return
	 */
	public <T> ResponseListBeanUtils<T> getGenericList(String jsonStr) {
		Gson gson = new Gson();
		ResponseListBeanUtils<T> list = gson.fromJson(jsonStr,
				new TypeToken<ResponseListBeanUtils<T>>() {
				}.getType());
		
		return list;
	}

	/**
	 * 生成json
	 * 
	 * @param
	 * @return
	 */
	public String getString(Object obj) {
		return new Gson().toJson(obj);
	}

	public static <T> List<T> changeGsonToList(String gsonString, Class<T> cls) {
		Gson gson = new Gson();
		List<T> list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
		}.getType());
		return list;
	}

	public static <T> LinkedList<T> changeGsonToLinkList(String gsonString,
			Class<T> cls) {
		Gson gson = new Gson();
		LinkedList<T> list = gson.fromJson(gsonString,
				new TypeToken<LinkedList<T>>() {
				}.getType());
		return list;
	}

	public static <T> List<Map<String, T>> changeGsonToListMaps(
			String gsonString) {
		List<Map<String, T>> list = null;
		Gson gson = new Gson();
		list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
		}.getType());
		return list;
	}

	public static <T> Map<String, T> changeGsonToMaps(String gsonString) {
		Map<String, T> map = null;
		Gson gson = new Gson();
		map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
		}.getType());
		return map;
	}
}
