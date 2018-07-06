package cn.zjc.enums;

/**
 * @Author : zhangjiacheng
 * @ClassName : SysUtilCode
 * @Date : 2018/6/8
 * @Description : 错误编码枚举类
 */
public enum SysUtilCode {

    //密码错误
    PASSWORD_ERROR(1, "密码错误"),
    //消息发送失败
    MESSAGE_SEND_ERROR(2, "消息发送失败"),
    //SESSION过期
    EXPIRED_SESSION(3, "你已下线,请重新登录");

    private Integer code;
    private String desc;

    SysUtilCode(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
