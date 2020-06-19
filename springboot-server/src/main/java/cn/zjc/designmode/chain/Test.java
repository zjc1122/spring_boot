package cn.zjc.designmode.chain;

/**
 * @Author : zhangjiacheng
 * @ClassName : Test
 * @Date : 2019/12/25
 * @Description : TODO
 */
public class Test {

    public static void main(String[] args) {
        MyRequest request = new MyRequest();
        request.setRequest("开始处理Request");
        MyResponse response = new MyResponse();
        response.setResponse("开始处理Response");
        MyFilterChain chain = new MyFilterChain();
        chain.addFilter(new MyFilter1Impl());
        chain.addFilter(new MyFilter2Impl());
        chain.doFilter(request, response, chain);
        System.out.println(request.getRequest());
        System.out.println(response.getResponse());
    }
}
