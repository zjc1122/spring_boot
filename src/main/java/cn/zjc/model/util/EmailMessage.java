package cn.zjc.model.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName : EmailMessage
 * @Author : zhangjiacheng
 * @Date : 2018/6/10
 * @Description : 邮件模板实体
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmailMessage {

    private String methodName;

    private String messageStatus;

    private String cause;
}
