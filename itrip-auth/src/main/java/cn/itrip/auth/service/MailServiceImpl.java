package cn.itrip.auth.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class MailServiceImpl implements MailService {
    @Resource
    private SimpleMailMessage message;
    @Resource
    private JavaMailSenderImpl mailSender;
    @Override
    public void sendMail(String emailAddr, String email) throws Exception {
        message.setText(email);
        message.setTo(emailAddr);
        mailSender.send(message);
    }
}
