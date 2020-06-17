package cn.itrip.auth.service;

import cn.itrip.beans.pojo.ItripUser;

public interface UserService {
    //登录
    public ItripUser selectByCode(String userCode) throws Exception;
    public ItripUser login(String userCode, String password)throws Exception;

/*    //注册用户 数据库添加信息
    public void registerByPhone(ItripUser user) throws Exception;
    //接受前端传来的手机号和验证码，如果一直 修改激活码为1
    public boolean validatePhone(String user, String code) throws Exception;*/

    /*public void registerByMail(ItripUser user) throws Exception;
    public boolean activateMail(String user, String code) throws Exception;*/
}
