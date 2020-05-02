package cn.zjc.designmode.chain;

import org.springframework.stereotype.Service;

/**
 * @Author : zhangjiacheng
 * @ClassName : MyFilter2Impl
 * @Date : 2019/12/25
 */
@Service
public class MyFilter2Impl implements MyFilter {

    @Override
    public void doFilter(MyRequest request, MyResponse response, MyFilter chain) {

        request.setRequest(request.getRequest() + MyFilter2Impl.class.getName() + "处理一次-->");
        chain.doFilter(request, response, chain);
        response.setResponse(response.getResponse() + MyFilter2Impl.class.getName() + "处理一次-->");
    }
}
