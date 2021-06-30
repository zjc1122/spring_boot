package com.zjc.designmode.chain.request;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * @ClassName : PrintProcessor
 * @Author : zhangjiacheng
 * @Date : 2021/2/2
 * @Description : TODO
 */
public class PrintProcessor extends Thread implements IRequestProcessor{
    LinkedBlockingDeque<Request> requests = new LinkedBlockingDeque<>();

    private IRequestProcessor nextProcessor;

    private volatile boolean isFinish = false;

    public PrintProcessor(IRequestProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    public PrintProcessor() {
    }

    public void shutdown(){
        isFinish = true;
    }

    @Override
    public void run() {
        while (!isFinish){
            try {
                Request request = requests.take();//阻塞式获取数据
                //真正的处理逻辑
                System.out.println("PrintProcessor:" + request);
                if (nextProcessor!=null){
                    nextProcessor.processor(request);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void processor(Request request) {
        //Todo 根据实际需求去做一些处理
        requests.add(request);
    }
}
