package com.practice.seckill.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.practice.seckill.admin.service.MemberService;
import com.practice.seckill.common.constant.ReturnResponse;
import com.practice.seckill.common.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @program: seckill
 * @description: 登陆控制器
 * @author: 张佳
 * @create: 2022-06-29 10:33
 **/
@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    MemberService memberService;

    //用户登陆
    @RequestMapping("/user/login")
    public ReturnResponse login(@RequestParam(name = "accountId") String accountId,
                                @RequestParam(name = "password") String password,  HttpSession session) {
        Member member = memberService.loginVerify(accountId, password);
        if(member == null) {
            return ReturnResponse.makeFailResponse("用户名或密码错误");
        }
        session.setAttribute("loginUser",member);
        return ReturnResponse.ok(200,"登录成功",member);
    }

    //用户退出
    @RequestMapping("/user/logout")
    public ReturnResponse logout(HttpServletRequest request) {
        request.getSession().removeAttribute("loginUser");
        return ReturnResponse.ok(200,"已退出登陆");
    }
}



