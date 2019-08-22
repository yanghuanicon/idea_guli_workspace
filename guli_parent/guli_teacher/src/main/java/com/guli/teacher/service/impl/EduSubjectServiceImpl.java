package com.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.teacher.entity.EduSubject;
import com.guli.teacher.entity.dto.OneSubjectDto;
import com.guli.teacher.entity.dto.TwoSubjectDto;
import com.guli.teacher.handler.EduException;
import com.guli.teacher.mapper.EduSubjectMapper;
import com.guli.teacher.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-07-31
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //导入课程分类
    @Override
    public List<String> importData(MultipartFile file) {
        try{
            //poi读取代码
            //获取文件输入流
            InputStream in = file.getInputStream();
            //获取workbook
            Workbook workbook = new HSSFWorkbook(in);
            //获取sheet
            Sheet sheet = workbook.getSheetAt(0);
            //获取sheet里边的所有row
            //获取总行数
            int lastRowNum = sheet.getLastRowNum();
            //定义msg集合来存储错误信息
            List<String> msg = new ArrayList <>();
            //遍历每一个row
            //第一行是表头不需要遍历
            for (int i = 1; i < lastRowNum; i++) {
                Row row = sheet.getRow(i);
                //获取第一列-------------------------------------------------
                Cell cellOne = row.getCell(0);

                //判断第一列是否为空
                if(cellOne == null){
                    //放入提示信息
                    msg.add("第"+(i+1)+"行，第一列为空请重新输入");
                    //跳出本次循环执行下次循环
                    continue;
                }

                //获取第一列的值
                String cellOneValue = cellOne.getStringCellValue();

                //定义一个变量用于存放一级标题的id
                String pid = null ;

                //判断数据库中是否有该一级标题
                EduSubject oneEduSubject = this.existOneSubject(cellOneValue);
                if(oneEduSubject == null){//数据库中没有进行添加操作
                    EduSubject eduSubjectOne = new EduSubject();
                    eduSubjectOne.setTitle(cellOneValue);
                    eduSubjectOne.setParentId("0");
                    baseMapper.insert(eduSubjectOne);
                    //获取添加完之后一级标题的id
                    pid = eduSubjectOne.getId();
                }else {//数据库中存在这个一级标题
                    //获取其一级标题的id
                    pid = oneEduSubject.getId();
                }
                //获取第二列--------------------------------------------------
                Cell cellTwo = row.getCell(1);
                //判断第二列是否为空
                if(cellTwo == null) {
                    //将错误信息放到msg中
                    msg.add("第"+(i+1)+"行，第二列为空请重新输入");
                    //跳出本次循环
                    continue;
                }
                //获取第二列的值
                String cellTwoValue = cellTwo.getStringCellValue();
                //判断是否有相同的二级标题
                EduSubject TwoEduSubject = this.existTwoSubject(cellTwoValue);
                if(TwoEduSubject == null){//没有就进行添加

                    EduSubject eduSubjectTwo = new EduSubject();
                    eduSubjectTwo.setTitle(cellTwoValue);
                    eduSubjectTwo.setParentId(pid);
                    baseMapper.insert(eduSubjectTwo);
                }

            }

            return msg;
        }catch (Exception e){
            e.printStackTrace();
            throw new EduException(20001,"出现异常啦");
        }

    }
    //显示回显数据以json的方式进行返回
    @Override
    public List <OneSubjectDto> getSubjectAll() {
        //查询所有的一级分类
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper();
        oneWrapper.eq("parent_id","0");
        List <EduSubject> oneEduSubjects = baseMapper.selectList(oneWrapper);

        //查询所有的二级分类
        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper <>();
        twoWrapper.ne("parent_id","0");
        List <EduSubject> twoEduSubjects = baseMapper.selectList(twoWrapper);

        //创建集合用于封装最终的数据
        List<OneSubjectDto> finalList = new ArrayList <>();

        //进行封装
        //BeanUtils.copyProperties();
        //封装一级目录
        for (int i = 0; i <oneEduSubjects.size() ; i++) {
            //得到每一个一级分类
            EduSubject oneEduSubject = oneEduSubjects.get(i);
            //将其装换位dto对象
            OneSubjectDto oneSubjectDto = new OneSubjectDto();
            BeanUtils.copyProperties(oneEduSubject,oneSubjectDto);
            //将dto对象放到最终封装好的集合中
            finalList.add(oneSubjectDto);

            //创建集合用于封装二级目录
            List<TwoSubjectDto> twoSubjectDtos = new ArrayList <>();

            //封装二级目录
            //遍历二级目录
            for (int j = 0; j < twoEduSubjects.size(); j++) {
                //获取每一个二级目录
                EduSubject twoEduSubject = twoEduSubjects.get(j);
                //判断二级标题的parent_id和一级标题的id是否一致
                if(twoEduSubject.getParentId().equals(oneEduSubject.getId())){
                    //相等，进行封装
                    //将其转换成dto对象
                    TwoSubjectDto twoSubjectDto = new TwoSubjectDto();
                    BeanUtils.copyProperties(twoEduSubject,twoSubjectDto);
                    //将dto对象放到生命好的集合中
                    twoSubjectDtos.add(twoSubjectDto);
                }

            }
            //将封装好的二级标题的集合赋值给一级标题的对象中的children集合
            oneSubjectDto.setChildren(twoSubjectDtos);
        }

        return finalList;
    }
    //根据id删除节点
    @Override
    public Boolean removeSubjectById(String id) {
        QueryWrapper<EduSubject> wrapper =new QueryWrapper <>();
        wrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(wrapper);

        if(count>0) {//有子节点不能删除
            throw new EduException(20001,"有子节点不能删除");
        } else{//没有子分类进行删除
            int result = baseMapper.deleteById(id);
            return result>0 ;
        }
    }


    //判断是否有相同的二级标题---------------------------
    private EduSubject existTwoSubject(String twoSubjectName){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper <>();
        wrapper.eq("title",twoSubjectName)
                .ne("parent_id","0");
        EduSubject eduSubject = baseMapper.selectOne(wrapper);
        return eduSubject;

    }

    //判断数据库中是否存在相同的一级标题
    private EduSubject existOneSubject(String oneSubjectName){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper <>();
        wrapper.eq("title",oneSubjectName)
                .eq("parent_id","0");
        EduSubject eduSubject = baseMapper.selectOne(wrapper);
        return eduSubject;
    }
}
