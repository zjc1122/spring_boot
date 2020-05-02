package cn.zjc.designmode.strategy;

import org.springframework.stereotype.Service;

/**
 * @Author : zhangjiacheng
 * @ClassName : Model2ServiceImpl
 * @Date : 2019/12/21
 * @Description : TODO
 */
@Service
public class Model2ServiceImpl implements ModelService {
    @Override
    public Integer queryByType() {
        return 2;
    }
}
