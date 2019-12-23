package cn.zjc.designmode.strategy;

/**
 * @Author : zhangjiacheng
 * @ClassName : ModelService
 * @Date : 2019/12/21
 * @Description : 策略设计模式
 */
public interface ModelService {

    Integer queryByType();

    default String printClassName(){
        return this.getClass().getName();
    }
}
