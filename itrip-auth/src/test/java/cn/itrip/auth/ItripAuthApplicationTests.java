package cn.itrip.auth;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.annotation.Resource;

@SpringBootTest
class ItripAuthApplicationTests {
    @Resource
    private SimpleMailMessage mailMessage;
    @Resource
    private JavaMailSender mailSender;

    @Test
    public void test01() throws Exception{
        mailMessage.setText("ac最棒!");
        mailMessage.setTo("ac_0318@163.com");
        mailSender.send(mailMessage);
    }

}
