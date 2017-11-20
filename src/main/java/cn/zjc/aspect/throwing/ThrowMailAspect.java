package cn.zjc.aspect.throwing;

import cn.zjc.util.MailUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhangjiacheng on 2017/11/19
 * 产生异常发送邮件通知
 */
@Aspect
@Component
public class ThrowMailAspect {
    private static final Logger log = LoggerFactory.getLogger(ThrowMailAspect.class);
    @Value("${email.Path}")
    private String Path;

    @Autowired
    private MailUtil mailUtil;

    @Pointcut("@annotation(cn.zjc.aspect.throwing.ThrowingMail) && execution(* cn.zjc..*(..))")
    private void lockPoint(){}

    @AfterThrowing(value = "lockPoint()",throwing = "ex")
    public void throwingMail(JoinPoint joinPoint, Exception ex) throws Exception {
        String methodName = joinPoint.getSignature().getName();
        log.info("The method " + methodName + " occurs excetion:" + ex);
        log.info("抛异常了=========");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(new Date());
        File file = new File(Path + dateString + "Error.log");
        //不存在则创建父目录
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        PrintStream stream = null;
        stream = new PrintStream(file);
        //将异常信息写入文件中
        ex.printStackTrace(stream);
        //将异常信息文件通过邮件附件方式发送
        mailUtil.sendMail(file);
        //关闭输出流
        stream.flush();
        stream.close();
    }
}
