package cn.zjc.enums;

import com.google.common.collect.Maps;
import java.util.Map;

/**
 * @Author : zhangjiacheng
 * @ClassName : SystemCodeEnum
 * @Date : 2018/6/8
 * @Description : 错误编码枚举类
 */
public enum SystemCodeEnum {

    //成功
    SUCCESS(0000, "success"),
    //错误
    ERROR(9999, "error"),
    //密码错误
    PASSWORD_ERROR(1, "密码错误"),
    //消息发送失败
    MESSAGE_SEND_ERROR(2, "消息发送失败"),
    //SESSION过期
    EXPIRED_SESSION(3, "你已下线,请重新登录"),
    EXPIRED_TOKEN(4, "Token已经过期"),
    USER_ERROR(5, "查询用户异常"),
    USER_NOT_EXIST(6, "用户不存在"),
    ERROR_USER(7, "用户信息不正确"),
    ;

    private Integer code;
    private String desc;

    SystemCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    private static final Map<Integer, SystemCodeEnum> map = Maps.newHashMap();
    static {
        for (SystemCodeEnum systemCodeEnum : values()) {
            map.put(systemCodeEnum.getCode(), systemCodeEnum);
        }
    }

    public static SystemCodeEnum getCode(Integer code) {
        return map.get(code);
    }

}
