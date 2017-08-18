package cn.blingfeng.disk.controller;

import cn.blingfeng.disk.pojo.TbFile;
import cn.blingfeng.disk.pojo.TbFileType;
import cn.blingfeng.disk.pojo.TbUser;
import cn.blingfeng.disk.service.FileService;
import cn.blingfeng.disk.utils.FastDFSClient;
import cn.blingfeng.disk.utils.pojo.DiskFile;
import cn.blingfeng.disk.utils.pojo.DiskResult;
import org.csource.fastdfs.StorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class FileController {

    @Autowired
    private FileService fileService;
    @Autowired
    private FastDFSClient fastDFSClient;
    @RequestMapping(value = {"/disk/list", "/index"})
    public String listAllFile(Model model, HttpSession session) {
        TbUser user = (TbUser) session.getAttribute("user");
        List<DiskFile> fileList = fileService.listAllFile(user.getId());
        model.addAttribute("fileList", fileList);
        return "index";
    }

    @RequestMapping("/disk/index")
    public String conditionQueryFiles(Long typeId, HttpSession session, Model model) {
        TbUser user = (TbUser) session.getAttribute("user");
        List<DiskFile> fileList = fileService.conditionQueryFiles(user.getId(), typeId);
        model.addAttribute("fileList", fileList);
        return "index";
    }

    /*修改文件名*/
    @RequestMapping("/disk/edit")
    public @ResponseBody
    DiskResult updateFileName(TbFile file) {
        DiskResult diskResult = fileService.updateFileName(file);
        /*还需添加将服务器中文件改名操作？也可不添加！*/
        return diskResult;
    }

    /*上传文件的保存*/
    @RequestMapping("/disk/upload")
    public @ResponseBody
    DiskResult uploadFile(@RequestParam("file") MultipartFile multipartFile, HttpSession session) {
        /*获得该文件对象*/
        try {
            /*获得文件扩展名*/
            String originalFilename = multipartFile.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            /*将该文件保存在服务器上*/
            String url = fastDFSClient.uploadFile(multipartFile.getBytes(), extName);
         /*获得文件名，文件类型，大小，用户id，以及服务器上的地址存入数据库*/
            TbUser user = (TbUser) session.getAttribute("user");
            TbFile tbFile = new TbFile();
            tbFile.setFileName(originalFilename);
            tbFile.setFileUrl("http://123.207.62.121/" + url);
            tbFile.setFileSize((long) (multipartFile.getBytes().length / 1024));
            tbFile.setUserId(user.getId());
            TbFileType fileType = new TbFileType();
            fileType.setTypeName(extName);
            String typeId = fileService.uploadFileType(fileType);
            tbFile.setFileType(typeId);
            DiskResult diskResult = fileService.uploadFile(tbFile);
         /*返回*/
            return diskResult;
        } catch (Exception e) {

            return DiskResult.build(500, null, e.getMessage());
        }

    }

    @RequestMapping("/downloadFile")
    public ResponseEntity<byte[]> downLoadFile(String url) {
        try {
            ResponseEntity<byte[]> responseEntity = fileService.downLoadFile(url);
            return responseEntity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @RequestMapping("/deleteFile")
    public @ResponseBody
    DiskResult deleteFile(String url) {
        try {
            DiskResult diskResult = fileService.deleteFile(url);
            return diskResult;
        } catch (Exception e) {
            return DiskResult.build(500,null,"服务器找不到该文件");
        }
    }


}
