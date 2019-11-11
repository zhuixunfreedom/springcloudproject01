package com.ylt.servicefile.dao;

import com.ylt.servicefile.bean.FileTable;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * JpaSpecificationExecutor接口
 *  方法定义规则
 符号     含义
 And  并且
 Or   或
 Is,Equals   等于
 Between  两者之间
 LessThan  小于
 LessThanEqual  小于等于
 GreaterThan  大于
 GreaterThanEqual  大于等于
 After  之后（时间）>
 Before  之前（时间）<
 IsNull   等于Null
 IsNotNull,NotNull   不等于Null
 Like   模糊查询。查询件中需要自己加%
 NotLike   不在模糊范围内。查询件中需要自己加%
 StartingWith   以某开头
 EndingWith    以某结束
 Containing   包含某
 OrderBy    排序
 Not    不等于
 In   某范围内
 NotIn  某范围外
 TRUE    真
 FALSE    假
 IgnoreCase 忽略大小写
 */
@Mapper
public interface FileTabletDao extends JpaRepository<FileTable,Long>,JpaSpecificationExecutor {

    @Query("select count(fileName) from FileTable where fileName like concat(:fileName,'%')")
    int getCount(@Param("fileName") String fileName);
}
