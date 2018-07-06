package cn.zjc.server.util;

import com.google.common.collect.Maps;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Map;


/**
 * @ClassName : MailServiceUtil
 * @Author : zhangjiacheng
 * @Date : 2018-06-09
 * @Description : 邮件模板
 */
@Service
public class MailService {

    @Resource
    private JavaMailSender mailSender;
    @Resource
    private FreeMarkerConfigurer configurer;

    /**
     * @param params       发送邮件的主题对象 object
     * @param from         发送人
     * @param to           接收人
     * @param subject      邮件标题
     * @param templateName 模板名称
     */
    public void sendMessageMail(String from, String to, String subject, String templateName, Object params, String fileName, File file) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(from);
            helper.setTo(InternetAddress.parse(to));
            helper.setSubject(subject);
            Template template = configurer.getConfiguration().getTemplate(templateName);
            Map<String, Object> model = Maps.newHashMap();
            model.put("params", params);
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            helper.setText(text, true);
            helper.addAttachment(fileName, file);
            mailSender.send(mimeMessage);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}