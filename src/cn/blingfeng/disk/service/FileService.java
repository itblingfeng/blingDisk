package cn.blingfeng.disk.service;

import cn.blingfeng.disk.pojo.TbFile;
import cn.blingfeng.disk.pojo.TbFileType;
import cn.blingfeng.disk.utils.pojo.DiskFile;
import cn.blingfeng.disk.utils.pojo.DiskResult;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FileService {
    List<DiskFile> listAllFile(Long userId);
    List<DiskFile> conditionQueryFiles(Long userId,Long typeId);
    DiskResult updateFileName(TbFile file);
    DiskResult uploadFile(TbFile file);
    String uploadFileType(TbFileType type);
    ResponseEntity<byte[]> downLoadFile(String url)throws Exception;
    DiskResult deleteFile(String url) throws Exception;
}
