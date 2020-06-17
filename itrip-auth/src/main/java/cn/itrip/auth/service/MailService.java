package cn.itrip.auth.service;

public interface MailService {
    public void sendMail(String emailAddr, String email) throws Exception;
}
