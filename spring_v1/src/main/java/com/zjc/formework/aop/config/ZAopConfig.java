package com.zjc.formework.aop.config;

import lombok.Data;

/**
 * @ClassName : ZAopConfig
 * @Author : zhangjiacheng
 * @Date : 2021/1/16
 * @Description : TODO
 */
@Data
public class ZAopConfig {

    private String pointCut;

    private String aspectBefore;

    private String aspectAfter;

    private String aspectClass;

    private String aspectAfterThrow;

    private String aspectAfterThrowingName;

    private String aspectAround;
}
