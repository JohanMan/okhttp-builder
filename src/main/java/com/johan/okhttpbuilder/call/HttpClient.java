package com.johan.okhttpbuilder.call;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2017/5/13.
 */

public class HttpClient {

    private static OkHttpClient client;

    /**
     * 获取OkHttpClient实例
     * @return
     */
    public static OkHttpClient getClient() {
        if (client == null) {
            synchronized (HttpClient.class) {
                if (client == null) {
                    buildClient(null);
                }
            }
        }
        return client;
    }

    /**
     * OkHttpClient用的是懒汉模式单例，在Application的onCreate方法Build，保证实例唯一
     * @param config
     */
    private static void buildClient(HttpClientConfig config) {
        if (config == null) {
            client = new OkHttpClient();
            return;
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (config.isCache()) {
            builder.cache(new Cache(config.cacheFile, config.cacheSize));
        }
        if (config.isSetConnectTimeOut()) {
            builder.connectTimeout(config.connectTimeOutSecond, TimeUnit.SECONDS);
        }
        if (config.isSetReadTimeOut()) {
            builder.readTimeout(config.readTimeOutSecond, TimeUnit.SECONDS);
        }
        if (config.isSetWriteTimeOut()) {
            builder.writeTimeout(config.writeTimeOutSecond, TimeUnit.SECONDS);
        }
        client = builder.build();
    }

    /**
     * OkHttpClient配置
     */
    public class HttpClientConfig {

        private File cacheFile;
        private long cacheSize;

        private int connectTimeOutSecond;
        private int readTimeOutSecond;
        private int writeTimeOutSecond;

        public HttpClientConfig setCache(File cacheFile, long cacheSize) {
            this.cacheFile = cacheFile;
            this.cacheSize = cacheSize;
            return this;
        }

        public HttpClientConfig setConnectTimeOutSecond(int connectTimeOutSecond) {
            this.connectTimeOutSecond = connectTimeOutSecond;
            return this;
        }

        public HttpClientConfig setReadTimeOutSecond(int readTimeOutSecond) {
            this.readTimeOutSecond = readTimeOutSecond;
            return this;
        }

        public HttpClientConfig setWriteTimeOutSecond(int writeTimeOutSecond) {
            this.writeTimeOutSecond = writeTimeOutSecond;
            return this;
        }

        public boolean isCache() {
            return cacheSize > 0;
        }

        public boolean isSetConnectTimeOut() {
            return connectTimeOutSecond > 0;
        }

        public boolean isSetReadTimeOut() {
            return readTimeOutSecond > 0;
        }

        public boolean isSetWriteTimeOut() {
            return writeTimeOutSecond > 0;
        }

        public void buildClient() {
            HttpClient.buildClient(this);
        }

    }

}
