package cn.itrip.auth.controller;

import cn.itrip.auth.service.TokenService;
import cn.itrip.auth.service.UserService;
import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.ErrorCode;
import cn.itrip.common.MD5;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private TokenService tokenService;

    @PostMapping("/dologin")
    public Dto doLogin(String name, String password, HttpServletRequest request){
        try {
            ItripUser user = userService.login(name, MD5.getMd5(password,32));
            if(user != null ){
                String userAgent = request.getHeader("user-agent");
                String token = tokenService.generateToken(userAgent, user);
                tokenService.saveToken(token,user);
                return DtoUtil.returnSuccess("登录成功");
            }else{
                return DtoUtil.returnFail("用户名密码错误","100231");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常", ErrorCode.AUTH_UNKNOWN);
        }
    }
}
