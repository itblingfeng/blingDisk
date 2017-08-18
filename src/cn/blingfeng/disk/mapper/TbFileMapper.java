package cn.blingfeng.disk.mapper;

import cn.blingfeng.disk.pojo.TbFile;
import cn.blingfeng.disk.pojo.TbFileExample;
import java.util.List;

import cn.blingfeng.disk.utils.pojo.DiskFile;
import org.apache.ibatis.annotations.Param;

public interface TbFileMapper {
    int countByExample(TbFileExample example);

    int deleteByExample(TbFileExample example);

    int deleteByPrimaryKey(Long id);

    Long insert(TbFile record);

    int insertSelective(TbFile record);

    List<TbFile> selectByExample(TbFileExample example);

    TbFile selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbFile record, @Param("example") TbFileExample example);

    int updateByExample(@Param("record") TbFile record, @Param("example") TbFileExample example);

    int updateByPrimaryKeySelective(TbFile record);

    int updateByPrimaryKey(TbFile record);
    List<DiskFile> getFileList(Long userId);
    List<DiskFile> conditionQueryFiles(Long userId,Long typeId);
}