package cn.zjc.aspect.throwing;

import cn.zjc.model.util.EmailMessage;
import cn.zjc.util.JsonResult;
import cn.zjc.util.MailServiceUtil;
import cn.zjc.util.TimeUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.PrintStream;

/**
 * @Author : zhangjiacheng
 * @ClassName : ThrowMailAspect
 * @Date : 2018/6/10
 * @Description : 异常发送邮件通知切面类
 */
@Aspect
@Component
public class ThrowMailAspect {

    private static final Logger logger = LoggerFactory.getLogger(ThrowMailAspect.class);

    /**
     * 邮件的发送者
     */
    @Value("${spring.mail.username}")
    private String from;
    /**
     * 邮件接收者
     */
    @Value("${email.receive}")
    private String to;
    /**
     * 邮件标题
     */
    @Value("${email.subject}")
    private String subject;
    /**
     * 模板名称
     */
    @Value("${email.template}")
    private String template;
    /**
     * 附件地址
     */
    @Value("${email.path}")
    private String path;

    @Resource
    private MailServiceUtil mailServiceUtil;

    @Pointcut("@annotation(cn.zjc.aspect.throwing.ThrowingMail) && execution(* cn.zjc..*(..))")
    private void lockPoint() {
    }

    @AfterThrowing(value = "lockPoint()", throwing = "ex")
    public void throwingMail(JoinPoint joinPoint, Exception ex) throws Exception {
        String methodName = joinPoint.getSignature().getName();
        logger.info("The method :{},occurs excetion:{}", methodName, ex.toString());
        logger.info("抛异常了=========");
        String dateString = TimeUtil.getCurrDateTime2();
        File file = new File(path + dateString + "Error.log");
        //不存在则创建父目录
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        String fileName = dateString.concat("Error.log");
        PrintStream stream = new PrintStream(file);
        //将异常信息写入文件中
        ex.printStackTrace(stream);
        EmailMessage message = EmailMessage
                .builder()
                .methodName(methodName)
                .messageStatus(JsonResult.SYS_ERROR)
                .cause(ex.toString())
                .build();
        //将异常信息文件通过邮件附件方式发送
        mailServiceUtil.sendMessageMail(from, to, subject, template, message, fileName, file);
        //关闭输出流
        stream.flush();
        stream.close();
    }
}
