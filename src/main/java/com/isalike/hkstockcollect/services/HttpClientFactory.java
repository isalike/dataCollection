package com.isalike.hkstockcollect.services;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
//import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

public class HttpClientFactory {
    private static PoolingHttpClientConnectionManager cm;

    static {
        cm = new PoolingHttpClientConnectionManager();

        // Increase max total connection to 200
        cm.setMaxTotal(200);

        // Increase default max connection per route to 20
        cm.setDefaultMaxPerRoute(100);

        // Increase max connections for localhost:80 to 50
        HttpHost localhost = new HttpHost("locahost", 80);
        cm.setMaxPerRoute(new HttpRoute(localhost), 50);
    }

    public static CloseableHttpClient HttpClientPoolingManager(int secondsTimeout) {
        if (secondsTimeout > 0) {
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(secondsTimeout * 1000)
                    .setConnectionRequestTimeout(secondsTimeout * 1000).setSocketTimeout(secondsTimeout * 1000).build();

            return HttpClients.custom().setConnectionManager(cm).setConnectionManagerShared(true)
                    .setDefaultRequestConfig(requestConfig).build();

        } else {
            return HttpClients.custom().setConnectionManager(cm).setConnectionManagerShared(true).build();
        }
    }

    public static CloseableHttpClient HttpClientPoolingManager(int secondsTimeout, HttpHost proxy) {
        // DefaultProxyRoutePlanner routePlanner = new
        // DefaultProxyRoutePlanner(proxy);
        if (secondsTimeout > 0) {
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(secondsTimeout * 1000)
                    .setConnectionRequestTimeout(secondsTimeout * 1000).setSocketTimeout(secondsTimeout * 1000).build();
            return HttpClients.custom().setConnectionManager(cm).setConnectionManagerShared(true)
                    .setDefaultRequestConfig(requestConfig).setProxy(proxy)
                    // .setRoutePlanner(routePlanner)
                    .build();

        } else {
            return HttpClients.custom().setConnectionManager(cm).setConnectionManagerShared(true).setProxy(proxy)
                    // .setRoutePlanner(routePlanner)
                    .build();
        }
    }

    @SuppressWarnings("deprecation")
    public static CloseableHttpClient HttpClientPoolingManagerSSL(int secondsTimeout) {
        SSLContext sslContext = null;
        try {
            sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(final X509Certificate[] arg0, final String arg1) throws CertificateException {
                    return true;
                }
            }).build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext,
                new NoopHostnameVerifier());
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslSocketFactory)
                .build();
        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        connMgr.setMaxTotal(200);
        connMgr.setDefaultMaxPerRoute(100);
        HttpHost localhost = new HttpHost("locahost", 80);
        connMgr.setMaxPerRoute(new HttpRoute(localhost), 50);

        if (secondsTimeout > 0) {
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(secondsTimeout * 1000).build();
            return HttpClients.custom().setConnectionManager(connMgr).setSslcontext(sslContext)
                    .setConnectionManagerShared(true).setDefaultRequestConfig(requestConfig).build();
        } else {
            return HttpClients.custom().setConnectionManager(connMgr).setSslcontext(sslContext)
                    .setConnectionManagerShared(true).build();
        }
    }
}
