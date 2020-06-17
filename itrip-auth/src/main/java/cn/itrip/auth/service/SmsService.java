package cn.itrip.auth.service;

public interface SmsService {
   public void sendSms(String to, String templateId, String[] datas) throws  Exception;
}
