package cn.itrip.auth.service;

import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.common.MD5;
import cn.itrip.common.RedisUtil;
import cn.itrip.dao.user.ItripUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    private ItripUserMapper userMapper;
  /*  @Resource
    private SmsService smsService;*/
    @Resource
    private RedisUtil redisUtil;
 /*   @Resource
    private MailService mailService;*/

    @Override
    public ItripUser selectByCode(String userCode) throws Exception {
        Map map = new HashMap();
        map.put("userCode",userCode);
        List<ItripUser> list = userMapper.getItripUserListByMap(map);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public ItripUser login(String userCode, String password) throws Exception {
        ItripUser user = selectByCode(userCode);
        if(user != null && user.getUserPassword().equals(password)){
            if(user.getActivated() == 1){
                return user;
            }
        }
        return null;
    }

  /*  @Override
    public void registerByPhone(ItripUser user) throws Exception {
        //添加操作 添加此条数据（前端和控制台已经验证过了）
        userMapper.insertItripUser(user);
        //生成验证码
        int code = MD5.getRandomCode();
        //String code = "杨紫川是猪...但是我爱你哦！嘻嘻";
        //发送短信(1.发给那个手机号 2.发送模板 3.发送内容)
        smsService.sendSms(user.getUserCode(),"1",new String[]{code+""});
        //保存到redis （将验证码保存到redis，以便验证激活）
        redisUtil.setString("activation:"+user.getUserCode(),code+"",10*60);
    }

    @Override
    public boolean validatePhone(String user, String code) throws Exception {
        //判断前端传来的验证码于redis中保存的验证码一致不一致
        String value = redisUtil.getString("activation:" + user);
        if(value != null && value.equals(code)){
            //查询数据库是否有此数据
            //如果与前端传来的验证码一致 修改激活码等
            ItripUser itripUser = selectByCode(user);
            if(itripUser != null){
                itripUser.setFlatID(itripUser.getId());
                itripUser.setUserType(0);
                itripUser.setActivated(1);
                //修改动作
                userMapper.updateItripUser(itripUser);
                return true;
            }
        }
        return false;
    }

    @Override
    public void registerByMail(ItripUser user) throws Exception {
        //添加操作 添加此条数据（前端和控制台已经验证过了）
        userMapper.insertItripUser(user);
        //生成邮件内容
        String email = MD5.getMd5(new Date().toString(),32);
        //发送邮件(1.发给那个邮箱地址 2.发送邮件内容)
        mailService.sendMail(user.getUserCode(),email);
        //保存到redis （将验证码保存到redis，以便验证激活）
        redisUtil.setString("activation:"+user.getUserCode(),email,30*60);
    }

    @Override
    public boolean activateMail(String user, String code) throws Exception {
        //判断前端传来的验证码于redis中保存的激活码一致不一致
        String value = redisUtil.getString("activation:" + user);
        if(value != null && value.equals(code)){
            //查询数据库是否有此数据
            //如果与前端传来的验证码一致 修改激活码等
            ItripUser itripUser = selectByCode(user);
            if(itripUser != null){
                itripUser.setFlatID(itripUser.getId());
                itripUser.setUserType(0);
                itripUser.setActivated(1);
                //修改动作
                userMapper.updateItripUser(itripUser);
                return true;
            }
        }
        return false;
    }
*/

}
