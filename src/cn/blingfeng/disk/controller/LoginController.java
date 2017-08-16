package cn.blingfeng.disk.controller;

import cn.blingfeng.disk.service.LoginService;
import cn.blingfeng.disk.utils.pojo.DiskResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;
    @RequestMapping("/user/login")
    public @ResponseBody
    DiskResult login(String username,String password,HttpSession session) {
        DiskResult diskResult = loginService.login(username, password);
         if(diskResult.getStatus()==200){
             session.setAttribute("user",diskResult.getData());
         }
        return diskResult;
    }
}
