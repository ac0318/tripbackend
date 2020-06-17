package cn.itrip.auth.service;

import cn.itrip.beans.pojo.ItripUser;

public interface TokenService {
    public String generateToken(String userAgent, ItripUser user) throws Exception;
    public void saveToken(String token, ItripUser user) throws Exception;

    public boolean validateToken(String userAgent, String token) throws Exception;
    public void deleteToken(String token) throws Exception;

    public String reToken(String userAgent, String token) throws Exception;
}
