package com.atyh.mybatis_plus;

import com.atyh.mybatis_plus.entity.User;
import com.atyh.mybatis_plus.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisPlusTests {

	@Autowired
	UserMapper userMapper;

	//ge、gt、le、lt、isNull、isNotNull
	@Test
	public void testDelete(){
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.isNull("name")
				.ge("age",43)
				.isNotNull("email");
		int i = userMapper.delete(queryWrapper);
		System.out.println(i);

	}

	//eq 等于、ne不等于
	@Test
	public void testSelectOne(){
		QueryWrapper<User> queryWrapper = new QueryWrapper <>();
		queryWrapper.eq("name","Tom");
		//queryWrapper.ne("name","Tom");
		User user = userMapper.selectOne(queryWrapper);
		System.out.println(user);

	}
	//between、notBetween
	@Test
	public void testSelectCount(){
		QueryWrapper<User> queryWrapper = new QueryWrapper <>();
		queryWrapper.between("age",12,44);
		Integer count = userMapper.selectCount(queryWrapper);
		System.out.println("--------"+count);

	}
	//allEq
	@Test
	public void testSelectList(){
		QueryWrapper<User> queryWrapper = new QueryWrapper <>();
		Map<String,Object> map = new HashMap <>();
		map.put("name","Tom");
		map.put("age",44);
		map.put("id",3);
		queryWrapper.allEq(map);
		List <User> users = userMapper.selectList(queryWrapper);
		System.out.println(users);
	}
	//like、notLike、likeLeft、likeRight
	/*WHERE
			deleted=0
	AND name NOT LIKE '%o%'
	AND email LIKE '%m'*/
	@Test
	public void testSelectMaps(){
		QueryWrapper<User> queryWrapper = new QueryWrapper <>();
		queryWrapper.notLike("name","o")
				.likeLeft("email","m");
		List <Map <String, Object>> maps = userMapper.selectMaps(queryWrapper);
		maps.forEach(System.out::println);
	}
	//in、notIn、inSql、notinSql、exists、notExists
	/*in、notIn：
	notIn("age",{1,2,3})--->age not in (1,2,3)
	notIn("age", 1, 2, 3)--->age not in (1,2,3)
	inSql、notinSql：可以实现子查询
	例: inSql("age", "1,2,3,4,5,6")--->age in (1,2,3,4,5,6)
	例: inSql("id", "select id from table where id < 3")--->id in (select id from table where id < 3)*/
	@Test
	public void testSelectObjs(){
		QueryWrapper<User> queryWrapper = new QueryWrapper <>();
		queryWrapper.inSql("id","select id from user where id < 3");
		List <Object> objects = userMapper.selectObjs(queryWrapper);
		objects.forEach(System.out::println);
	}

}
