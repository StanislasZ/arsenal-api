package com.cmbnb.weChatNotice.core.util.restTemplate;


import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

public class RestTemplateUtils {


    /**
     * restTemplate请求模板  发起post请求  协议为http
     * @param url
     * @param body
     * @param clazz
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T, V>  V postHttp(String url, T body, Class<V>  clazz) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<T> request = new HttpEntity<>(body, headers);
        ResponseEntity<V> response = template.postForEntity(url, request, clazz);
        return response.getBody();
    }


    /**
     * https  post
     * @param url
     * @param body
     * @param clazz
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T, V>  V postHttps(String url, T body, Class<V>  clazz, MediaType contentType) {
        RestTemplate template = new RestTemplate(new HttpsClientRequestFactory());
        //编码为utf-8
        template.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        HttpHeaders headers = new HttpHeaders();
        //定义请求参数类型，这里用json所以是MediaType.APPLICATION_JSON
        headers.setContentType(contentType);

        HttpEntity<T> request = new HttpEntity<>(body, headers);
        ResponseEntity<V> response = template.postForEntity(url, request, clazz);
        return response.getBody();

    }

    /**
     * https  post
     * @param url
     * @param body
     * @param clazz
     * @param <V>
     * @return
     */
    public static <V>  V postHttps(String url, MultiValueMap<String, Object> body, Class<V>  clazz, MediaType contentType) {
        RestTemplate template = new RestTemplate(new HttpsClientRequestFactory());
        //编码为utf-8
        template.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        HttpHeaders headers = new HttpHeaders();
        //定义请求参数类型
        headers.setContentType(contentType);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<V> response = template.postForEntity(url, request, clazz);
        return response.getBody();

    }






    /**
     * http   get
     * @param url
     * @param clazz
     * @param <V>
     * @return
     */
    public static <V>  V getHttp(String url, Class<V>  clazz) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<V> response = template.getForEntity(url, clazz);
        return response.getBody();
    }

    /**
     * https   get
     * @param url
     * @param clazz
     * @param <V>
     * @return
     */
    public static <V>  V getEntityFromHttps(String url, Class<V>  clazz) {
        RestTemplate template = new RestTemplate(new HttpsClientRequestFactory());
        template.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<V> response = template.getForEntity(url, clazz);
        return response.getBody();
    }


    public static String getStringFromHttps(String url) {
        RestTemplate template = new RestTemplate(new HttpsClientRequestFactory());
        template.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> response = template.getForEntity(url, String.class);
        return response.getBody();
    }





}
