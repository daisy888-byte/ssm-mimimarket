package com.bjpn.service.impl;

import com.bjpn.mapper.AdminMapper;
import com.bjpn.pojo.Admin;
import com.bjpn.pojo.AdminExample;
import com.bjpn.service.AdminService;
import com.bjpn.utils.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminMapper;
    @Override
    public Admin login( String username, String password) {
        Admin admindb=null;
        AdminExample example = new AdminExample();
        example.createCriteria().andANameEqualTo(username);
        List<Admin> list = adminMapper.selectByExample(example);
        if(list.size()>0){
            admindb = list.get(0);
            if(MD5Util.getMD5(password).equals(admindb.getaPass())){
                return admindb;
            }

        }
        return null;
    }
}
