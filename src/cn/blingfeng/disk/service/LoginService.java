package cn.blingfeng.disk.service;

import cn.blingfeng.disk.utils.pojo.DiskResult;

public interface LoginService {
    DiskResult login(String username,String password);
}
