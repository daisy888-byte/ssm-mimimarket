package com.bjpn.controller;

import com.bjpn.pojo.Admin;
import com.bjpn.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminService adminService;
    @RequestMapping("/login")
    public String login(@RequestParam("name") String username, @RequestParam("pwd") String password, HttpServletRequest request){
        Admin admin = adminService.login(username,password);
        if(admin!=null){
            request.setAttribute("admin",admin);
            return "main";
        }else {
            request.setAttribute("errmsg","账号或密码不存在！");
            return "login";
        }

    }
}
