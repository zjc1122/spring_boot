package com.zjc.designmode.chain;

import org.springframework.stereotype.Service;

/**
 * @Author : zhangjiacheng
 * @ClassName : MyFilter1Impl
 * @Date : 2019/12/25
 */
@Service
public class MyFilter1Impl implements MyFilter {

    @Override
    public void doFilter(MyRequest request, MyResponse response, MyFilter chain) {
        request.setRequest(request.getRequest() + MyFilter1Impl.class.getName() + "处理一次-->");
        chain.doFilter(request, response, chain);
        response.setResponse(response.getResponse() + MyFilter1Impl.class.getName() + "处理一次-->");
        Thread.currentThread().isInterrupted();
    }
}
