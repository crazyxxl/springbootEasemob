package com.crazyxxl.easemob.service.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HttpClientService {
	@Autowired
    private CloseableHttpClient httpClient;
    @Autowired
    private RequestConfig requestConfig;
    private static Logger logger = Logger.getLogger(HttpClientService.class);
    public String doGet(String url) throws IOException {
        //创建httpClient对象
        CloseableHttpResponse response = null;
        HttpGet httpGet = new HttpGet(url);
        //设置请求参数
        httpGet.setConfig(requestConfig);
        try {
            //执行请求
            response = httpClient.execute(httpGet);
            //判断返回状态码是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;

    }
    public String doGet(String url,  Map<String, String> map) throws IOException {
        //创建httpClient对象
        CloseableHttpResponse response = null;
      
        List<String> params = Lists.newArrayList();
        for (String key : map.keySet()){
        	params.add(key + "=" + map.get(key));
		}
        Joiner joiner = Joiner.on("&&");
        HttpGet httpGet = new HttpGet(url + "?" + joiner.join(params));
        //设置请求参数
        httpGet.setConfig(requestConfig);
        try {
            //执行请求
            response = httpClient.execute(httpGet);
            //判断返回状态码是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;

    }
    public byte[] doPost(String url, byte[] bytes, String contentType) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        //设置请求参数
        httpPost.setConfig(requestConfig);
        httpPost.setEntity(new ByteArrayEntity(bytes));  
        if (contentType != null)  
            httpPost.setHeader("Content-type", contentType); 
        
        //创建httpClient对象
        CloseableHttpResponse response = null;
        try {
            //执行请求
            response = httpClient.execute(httpPost);
            HttpEntity entityResponse = response.getEntity();  
            int contentLength = (int) entityResponse.getContentLength();  
            if (contentLength <= 0)  
                throw new IOException("No response");
            byte[] respBuffer = new byte[contentLength];  
            if (entityResponse.getContent().read(respBuffer) != respBuffer.length)  
                throw new IOException("Read response buffer error"); 
            logger.info("get Resp : " + Hex.encodeHexString(respBuffer));
            return respBuffer;  
            //return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity()));
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    public  byte[] post(String url, byte[] bytes) throws IOException {  
        return doPost(url, bytes, null);  
    }
    public void requestPost(String url, Map<String, String> map) throws ClientProtocolException, IOException{
    	HttpPost httpPost = new HttpPost(url);
    	httpPost.setConfig(requestConfig);
    	httpPost.setEntity(new UrlEncodedFormEntity(mapToList(map))); 
    	CloseableHttpResponse response = null;
    	try {
	    	response = httpClient.execute(httpPost);
	    	HttpEntity entity = response.getEntity();
	        String jsonStr = EntityUtils.toString(entity, "utf-8");
	        //EntityUtils.consume(entity);
	        logger.info(jsonStr);
    	} finally {
            if (response != null) {
                response.close();
            }
        }    
    }
    public String postJson(String url, String jsonStr, Map<String, String> header) throws ClientProtocolException, IOException{
    	HttpPost post = new HttpPost(url);
    	post.setConfig(requestConfig);
    	post.addHeader("Content-type","application/json; charset=utf-8");  
    	post.setHeader("Accept", "application/json");
    	if (header != null){
    		for (String key : header.keySet()){
    			post.setHeader(key, header.get(key));
    		}
    	}
    	post.setEntity(new StringEntity(jsonStr, Charset.forName("UTF-8")));  
       
    	CloseableHttpResponse response = null;
    	try {
    		response = httpClient.execute(post);
    		return EntityUtils.toString(response.getEntity()); 
    	} finally {
            if (response != null) {
                response.close();
            }
        }    
    }
    public String postJson(String url, String jsonStr) throws ClientProtocolException, IOException{
    	return postJson(url, jsonStr, null);
    }
    public String post(String url,  Map<String, String> header) throws ClientProtocolException, IOException{
    	HttpPost post = new HttpPost(url);
    	post.setConfig(requestConfig);
    	if (header != null){
    		for (String key : header.keySet()){
    			post.setHeader(key, header.get(key));
    		}
    	}
    	//post.setEntity(new StringEntity(jsonStr, Charset.forName("UTF-8")));  
       
    	CloseableHttpResponse response = null;
    	try {
    		response = httpClient.execute(post);
    		return EntityUtils.toString(response.getEntity()); 
    	} finally {
            if (response != null) {
                response.close();
            }
        }    
    }
    public  String delete(String url, Map<String, String> header) throws ClientProtocolException, IOException{  
     
    	HttpDelete delete = new HttpDelete(url);
    	delete.setConfig(requestConfig);
    	if (header != null){
    		for (String key : header.keySet()){
    			delete.setHeader(key, header.get(key));
    		}
    	}
    	//post.setEntity(new StringEntity(jsonStr, Charset.forName("UTF-8")));  
       
    	CloseableHttpResponse response = null;
    	try {
    		response = httpClient.execute(delete);
    		return EntityUtils.toString(response.getEntity()); 
    	} finally {
            if (response != null) {
                response.close();
            }
        }     
        
    }
    public String put(String url, Map<String, String> header, String jsonStr) throws ClientProtocolException, IOException{
    	HttpPut put = new HttpPut(url);
    	
    	put.setConfig(requestConfig);
    	put.addHeader("Content-type","application/json; charset=utf-8");  
    	put.setHeader("Accept", "application/json");
    	if (header != null){
    		for (String key : header.keySet()){
    			put.setHeader(key, header.get(key));
    		}
    	}
    	//post.setEntity(new StringEntity(jsonStr, Charset.forName("UTF-8")));  
    	put.setEntity(new StringEntity(jsonStr, Charset.forName("UTF-8")));  
    	CloseableHttpResponse response = null;
    	try {
    		response = httpClient.execute(put);
    		return EntityUtils.toString(response.getEntity()); 
    	} finally {
            if (response != null) {
                response.close();
            }
        }     
    }
    private List<NameValuePair> mapToList(Map<String,String> map){
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
    	for (String key : map.keySet()) {
    		params.add(new BasicNameValuePair(key, map.get(key)));
    	}
    	return params;
    }
    


}
