package cn.itrip.auth.service;

import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.common.MD5;
import cn.itrip.common.RedisUtil;
import cn.itrip.common.UserAgentUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Transactional
public class TokenServiceImpl implements TokenService {
    @Resource
    private RedisUtil redisUtil;

    @Override
    public String generateToken(String userAgent, ItripUser user) throws Exception {
        //token:PC-userCode(MD5)-userId-creationDate-random(6)
        StringBuilder str = new StringBuilder("token:");
        //判断是PC端还是移动端  分别拼接
        if(UserAgentUtil.CheckAgent(userAgent)){
            str.append("MOBILE-");
        }else{
            str.append("PC-");
        }
        //拼接userCode(MD5加密)
        str.append(MD5.getMd5(user.getUserCode(),32)+"-");
        //拼接userId
        str.append(user.getId()+"-");
        //拼接creationDate
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        str.append(sdf.format(new Date())+"-");
        //拼接随机数
        str.append(MD5.getMd5(userAgent,6));
        return str.toString();
    }

    @Override
    public void saveToken(String token, ItripUser user) throws Exception {
        String json = JSONObject.toJSONString(user);
        //保存到redis中
        //先判断 PC端有过期期限
        if(token.startsWith("token:PC-")){
            redisUtil.setString(token,json,2*60*60);
        }else{
            //移动端没有过期期限
            redisUtil.setString(token,json);
        }
    }

    @Override
    public boolean validateToken(String userAgent, String token) throws Exception {
        if(!redisUtil.hasKey(token)){
            return false;
        }
        String agentMD5 = token.split("-")[4];
        if(!agentMD5.equals(MD5.getMd5(userAgent,6))){
            return false;
        }
        return true;
    }

    @Override
    public void deleteToken(String token) throws Exception {
        if(redisUtil.hasKey(token)){
            redisUtil.del(token);
        }
    }

    @Override
    public String reToken(String userAgent, String token) throws Exception {
        if(!validateToken(userAgent,token)){
            throw new Exception("token无效");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        //token的生成时间
        long genTime = sdf.parse(token.split("-")[3]).getTime();
        long passTime = System.currentTimeMillis() - genTime;
        if(passTime < 30*60*1000){
            throw new Exception("token在保护期内，不允许置换");
        }
        String newToken = "";
        ItripUser user = JSONObject.parseObject(redisUtil.getString(token), ItripUser.class);
        long ttl = new RedisUtil().getExpire(token);
        if(ttl > 0 || ttl == -1){
            newToken = generateToken(userAgent,user);
            saveToken(newToken,user);
            redisUtil.setString(token,redisUtil.getString(token),5*60);
        }
        return newToken;
    }
}
