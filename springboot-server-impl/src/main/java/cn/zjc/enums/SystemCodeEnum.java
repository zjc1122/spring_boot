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
    SUCCESS(0, "success","ok"),
    //错误
    ERROR(9999, "error","系统发生错误"),
    //密码错误
    PASSWORD_ERROR(1, "密码错误","error"),
    //消息发送失败
    MESSAGE_SEND_ERROR(2, "消息发送失败","error"),
    //SESSION过期
    EXPIRED_SESSION(3, "请重新登录","error"),
    EXPIRED_TOKEN(4, "Token已经过期","error"),
    USER_ERROR(5, "获取用户异常","error"),
    USER_NOT_EXIST(6, "用户不存在","error"),
    VERIFY_ERROR(7, "用户名或密码错误","登录失败"),
    ACCESS_ERROR(8, "访问无权限","error"),
    ;

    private Integer code;
    private String name;
    private String info;

    SystemCodeEnum(Integer code, String name, String info) {
        this.code = code;
        this.name = name;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    private static final Map<Integer, SystemCodeEnum> MAP = Maps.newHashMap();
    static {
        for (SystemCodeEnum systemCodeEnum : values()) {
            MAP.put(systemCodeEnum.getCode(), systemCodeEnum);
        }
    }

    public static SystemCodeEnum getCode(Integer code) {
        return MAP.get(code);
    }

}
