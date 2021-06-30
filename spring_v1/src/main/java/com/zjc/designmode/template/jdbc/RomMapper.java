package com.zjc.designmode.template.jdbc;

import java.sql.ResultSet;

/**
 * @ClassName : RomMapper
 * @Author : zhangjiacheng
 * @Date : 2020/12/22
 * @Description : TODO
 */
public interface RomMapper<T> {

    T mapRow(ResultSet rs, int rowNum) throws Exception;
}
