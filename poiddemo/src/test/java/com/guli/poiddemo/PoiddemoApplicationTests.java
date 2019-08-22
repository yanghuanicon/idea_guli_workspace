package com.guli.poiddemo;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileOutputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PoiddemoApplicationTests {

	//1.对03版本excel做写操作
	@Test
	public void test01() {
		//1.创建workbook对象
		Workbook workbook = new HSSFWorkbook();

		//2.根据workbook创建sheet
		Sheet sheet = workbook.createSheet("会员管理");
		for (int i = 0; i <10 ; i++) {


			//3.根据sheet创建row
			Row row = sheet.createRow(i);//第一行

			//4.根据row创建cell
			Cell cell = row.createCell(i);//第一列

			//5.向cell设置内容
			cell.setCellValue("大司马");
		}
		try{
			//6.使用流把内容写到文件中
			FileOutputStream out = new FileOutputStream("D:/01.xls");
			//调用workbook里面的方法写入文件
			workbook.write(out);
			//关闭
			out.close();

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	//1.对07版本excel做写操作
	@Test
	public void test02(){
		//1.创建workbook对象
		Workbook workbook = new XSSFWorkbook();
		//2.创建sheet对象
		Sheet sheet = workbook.createSheet("讲师管理");

		for (int i = 0; i < 10; i++) {
			//3.创建row 行
			Row row = sheet.createRow(i);//第一行
			for (int j = 0; j < 10; j++) {
				//4.创建列
				Cell cell = row.createCell(j);//第一列
				//5.向列中写入内容
				cell.setCellValue("司马老贼");
			}
		}

		try{
			//6.使用流把数据写到文件上
			FileOutputStream out = new FileOutputStream("D:/01.xlsx");
			//调用workbook中的方法写入文件
			workbook.write(out);
			//关闭
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}



}
