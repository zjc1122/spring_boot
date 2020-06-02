package service.impl;

import service.IAnimal;

/**
 * @ClassName : DogImpl
 * @Author : zhangjiacheng
 * @Date : 2020/5/18
 * @Description : TODO
 */
public class PigImpl implements IAnimal {
    @Override
    public void say() {
        System.out.println("I am a pig");
    }
}
