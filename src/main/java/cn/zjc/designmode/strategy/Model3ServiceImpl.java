package cn.zjc.designmode.strategy;

import org.springframework.stereotype.Service;

/**
 * @Author : zhangjiacheng
 * @ClassName : Model3ServiceImpl
 * @Date : 2019/12/23
 * @Description : TODO
 */
@Service
public class Model3ServiceImpl implements ModelService {
    @Override
    public Integer queryByType() {
        return 3;
    }
}
