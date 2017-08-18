package cn.blingfeng.disk.mapper;

import cn.blingfeng.disk.pojo.TbFileType;
import cn.blingfeng.disk.pojo.TbFileTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbFileTypeMapper {
    int countByExample(TbFileTypeExample example);

    int deleteByExample(TbFileTypeExample example);

    int deleteByPrimaryKey(Long id);

    Long insert(TbFileType record);

    int insertSelective(TbFileType record);

    List<TbFileType> selectByExample(TbFileTypeExample example);

    TbFileType selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbFileType record, @Param("example") TbFileTypeExample example);

    int updateByExample(@Param("record") TbFileType record, @Param("example") TbFileTypeExample example);

    int updateByPrimaryKeySelective(TbFileType record);

    int updateByPrimaryKey(TbFileType record);
}