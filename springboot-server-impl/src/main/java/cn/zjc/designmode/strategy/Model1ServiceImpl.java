package cn.zjc.designmode.strategy;

import org.springframework.stereotype.Service;

/**
 * @Author : zhangjiacheng
 * @ClassName : Model1ServiceImpl
 * @Date : 2019/12/21
 * @Description : TODO
 */
@Service
public class Model1ServiceImpl implements ModelService {

    @Override
    public Integer queryByType() {
        return 1;
    }
}
