package com.zz.eshuo.as30;

import android.app.Application;
import android.content.Context;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;


/**
 * Created by ADMIN on 2017/4/24.
 */

public class BaseApp extends Application {
    private static BaseApp instance;

    {

        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        UMShareAPI.get(this);

//        initFresco();
//        initOkGo();

    }

    /*获取上下文对象*/
    public static Context getInstance() {
        return instance;
    }


//    private void initOkGo() {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
//        //log打印级别，决定了log显示的详细程度
//        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
//        //log颜色级别，决定了log在控制台显示的颜色
//        loggingInterceptor.setColorLevel(Level.INFO);
//        builder.addInterceptor(loggingInterceptor);
//
//        //全局的读取超时时间
//        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
//        //全局的写入超时时间
//        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
//        //全局的连接超时时间
//        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
//
//        //使用sp保持cookie，如果cookie不过期，则一直有效
//        builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
//
//        //方法一：信任所有证书,不安全有风险
//        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
//
//        //全局的头部
//        HttpHeaders headers = new HttpHeaders();
//        headers.put(Constant.PostKey, Constant.PostValue);
////-------------------------------------------------------------------------------------//
//
//        OkGo okGo = OkGo.getInstance().init(this)        //必须调用初始化
//                .setOkHttpClient(builder.build())        //必须设置OkHttpClient
//                .setRetryCount(3)                        //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
//                .addCommonHeaders(headers);              //全局的公共头部
//
//    }


    /**
     * 初始化Fresco
     * 使用ImagePipelineConfig的原因是为了支持不同格式图片的压缩
     */
//    private void initFresco() {
//        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
//                .setDownsampleEnabled(true)
//                .build();
//        Fresco.initialize(this, config);
//        Fresco.initialize(this);
//    }


}