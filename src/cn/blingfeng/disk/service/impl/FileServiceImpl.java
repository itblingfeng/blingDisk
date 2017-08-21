package cn.blingfeng.disk.service.impl;

import cn.blingfeng.disk.mapper.TbFileMapper;
import cn.blingfeng.disk.mapper.TbFileTypeMapper;
import cn.blingfeng.disk.mapper.TbLastUploadMapper;
import cn.blingfeng.disk.pojo.*;
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

import java.util.Arrays;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private TbFileMapper fileMapper;
    @Autowired
    private TbFileTypeMapper fileTypeMapper;
    @Autowired
    private FastDFSClient fastDFSClient;
    @Autowired
    private TbLastUploadMapper lastUploadMapper;

    @Override
    public List<DiskFile> listAllFile(Long userId, Long parentId) {
        /*获得文件列表*/
        List<DiskFile> fileList = fileMapper.getFileList(userId, parentId);
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
        /*首先根据用户id查询记录条数*/
        /*条数如果大于等于3，则删掉id最小的*/
        Integer count = lastUploadMapper.userLastUploadCount(file.getUserId());
        TbLastUpload lastUpload = new TbLastUpload();
        lastUpload.setLastUpload(file.getFileName());
        lastUpload.setUserId(file.getUserId());
        if (count < 3) {
            lastUploadMapper.insert(lastUpload);
        } else {
            lastUploadMapper.deleteLastUpload(file.getUserId());
            lastUploadMapper.insert(lastUpload);
        }
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
        fastDFSClient.delete_File(group, remoteFileName);
        return DiskResult.ok();
    }

    @Override
    public List<TbLastUpload> selectLastUpload(Long userId) {
        TbLastUploadExample example = new TbLastUploadExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<TbLastUpload> lastUploadList = lastUploadMapper.selectByExample(example);
        return lastUploadList;
    }

    @Override
    public DiskResult mkDir(String dirName, Long parentId, Long userId) {
        TbFile file = new TbFile();
        file.setFileName(dirName);
        file.setUserId(userId);
        file.setFileType("6");
        file.setParentId(parentId);
        file.setIsParent(1);
        fileMapper.insert(file);
        return DiskResult.ok();
    }

    @Override
     /*查询Id为parentId的文件*/
    /*查找此文件的parentId*/
    /*查找parentId为parentId的文件*/
    public List<DiskFile> backLevel(Long parentId, Long userId) {
        List<DiskFile> diskFiles = fileMapper.getFileListById(parentId, userId);
        if (diskFiles==null||diskFiles.size()==0){
            return null;
        }
        Long parent_id = diskFiles.get(0).getParent_id();
        List<DiskFile> fileList = this.listAllFile(userId, parent_id);
        return fileList;
    }

    @Override
    public DiskResult deleteFilesByIds(Long[] ids, Long userId) {
        TbFileExample example = new TbFileExample();
        example.createCriteria().andIdIn(Arrays.asList(ids)).andUserIdEqualTo(userId);
        List<TbFile> tbFiles = fileMapper.selectByExample(example);
        fileMapper.deleteByExample(example);
        for (TbFile file : tbFiles) {
            if (file.getIsParent() == 1) {
                deleteFileByParentId(file.getId(), userId);
            }
        }
        return DiskResult.ok();
    }

    public void deleteFileByParentId(Long parentId, Long userId) {
        /*首先查询parent_Id为parentId的记录*/
        /*对记录进行遍历，若为文件，则删除，若为文件夹，则记录Id为parentId，删除递归*/
        TbFileExample example = new TbFileExample();
        example.createCriteria().andParentIdEqualTo(parentId).andUserIdEqualTo(userId);
        List<TbFile> tbFiles = fileMapper.selectByExample(example);
        fileMapper.deleteByExample(example);
        if (tbFiles.size() == 0) {
            return;
        } else {
            for (TbFile file : tbFiles) {
                if (file.getIsParent() == 1) {
                    deleteFileByParentId(file.getId(), userId);
                }
            }
        }

    }
}
