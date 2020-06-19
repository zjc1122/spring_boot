package cn.zjc.designmode.chain;

/**
 * @Author : zhangjiacheng
 * @ClassName : MyFilter
 * @Date : 2019/12/25
 */

public interface MyFilter {

    void doFilter(MyRequest request, MyResponse response, MyFilter chain);
}
