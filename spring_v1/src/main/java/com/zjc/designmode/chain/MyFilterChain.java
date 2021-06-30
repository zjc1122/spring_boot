package com.zjc.designmode.chain;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author : zhangjiacheng
 * @ClassName : MyFilterChain
 * @Date : 2019/12/25
 */
@Configuration
public class MyFilterChain implements MyFilter {

    public List<MyFilter> filters = Lists.newArrayList();

    public int index = 0;

    public MyFilterChain addFilter(MyFilter filter) {
        filters.add(filter);
        return this;
    }

    @Override
    public void doFilter(MyRequest request, MyResponse response, MyFilter chain) {
        if(index == filters.size()){
            return;
        }
        MyFilter filter = filters.get(index);
        index ++;
        filter.doFilter(request, response, chain);
    }

}
