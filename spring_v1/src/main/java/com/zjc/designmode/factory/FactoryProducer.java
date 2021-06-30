package com.zjc.designmode.factory;

public class FactoryProducer {

   public static AbstractFactory getFactory(String type) throws Exception {

      Class clazz = Class.forName(type);

      System.out.println("创建工厂:" + type);

      return (AbstractFactory) clazz.newInstance();

   }

}