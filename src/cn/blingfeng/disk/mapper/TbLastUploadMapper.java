package cn.blingfeng.disk.mapper;

import cn.blingfeng.disk.pojo.TbLastUpload;
import cn.blingfeng.disk.pojo.TbLastUploadExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbLastUploadMapper {
    int countByExample(TbLastUploadExample example);

    int deleteByExample(TbLastUploadExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbLastUpload record);

    int insertSelective(TbLastUpload record);

    List<TbLastUpload> selectByExample(TbLastUploadExample example);

    TbLastUpload selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbLastUpload record, @Param("example") TbLastUploadExample example);

    int updateByExample(@Param("record") TbLastUpload record, @Param("example") TbLastUploadExample example);

    int updateByPrimaryKeySelective(TbLastUpload record);

    int updateByPrimaryKey(TbLastUpload record);

    Integer userLastUploadCount(Long userId);

    void deleteLastUpload(Long userId);
}