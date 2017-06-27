package com.hz.school.util;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * http请求工具类
 */
public class HttpUtil {
    private static final String DEFAULT_ENCODING = "GBK";

    /**
     * Http的Get请求
     * @param uri 服务器的uri要用物理IP或域名,不识别localhost或127.0.0.1形式!
     */
    public static String get(String uri) throws IOException {
        HttpGet httpGet=new HttpGet(uri);
        HttpClient httpClient=new DefaultHttpClient();
        HttpResponse httpResponse=httpClient.execute(httpGet);
        int statusCode=httpResponse.getStatusLine().getStatusCode();
        if(statusCode==200){
            String result = EntityUtils.toString(httpResponse.getEntity());
            return result;
        }
        throw new IOException("status is:"+statusCode);
    }
    public static String get(String uri, Map<String, String> paramMap)
            throws ClientProtocolException, IOException {
        StringBuilder sb = new StringBuilder(uri);
        if (paramMap != null) {
            boolean isBegin = true;
            for (String key : paramMap.keySet()) {
                if (isBegin) {
                    sb.append("?").append(key).append("=")
                            .append(paramMap.get(key));
                    isBegin = false;
                } else {
                    sb.append("&").append(key).append("=")
                            .append(paramMap.get(key));
                }
            }
        }
        HttpGet httpGet = new HttpGet(sb.toString());
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = httpClient.execute(httpGet);
        int statusCode;
        if ((statusCode = httpResponse.getStatusLine().getStatusCode()) == 200) {
            String result = EntityUtils.toString(httpResponse.getEntity());
            return result;
        }
        throw new IOException("status is " + statusCode);
    }
    /**
     * GET方式请求https
     *
     * @param uri 服务器的uri要用物理IP或域名,不识别localhost或127.0.0.1形式!
     */
    public static String httpsGet(String uri, String keyFile, String keyPwd)
            throws Exception {
        HttpGet httpGet = new HttpGet(uri);
        HttpClient httpClient = newHttpsClient(keyFile, keyPwd);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        int statusCode;
        if ((statusCode = httpResponse.getStatusLine().getStatusCode()) == 200) {
            String result = EntityUtils.toString(httpResponse.getEntity());
            return result;
        }
        throw new IOException("status is " + statusCode);
    }
    /**
     * POST方式请求
     * @param uri  服务器的uri要用物理IP或域名,不识别localhost或127.0.0.1形式!
     */
    public static String post(String uri, Map<String, String> paramMap)
            throws ClientProtocolException, IOException {

        System.out.println(uri);

        HttpPost httpPost = new HttpPost(uri);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if (paramMap != null) {
            for (String key : paramMap.keySet()) {
                params.add(new BasicNameValuePair(key, paramMap.get(key)));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(params,
                    DEFAULT_ENCODING));
        }
        HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
        int statusCode;
        if ((statusCode = httpResponse.getStatusLine().getStatusCode()) == 200) {
            return EntityUtils.toString(httpResponse.getEntity());
        }
        throw new IOException("status is " + statusCode);
    }
    /**
     * POST方式请求
     *
     * @param uri 服务器的uri要用物理IP或域名,不识别localhost或127.0.0.1形式!
     */
    public static String post(String uri, Map<String, String> paramMap,
                              Map<String, String> headers) throws ClientProtocolException,
            IOException {
        HttpPost httpPost = new HttpPost(uri);
        if (headers != null) {
            for (String key : headers.keySet()) {
                httpPost.setHeader(key, headers.get(key));
            }
        }
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if (paramMap != null) {
            for (String key : paramMap.keySet()) {
                params.add(new BasicNameValuePair(key, paramMap.get(key)));
            }
            httpPost.setEntity(new ByteArrayEntity(paramMap.get("reqData").getBytes("UTF-8")));
//			httpPost.setEntity(new UrlEncodedFormEntity(params,
//					DEFAULT_ENCODING));
        }
        HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
        int statusCode;
        if ((statusCode = httpResponse.getStatusLine().getStatusCode()) == 200) {
            return EntityUtils.toString(httpResponse.getEntity());
        }
        throw new IOException("status is " + statusCode);
    }
    /**
     * POST方式请求https
     *
     * @param uri 服务器的uri要用物理IP或域名,不识别localhost或127.0.0.1形式!
     */
    public static String httpsPost(String uri, Map<String, String> paramMap,
                                   String keyFile, String keyPwd) throws ClientProtocolException,
            IOException, Exception {
        HttpPost httpPost = new HttpPost(uri);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if (paramMap != null) {
            for (String key : paramMap.keySet()) {
                params.add(new BasicNameValuePair(key, paramMap.get(key)));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(params,
                    DEFAULT_ENCODING));
        }
        HttpResponse httpResponse = newHttpsClient(keyFile, keyPwd).execute(
                httpPost);
        int statusCode;
        if ((statusCode = httpResponse.getStatusLine().getStatusCode()) == 200) {
            return EntityUtils.toString(httpResponse.getEntity());
        }
        throw new IOException("status is " + statusCode);
    }

    /*
	* 新建httpsClient
	*/
    private static HttpClient newHttpsClient(String keyFile, String keyPwd)
            throws Exception {
        KeyStore trustStore = KeyStore.getInstance("BKS");
        trustStore.load(new FileInputStream(new File(keyFile)),
                keyPwd.toCharArray());
        SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
        Scheme sch = new Scheme("https", socketFactory, 8443);
        HttpClient client = new DefaultHttpClient();
        client.getConnectionManager().getSchemeRegistry().register(sch);
        return client;
    }
}
