package cn.zjc.model;

import lombok.*;

/**
 * Created by zhangjiacheng on 2018/2/7.
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
