package cn.blingfeng.disk.service.impl;

import cn.blingfeng.disk.mapper.TbFileMapper;
import cn.blingfeng.disk.mapper.TbFileTypeMapper;
import cn.blingfeng.disk.pojo.TbFile;
import cn.blingfeng.disk.pojo.TbFileExample;
import cn.blingfeng.disk.pojo.TbFileType;
import cn.blingfeng.disk.pojo.TbFileTypeExample;
import cn.blingfeng.disk.service.FileService;
import cn.blingfeng.disk.utils.FastDFSClient;
import cn.blingfeng.disk.utils.pojo.DiskFile;
import cn.blingfeng.disk.utils.pojo.DiskResult;
import org.csource.fastdfs.StorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private TbFileMapper fileMapper;
    @Autowired
    private TbFileTypeMapper fileTypeMapper;
    @Autowired
    private FastDFSClient fastDFSClient;
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

    @Override
    public DiskResult updateFileName(TbFile file) {
        fileMapper.updateByPrimaryKeySelective(file);
        return DiskResult.ok();
    }

    @Override
    public DiskResult uploadFile(TbFile file) {
        fileMapper.insert(file);
        return DiskResult.ok();
    }

    @Override
    public String uploadFileType(TbFileType fileType) {
        TbFileTypeExample example = new TbFileTypeExample();
        example.createCriteria().andTypeNameEqualTo(fileType.getTypeName());
        List<TbFileType> typeList = fileTypeMapper.selectByExample(example);
        Long id;
        if (typeList != null && typeList.size() != 0) {
            id = typeList.get(0).getId();
        } else {
            id = fileTypeMapper.insert(fileType);
        }
        return id.toString();
    }

    @Override
    public ResponseEntity<byte[]> downLoadFile(String url) throws Exception {
        /*获得文件名*/
        String substr = url.substring(url.indexOf("group"));
        String group = substr.split("/")[0];
        String remoteFileName = substr.substring(substr.indexOf("/") + 1);
        /*组名*/
        HttpHeaders headers = new HttpHeaders();
        byte[] download_file = fastDFSClient.downLoad_File(group, remoteFileName);
        TbFileExample example = new TbFileExample();
        example.createCriteria().andFileUrlEqualTo(url);
        List<TbFile> fileList = fileMapper.selectByExample(example);
        /*获取文件名*/
        String fileName = fileList.get(0).getFileName();
        headers.setContentDispositionFormData("attachment", new String(fileName.getBytes("UTF-8"), "iso-8859-1"));
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(download_file, headers, HttpStatus.CREATED);
    }

    @Override
    public DiskResult deleteFile(String url) throws Exception {
        TbFileExample example = new TbFileExample();
        example.createCriteria().andFileUrlEqualTo(url);
        fileMapper.deleteByExample(example);
        /*还需删除服务器上文件*/
        String substr = url.substring(url.indexOf("group"));
        String group = substr.split("/")[0];
        String remoteFileName = substr.substring(substr.indexOf("/") + 1);
        fastDFSClient.delete_File(group,remoteFileName);
        return DiskResult.ok();
    }
}
