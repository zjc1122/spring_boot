package cn.zjc.util;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName : HttpClient
 * @Author : zhangjiacheng
 * @Date : 2020/6/22
 * @Description : TODO
 */
public class HttpClientUtil {


    private static final int TIME_OUT = 60;
    private static OkHttpClient okHttpClient = null;

    public HttpClientUtil() {

    }

    public static OkHttpClient getInstance() {
        if (okHttpClient == null) {
            synchronized (HttpClientUtil.class) {
                if (okHttpClient == null){
                    OkHttpClient.Builder okHttpBuilder = new OkHttpClient().newBuilder();
                    okHttpBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
                    okHttpBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
                    okHttpBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
                    // 添加https支持
                    okHttpBuilder.hostnameVerifier((hostname, session) -> true);
                    okHttpBuilder.sslSocketFactory(createSSLSocketFactory());
                    okHttpClient = okHttpBuilder.build();
                }
            }
        }
        return okHttpClient;
    }
    public static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sSLSocketFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()},
                    new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception ignored) {
        }
        return sSLSocketFactory;
    }

    private static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    /**
     * get 同步请求
     *
     * @return response
     */
    public static Response doGet(String url) {
        Request request = new Request.Builder()
                .get()//默认get,可省略
                .url(url)
                .build();
        Response response = null;
        try {
            response = getInstance().newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    /**
     * get异步请求方法
     * 通过callback获取结果
     * @param url
     * @param callback
     */
     public static void doGet(String url, Callback callback) {
         Request request = new Request.Builder()
                 .url(url).build();
         Call call = getInstance().newCall(request);
         call.enqueue(callback);
     }

    /**
     * post同步请求
     * @param url
     * @param map
     * @return
     * @throws IOException
     */
    public static Response doPost(String url, ConcurrentMap<String,String> map) throws IOException {

        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            builder.add(key, value);
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        return getInstance().newCall(request).execute();
    }

    /**
     * post 异步请求
     */
    public static void doPost(String url, final Callback callback, ConcurrentMap<String,String> map) {
        FormBody.Builder builder = new FormBody.Builder();
        map.forEach(builder::add);
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);

    }

}
