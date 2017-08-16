package cn.blingfeng.disk.service.impl;

import cn.blingfeng.disk.mapper.TbUserMapper;
import cn.blingfeng.disk.pojo.TbUser;
import cn.blingfeng.disk.pojo.TbUserExample;
import cn.blingfeng.disk.service.LoginService;
import cn.blingfeng.disk.utils.pojo.DiskResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private TbUserMapper userMapper;
    @Override
    public DiskResult login(String username, String password) {
        /*根据用户名查询是否存在用户*/
        /*若存在，再对比密码*/
        TbUserExample example = new TbUserExample();
        example.createCriteria().andUserNameEqualTo(username);
        List<TbUser> userList = userMapper.selectByExample(example);
        if(userList==null||userList.size()==0){
            return DiskResult.build(400,null,"用户名或密码错误");
        }
        /*若存在该用户名，对比密码*/
        TbUser user = userList.get(0);
        if(!user.getUserPassword().equals(password)){
            return DiskResult.build(400,null,"用户名或密码错误");
        }
        user.setUserPassword(null);
        return DiskResult.ok(user);
    }
}
