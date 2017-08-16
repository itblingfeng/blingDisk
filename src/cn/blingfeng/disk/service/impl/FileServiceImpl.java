package cn.blingfeng.disk.service.impl;

import cn.blingfeng.disk.mapper.TbFileMapper;
import cn.blingfeng.disk.pojo.TbFile;
import cn.blingfeng.disk.service.FileService;
import cn.blingfeng.disk.utils.pojo.DiskFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FileServiceImpl implements FileService {
   @Autowired
   private TbFileMapper fileMapper;
    @Override
    public List<DiskFile> listAllFile(Long userId) {
        /*获得文件列表*/
        List<DiskFile> fileList = fileMapper.getFileList(userId);
        return fileList;
    }

    @Override
    public List<DiskFile> conditionQueryFiles(Long userId, Long typeId) {
        List<DiskFile> fileList = fileMapper.conditionQueryFiles(userId, typeId);
        return fileList;
    }
}
