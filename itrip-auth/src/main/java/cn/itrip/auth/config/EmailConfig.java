package cn.itrip.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {
    @Bean
    public SimpleMailMessage simpleMailMessage(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ac_0318@163.com");
        message.setSubject("【i旅行】请激活您的账户");
        return message;
    }
    @Bean
    public JavaMailSenderImpl javaMailSender(){
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtp.163.com");
        sender.setPort(25);
        sender.setUsername("ac_0318@163.com");
        //授权码 授权此邮箱可以发送邮件 不是邮箱登录密码
        sender.setPassword("ARWVTNTUATNEJTAJ");
        sender.setDefaultEncoding("utf-8");
        return sender;
    }
}
