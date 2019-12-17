package com.songsmily.petapi;

import com.songsmily.petapi.service.impl.MailServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@SpringBootTest
class PetApiApplicationTests {

    @Autowired
    MailServiceImpl mailService;
    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void test()
    {
        mailService.sendSimpleMail("2874643810@qq.com","测试","测试");
    }
    @Test
    public void testTemplateMail() {
        //向Thymeleaf模板传值，并解析成字符串
        Context context = new Context();
        context.setVariable("id", "001");
        String emailContent = templateEngine.process("successMail", context);

        mailService.sendHtmlMail("2874643810@qq.com", "注册确认信息", emailContent);
    }


}
