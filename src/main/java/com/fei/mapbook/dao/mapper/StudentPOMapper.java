package com.fei.mapbook.dao.mapper;

import com.fei.mapbook.dao.po.StudentPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentPOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StudentPO record);

    int insertSelective(StudentPO record);

    StudentPO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StudentPO record);

    int updateByPrimaryKey(StudentPO record);
}