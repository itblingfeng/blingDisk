package cn.blingfeng.disk.controller;
import cn.blingfeng.disk.pojo.TbFile;
import cn.blingfeng.disk.pojo.TbUser;
import cn.blingfeng.disk.service.FileService;
import cn.blingfeng.disk.utils.pojo.DiskFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class FileController {
    @Autowired
    private FileService fileService;
    @RequestMapping(value={"/disk/list","/index"})
    public String listAllFile(Model model, HttpSession session){
        TbUser user = (TbUser) session.getAttribute("user");
        List<DiskFile> fileList = fileService.listAllFile(user.getId());
        model.addAttribute("fileList",fileList);
        return "index";
    }
    @RequestMapping("/disk/index")
    public String conditionQueryFiles(Long typeId,HttpSession session,Model model){
        TbUser user = (TbUser) session.getAttribute("user");
        List<DiskFile> fileList = fileService.conditionQueryFiles(user.getId(), typeId);
        model.addAttribute("fileList",fileList);
        return "index";
    }
}
