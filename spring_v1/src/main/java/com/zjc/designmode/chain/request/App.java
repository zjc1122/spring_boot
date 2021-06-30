package com.zjc.designmode.chain.request;

/**
 * @ClassName : App
 * @Author : zhangjiacheng
 * @Date : 2021/2/2
 * @Description : TODO
 */
public class App {

    static IRequestProcessor requestProcessor;
    private void setUp(){
        PrintProcessor printProcessor = new PrintProcessor();
        printProcessor.start();
        SaveProcessor saveProcessor = new SaveProcessor(printProcessor);
        saveProcessor.start();
        requestProcessor = new PreProcessor(saveProcessor);
        ((PreProcessor)requestProcessor).start();
    }

    public static void main(String[] args) {
        App app = new App();
        app.setUp();
        Request request = new Request();
        request.setName("aa");
        requestProcessor.processor(request);
    }
}
