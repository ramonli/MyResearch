package com.ramonli.sandbox.jackson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JacksonMain {

	public static void main(String[] args) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("int", 1);
		map.put("datetime", new Date());
		map.put("string", "gulu is googd!");
		System.out.println(new Date());
		System.out.println(System.getProperty("user.timezone"));
		for (String id : TimeZone.getAvailableIDs()) {
			System.out.println(id);
		}
		System.out.println(TimeZone.getTimeZone(System.getProperty("user.timezone")));

		ObjectMapper mapper = new MyObjectMapper();
//		mapper.getFactory().enable(JsonFactory.Feature.CANONICALIZE_FIELD_NAMES);
//		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, false);
		// mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		// mapper.setTimeZone(TimeZone.getDefault());
//		mapper.setTimeZone(TimeZone.getTimeZone(System.getProperty("user.timezone")));
		// mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ"));
		String json = mapper.writeValueAsString(map);
		System.out.println(json);

		Map<String, Object> obj = mapper.readValue(json, Map.class);
		for (String key : obj.keySet()) {
			System.out.println("key :" + key);
			System.out.println("value: " + obj.get(key) + ", " + obj.get(key).getClass());
		}
	}

	static class MyObjectMapper extends ObjectMapper {
        private static final long serialVersionUID = -6180290573148065443L;

		public MyObjectMapper() {
			this.setTimeZone(TimeZone.getTimeZone(System.getProperty("user.timezone")));
			// mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			this.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ"));
		}
	}
}
