package cn.blingfeng.disk.service;

import cn.blingfeng.disk.pojo.TbFile;
import cn.blingfeng.disk.utils.pojo.DiskFile;

import java.util.List;

public interface FileService {
    List<DiskFile> listAllFile(Long userId);
    List<DiskFile> conditionQueryFiles(Long userId,Long typeId);
}
