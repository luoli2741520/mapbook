package com.fei.mapbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class MapbookApplication {

//	@Autowired
//	private StringRedisTemplate stringRedisTemplate;

	public static void main(String[] args) {
//		SpringApplication.run(MapbookApplication.class, args);
		//启动顺带测试jdbc链接
		ConfigurableApplicationContext context = SpringApplication.run(MapbookApplication.class, args);
		JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
		List<Map<String, Object>> result = jdbcTemplate.queryForList("SELECT * FROM student");
		System.out.println(result);
		//测试redis链接
		RedisTemplate bean = context.getBean(StringRedisTemplate.class);
		Object luoli = bean.opsForValue().get("luoli");
		System.out.println(luoli.toString());
	}

}
