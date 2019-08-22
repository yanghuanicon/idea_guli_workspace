package com.atyh.mybatis_plus;

import com.atyh.mybatis_plus.entity.User;
import com.atyh.mybatis_plus.mapper.UserMapper;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisPlusApplicationTests {

	@Autowired
	UserMapper userMapper;
	@Test
	public void selectList() {
		List <User> users = userMapper.selectList(null);
		users.forEach(System.out::println);
	}

	@Test
	public void insert(){
		User user = new User();
		user.setName("zhao6");
		user.setAge(26);
		user.setEmail("zhao6@163.com");
		int insert = userMapper.insert(user);
		System.out.println(insert);
		System.out.println(user);
	}
	@Test
	public void update(){
		// 修改：根据条件；条件ID； version ？ 全部查询出来，吧所有的字段都查询出来；修改的时候根据version来修改
		User user = userMapper.selectById(3l);
		user.setVersion(user.getVersion()-1);
		user.setAge(55);
		int i = userMapper.updateById(user);
		System.out.println(i);
	}

	@Test
	public void testSelectBatchIds(){

		List<Long> ids = new ArrayList <>();
		ids.add(1L);
		ids.add(2L);
		ids.add(3L);
		List <User> users = userMapper.selectBatchIds(ids);
		users.forEach(System.out::println);
	}

	@Test
	public void testSelectPage() {
		Page<User> page =new Page <>(2,5);
		IPage <User> userIPage = userMapper.selectPage(page, null);
		System.out.println("userIPage.getRecords()"+userIPage.getRecords());
		System.out.println("userIPage.getCurrent()"+userIPage.getCurrent());
		System.out.println("userIPage.getPages()"+userIPage.getPages());
		System.out.println("userIPage.getSize()"+userIPage.getSize());
		System.out.println("userIPage.getTotal()"+userIPage.getTotal());
		System.out.println("userIPage.hashCode()"+userIPage.hashCode());

	}
	@Test
	public void testDeleteById() {
		int i = userMapper.deleteById(1153982671345471490l);
		System.out.println(i);
	}
	@Test
	public void testDeleteByMap() {
		HashMap<String,Object> map = new HashMap <>() ;
		map.put("name","wangwu5");
		map.put("age","21");
		int i = userMapper.deleteByMap(map);
		System.out.println(i);
	}
	@Test
	public void testLogicDelete(){
		int i = userMapper.deleteById(1153971415234445313L);
		System.out.println(i);
	}


}
