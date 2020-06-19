package cn.zjc.model.util;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName : SystemMsg
 * @Author : zhangjiacheng
 * @Date : 2018/6/10
 * @Description : 统一异常实体
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SystemMsg {

    private Integer status;

    private String sysTime;

    private String message;

    private String error;

    private String path;
}
