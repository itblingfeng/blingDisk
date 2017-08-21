package cn.blingfeng.disk.mapper;

import cn.blingfeng.disk.pojo.TbLastDownload;
import cn.blingfeng.disk.pojo.TbLastDownloadExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbLastDownloadMapper {
    int countByExample(TbLastDownloadExample example);

    int deleteByExample(TbLastDownloadExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbLastDownload record);

    int insertSelective(TbLastDownload record);

    List<TbLastDownload> selectByExample(TbLastDownloadExample example);

    TbLastDownload selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbLastDownload record, @Param("example") TbLastDownloadExample example);

    int updateByExample(@Param("record") TbLastDownload record, @Param("example") TbLastDownloadExample example);

    int updateByPrimaryKeySelective(TbLastDownload record);

    int updateByPrimaryKey(TbLastDownload record);
}