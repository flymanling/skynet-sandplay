package com.skynet.sandplay.form;

import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.skynet.sandplay.util.ObjectUtil;

public class BaseMsg {

	public int serviceId;
	public int actionId;
	
	public String content;
	
	public Integer userId;
	public String userName;
	public HttpServletRequest req;
	public HttpServletResponse resp;
	private static Gson gson = new Gson();
	private JsonObject jsonObject;
	private static Gson _gson = new GsonBuilder().registerTypeAdapter(Double.class, new JsonSerializer<Double>() {

		@Override
		public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
			if (src == src.longValue())
				return new JsonPrimitive(src.longValue());
			return new JsonPrimitive(src);
		}
	}).create();
	
	public JsonObject json() {
		if (jsonObject == null) {
			jsonObject = new JsonParser().parse(content).getAsJsonObject();
		}
		return jsonObject;
	}
	
	/**
	 * 从data中获取指定key的值
	 * @param key
	 * @return
	 */
	public JsonElement get(String key) {
		return json().get(key);
	}
	
	/**
	 * 是否有指定的key
	 * @param key
	 * @return
	 */
	public boolean has(String key) {
		return json().has(key);
	}

	/**
	 * 获取一个int值，不存在key则抛异常
	 * @param key
	 * @return
	 */
	public int getAsInt(String key) {
		try {
			return get(key).getAsInt();
		} catch (Exception e) {
			throw new RuntimeException("get key error", e);
		}
	}

	/**
	 * 获取一个int值,没有则返回默认的defaults
	 * @param key
	 * @param defaults-key不存在时的默认值
	 * @return
	 */
	public int getAsInt(String key, int defaults) {
		if (has(key)) {
			return getAsInt(key);
		}
		return defaults;
	}

	/**
	 * 获取一个byte值，不存在key则抛异常
	 * @param key
	 * @return
	 */
	public byte getAsByte(String key) {
		try {
			return get(key).getAsByte();
		} catch (Exception e) {
			throw new RuntimeException("get key error", e);
		}
	}

	public byte getAsByte(String key, byte defaults) {
		if (has(key)) {
			return getAsByte(key);
		}
		return defaults;
	}

	/**
	 * 获取一个Float值，不存在key则抛异常
	 * @param key
	 * @return
	 */
	public Float getAsFloat(String key) {
		try {
			return get(key).getAsFloat();
		} catch (Exception e) {
			throw new RuntimeException("key not found", e);
		}
	}

	public Float getAsFloat(String key, Float defaults) {
		if (has(key)) {
			return getAsFloat(key);
		}
		return defaults;
	}

	/**
	 * 获取一个Double值，不存在key则抛异常
	 * @param key
	 * @return
	 */
	public Double getAsDouble(String key) {
		try {
			return get(key).getAsDouble();
		} catch (Exception e) {
			throw new RuntimeException("key not found", e);
		}
	}

	public Double getAsDouble(String key, Double defaults) {
		if (has(key)) {
			return getAsDouble(key);
		}
		return defaults;
	}

	/**
	 * 获取一个Long值，不存在key则抛异常
	 * @param key
	 * @return
	 */
	public Long getAsLong(String key) {
		try {
			return get(key).getAsLong();
		} catch (Exception e) {
			throw new RuntimeException("get key error", e);
		}
	}

	public Long getAsLong(String key, Long defaults) {
		if (has(key)) {
			return getAsLong(key);
		}
		return defaults;
	}

	/**
	 * 获取一个Boolean值，不存在key则抛异常
	 * @param key
	 * @return
	 */
	public Boolean getAsBoolean(String key) {
		try {
			return get(key).getAsBoolean();
		} catch (Exception e) {
			throw new RuntimeException("get key error", e);
		}
	}

	public Boolean getAsBoolean(String key, Boolean defaults) {
		if (has(key)) {
			return getAsBoolean(key);
		}
		return defaults;
	}

	/**
	 * 获取一个String值，不存在key则抛异常
	 * @param key
	 * @return
	 */
	public String getAsString(String key) {
		try {
			return get(key).getAsString();
		} catch (Exception e) {
			throw new RuntimeException("get key error", e);
		}
	}

	public String getAsString(String key, String defaults) {
		if (has(key)) {
			return getAsString(key);
		}
		return defaults;
	}

	/**
	 * 获取一个key并转化为指定Object, 不存在key则返回null
	 * @param key-key名称
	 * @param clazz-类类型
	 * @return
	 */
	public <T> T getAsObject(String key, Class<T> clazz) {
		if (has(key)) {
			JsonElement jsonElement = get(key);
			return gson.fromJson(jsonElement, clazz);
		}
		return null;
	}

	/**
	 * 获取一个key并转化为指定Object的List, 不存在key则返回null
	 * @param key-key名称
	 * @param clazz-类类型
	 * @return
	 */
	public <T> List<T> getAsList(String key, Class<T> clazz) {
		if (has(key)) {
			JsonElement jsonElement = get(key);
			if (clazz == Integer.class) {
				return _gson.fromJson(jsonElement, new TypeToken<List<Integer>>() {
				}.getType());
			}
			return gson.fromJson(jsonElement, new TypeToken<List<T>>() {
			}.getType());
		}
		return null;
	}

	/**
	 * 将整个data转化成T对象
	 * @param clazz-类类型
	 * @return
	 */
	public <T> T toObject(Class<T> clazz) {
		return gson.fromJson(content, clazz);
	}

	/**
	 * 将整个data转化成List<T>
	 * @param clazz-类类型
	 * @return
	 */
	public <T> List<T> toList(Class<T> clazz) {
		return gson.fromJson(content, new TypeToken<List<T>>() {
		}.getType());
	}

	@Override
	public String toString() {
		return ObjectUtil.toString(this);
	}
}
